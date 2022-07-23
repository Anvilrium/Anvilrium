package anvilrium.common.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import anvilrium.common.Pair;
import anvilrium.common.events.IEvent;

public class EventBus implements IEventBus {

	public static final EventBus MAIN_EVENT_BUS = new EventBus("MainEventBus");

	protected final Map<Class<? extends IEvent>, List<Consumer<IEvent>>> listeners;
	protected final String name;
	protected final EventHandlerThread handlerThread;

	public EventBus(String name) {
		this.name = name;
		this.listeners = new ConcurrentHashMap<>();
		this.handlerThread = new EventHandlerThread(this, 10);
		handlerThread.start();
	}

	@Override
	public void fire(IEvent event) {
		if (event != null) {
			List<Consumer<IEvent>> list = listeners.get(event.getClass());
			List<Callable<Void>> list1 = Collections.emptyList();
			if (list != null) {
				list1 = list.stream()
						.<Callable<Void>>map(consumer -> {
							return new Callable<>() {

								@Override
								public Void call() {
									consumer.accept(event);
									return null;
								}
							};
						}).toList();
			}
			handlerThread.addEvent(new Pair<>(event, list1));
		}
	}

	@Override
	public void registerListenerObject(Object object) {
		Arrays.stream(object.getClass().getMethods())
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.filter(method -> method.isAnnotationPresent(EventSubscriber.class))
				.filter(this::checkMethod)
				.forEach(method -> registerListenerMethod(object, method));
	}

	@Override
	public void registerListenerClass(Class<?> clazz) {
		Arrays.stream(clazz.getMethods())
				.filter(method -> Modifier.isStatic(method.getModifiers()))
				.filter(method -> method.isAnnotationPresent(EventSubscriber.class))
				.filter(this::checkMethod)
				.forEach(method -> registerListenerMethod(null, method));
	}

	private void registerListenerMethod(Object target, Method method) {
		Consumer<IEvent> consumer = new Consumer<>() {

			@Override
			public void accept(IEvent t) {
				try {
					method.invoke(target, t);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		};

		@SuppressWarnings("unchecked")
		Class<? extends IEvent> eventClass = (Class<? extends IEvent>) method.getParameters()[0].getType();

		if (listeners.containsKey(eventClass)) {
			listeners.get(eventClass).add(consumer);
		} else {
			List<Consumer<IEvent>> list = new LinkedList<>();
			list.add(consumer);
			listeners.put(eventClass, list);
		}
	}

	private boolean checkMethod(Method method) {

		// TODO add warn messages

		if (method.getParameterCount() != 1) {
			throw new IllegalArgumentException();
		}

		if (!IEvent.class.isAssignableFrom(method.getParameters()[0].getType())) {
			throw new IllegalArgumentException();
		}

		return true;
	}

	@Override
	public void shutdown() {
		handlerThread.startShutdown();
		try {
			this.handlerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return name;
	}

}
