package anvilrium;

import anvilrium.client.Renderer;

public class Game {
	
	public Game() {
		Thread thread = new Thread(new Renderer(), "Renderer-Thread");
		thread.start();
	}
	
	private void shutdown() {
	}
	
	private void showCrashReport(Throwable t) {
		t.printStackTrace();
	}
    
}
