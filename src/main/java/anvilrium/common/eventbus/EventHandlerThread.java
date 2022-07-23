package anvilrium.common.eventbus;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import anvilrium.common.Pair;
import anvilrium.common.events.IEvent;

public class EventHandlerThread extends Thread {

	protected IEventBus eventBus;
	protected ExecutorService executorService;
	protected volatile boolean isShuttingDown;
	protected BlockingQueue<Pair<IEvent, Collection<Callable<Void>>>> eventQueue;

	public EventHandlerThread(EventBus bus, int threadCount) {
		this.eventBus = bus;
		this.executorService = Executors.newFixedThreadPool(threadCount);
		this.eventQueue = new LinkedBlockingQueue<>();
		this.setName(eventBus.getName() + "-Handler-Thread");
	}

	public boolean addEvent(Pair<IEvent, Collection<Callable<Void>>> listeners) {
		if (!isShuttingDown) {
			eventQueue.add(listeners);
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if (Thread.currentThread() != this) {
			throw new IllegalCallerException();
		}

		while (!(isInterrupted() || (isShuttingDown && eventQueue.isEmpty()))) {
			try {
				Pair<IEvent, Collection<Callable<Void>>> eventPair = eventQueue.take();
				executorService.invokeAll(eventPair.getSecond());
				eventPair.getFirst().getFuture().complete(eventPair.getFirst());
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void startShutdown() {
		isShuttingDown = true;
	}

}
