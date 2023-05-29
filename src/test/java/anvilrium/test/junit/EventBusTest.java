package anvilrium.test.junit;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import anvilrium.common.eventbus.EventBus;
import anvilrium.common.eventbus.EventSubscriber;
import anvilrium.common.events.Event;

public class EventBusTest {

	@Test
	void testDirectListener() {
		EventBus eventBus = new EventBus("Test");
		eventBus.directlyRegisterListener(this::listenerMethod);
		eventBus.fire(new TestEvent());
	}

	@Test
	void testRegisterObject() {
		EventBus eventBus = new EventBus("Test");
		eventBus.registerListenerObject(new TestListener());
		TestEvent event = new TestEvent();
		eventBus.fire(event);
		assertTrue(event.isCancelled());
	}

	@Test
	void testRegisterObjectMalformedParameterCount() {
		EventBus eventBus = new EventBus("Test");
		assertThrowsExactly(IllegalArgumentException.class,
				() -> eventBus.registerListenerObject(new TestListenerMalformedParameterCount()));
	}

	@Test
	void testRegisterObjectMalformedParameterType() {
		EventBus eventBus = new EventBus("Test");
		assertThrowsExactly(IllegalArgumentException.class,
				() -> eventBus.registerListenerObject(new TestListenerMalformedParameterType()));
	}

	void listenerMethod(TestEvent event) {

	}

	class TestEvent extends Event {

	}

	public class TestListener {
		@EventSubscriber
		public void test(TestEvent event) {
			event.setCancelled(true);
		}
	}

	class TestListenerMalformedParameterCount {
		@EventSubscriber
		public void test(TestEvent event, Object object) {
			event.setCancelled(true);
		}
	}

	class TestListenerMalformedParameterType {
		@EventSubscriber
		public void test(Object event) {
			event.hashCode();
		}
	}

}
