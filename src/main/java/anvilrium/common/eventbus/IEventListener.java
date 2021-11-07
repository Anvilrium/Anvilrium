package anvilrium.common.eventbus;

import java.util.EventListener;

import anvilrium.common.events.IEvent;

public interface IEventListener extends EventListener {
	
	void call(IEvent event);
	
	void register(IEvent event);

}
