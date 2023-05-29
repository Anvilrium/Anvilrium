package anvilrium.common.eventbus;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import anvilrium.common.events.Event;

public class EventBus {

	public static final EventBus MAIN_EVENT_BUS = new EventBus("MainEventBus");

	protected final Map<Class<? extends Event>, List<EventListener<?>>> listeners;
	protected final String name;
	protected final Logger LOGGER;

	public EventBus(String name) {
		this.name = name;
		this.LOGGER = LogManager.getLogger(name + "-Event-Handler");
		this.listeners = new ConcurrentHashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <T extends Event> void fire(T event) {
		if (event != null) {
			List<EventListener<?>> list = listeners.get(event.getClass());
			if (list != null) {
				for (EventListener<?> listener : list) {
					((EventListener<T>)listener).post(event);
				}
			}
		}
	}

	public void registerListenerObject(Object object) {
		Arrays.stream(object.getClass().getMethods())
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.filter(this::checkMethod)
				.forEach(method -> registerListenerMethod(object, object.getClass(), method));
	}

	public void registerListenerClass(Class<?> clazz) {
		Arrays.stream(clazz.getMethods())
				.filter(method -> Modifier.isStatic(method.getModifiers()))
				.filter(this::checkMethod)
				.forEach(method -> registerListenerMethod(null, clazz, method));
	}
	
	public <T extends Event> void directlyRegisterListener(EventListener<T> listener) {
		try {
			Method method = listener.getClass().getMethod("post", Event.class);
			@SuppressWarnings("unchecked")
			Class<T> eventClass = (Class<T>) method.getParameters()[0].getType();
			addListener(eventClass, listener);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private <T extends Event> void registerListenerMethod(Object target, Class<?> targetClass, Method method) {
		@SuppressWarnings("unchecked")
		Class<T> eventClass = (Class<T>) method.getParameters()[0].getType();

		try {
			boolean isStatic = Modifier.isStatic(method.getModifiers());
			MethodType methodType = MethodType.methodType(void.class, eventClass);
			Lookup lookup = MethodHandles.lookup();
			MethodHandle methodHandle;
			if (isStatic) {
				methodHandle = lookup.findStatic(targetClass, method.getName(), methodType);
			} else {
				methodHandle = lookup.findVirtual(targetClass, method.getName(), methodType);
			}
			EventListener<T> eventListener = createEventListenerFromMethodHandle(target, methodHandle, eventClass);
			addListener(eventClass, eventListener);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private <T extends Event> EventListener<T> createEventListenerFromMethodHandle(Object target, MethodHandle methodHandle,
			Class<T> eventClass) {
		EventListener<T> eventListener;
		if (target == null) {
			eventListener = (event) -> {
				try {
					methodHandle.invoke(event);
				} catch (Throwable e) {
					LOGGER.error("Exception during event invocation");
					LOGGER.catching(e);
				}
			};
		} else {
			eventListener = (event) -> {
				try {
					methodHandle.invoke(target, event);
				} catch (Throwable e) {
					LOGGER.error("Exception during event invocation");
					LOGGER.catching(e);
				}
			};
		}
		return eventListener;
	}

	private <T extends Event> void addListener(Class<? extends Event> eventClass, EventListener<?> eventListener) {
		if (listeners.containsKey(eventClass)) {
			listeners.get(eventClass).add(eventListener);
		} else {
			List<EventListener<?>> list = new LinkedList<>();
			list.add(eventListener);
			listeners.put(eventClass, list);
		}
	}

	private boolean checkMethod(Method method) {
		if (!method.isAnnotationPresent(EventSubscriber.class)) {
			return false;
		}
		
		if (method.getParameterCount() != 1) {
			throw new IllegalArgumentException();
		}

		if (!Event.class.isAssignableFrom(method.getParameters()[0].getType())) {
			throw new IllegalArgumentException();
		}

		return true;
	}

	public String getName() {
		return name;
	}

}
