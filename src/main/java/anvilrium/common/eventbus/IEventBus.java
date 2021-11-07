package anvilrium.common.eventbus;

import anvilrium.common.events.IEvent;

public interface IEventBus {
	
	void fire(IEvent event);
	
	default void register(Object object) {
		if (object instanceof Class<?> clazz) {
			registerListenerClass(clazz);
		} else {
			registerListenerObject(object);
		}
	}
	
	void registerListenerObject(Object object);
	
	void registerListenerClass(Class<?> clazz);
	
	void shutdown();
	
	String getName();

}
