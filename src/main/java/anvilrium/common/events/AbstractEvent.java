package anvilrium.common.events;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractEvent implements IEvent {

	private CompletableFuture<IEvent> future = new CompletableFuture<>();
	
	@Override
	public CompletableFuture<IEvent> getFuture() {
		return future;
	}

}
