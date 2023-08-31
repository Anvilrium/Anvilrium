package anvilrium.common.events;

public class Event {
	
	private boolean cancelled;
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public boolean setCancelled(boolean value) {
		boolean changed = cancelled != value;
		cancelled = value;
		return changed;
	}
	
	public String getEventName() {
		return getClass().getName();
	}

}
