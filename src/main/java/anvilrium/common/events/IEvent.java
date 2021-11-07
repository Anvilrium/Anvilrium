package anvilrium.common.events;

import java.util.concurrent.CompletableFuture;

public interface IEvent {
	
	CompletableFuture<IEvent> getFuture();
	
	default String getEventName() {
		return getClass().getSimpleName();
	}

}
