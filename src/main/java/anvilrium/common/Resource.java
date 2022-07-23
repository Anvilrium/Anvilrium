package anvilrium.common;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Resource {
	
	private final ResourceLocation resourceLocation;
	
	private InputStream cachedInputStream = null;
	
	public Resource(ResourceLocation resourceLocation) {
		this.resourceLocation = resourceLocation;
	}
	
	public Resource(String resourceLocation) {
		this(new ResourceLocation(resourceLocation));
	}
	
	public InputStream getResourceAsStream() throws FileNotFoundException {
		if (cachedInputStream == null) {
			cachedInputStream = FileUtils.getFileFromResourceAsStream(resourceLocation.getLocation());
		}
		return cachedInputStream;
	}
	
	public void forceCacheReload() {
		this.cachedInputStream = null;
	}

}
