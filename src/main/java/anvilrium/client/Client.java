package anvilrium.client;

import anvilrium.server.Server;

public class Client {
	
	private Server integratedServer;
	
	private static final Client INSTANCE = new Client();
	
	public static Client getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) {
		INSTANCE.startup(args);
	}
	
	private void startup(String[] args) {
		try {
			
		} catch (Throwable t) {
			
		}
		
	}
	
	public void shutdown() {
		integratedServer.shutdown();
	}

}
