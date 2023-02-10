package anvilrium.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkEnqueueThread extends Thread {
	
	private static volatile int count = 1;
	protected ExecutorService executorService;
	protected BlockingQueue<Callable<?>> workList;
	protected volatile boolean isShuttingDown;
	
	public WorkEnqueueThread(int threadCount) {
		this.workList = new LinkedBlockingQueue<>();
		this.executorService = Executors.newFixedThreadPool(threadCount);
		this.setName("WorkEnqueue-Thread-" + count++);
	}
	
	public boolean addWork(Callable<?> callable) {
		try {
			workList.put(callable);
			return true;
		} catch (InterruptedException e) {
			//TODO replace with Logger
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		return false;
	}
	
	@Override
	public void run() {
		while (!isInterrupted() && !isShuttingDown) {
			if (!workList.isEmpty()) {
				try {
					executorService.submit(workList.take());
				} catch (InterruptedException e) {
					//TODO replace with Logger
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
