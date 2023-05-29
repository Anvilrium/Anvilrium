package anvilrium.common.eventbus;

import anvilrium.common.events.Event;

public interface EventListener<T extends Event> {

	void post(T t);

}
