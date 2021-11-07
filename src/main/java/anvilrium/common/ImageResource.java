package anvilrium.common;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResource extends Resource {

	private Image cachedImage = null;

	public ImageResource(ResourceLocation resourceLocation) {
		super(resourceLocation);
	}

	public ImageResource(String resourceLocation) {
		super(resourceLocation);
	}

	public Image getImage() throws IOException {
		if (this.cachedImage == null) {
			this.cachedImage = ImageIO.read(getResourceAsStream());
		}
		return cachedImage;
	}

	@Override
	public void forceCacheReload() {
		super.forceCacheReload();
		this.cachedImage = null;
	}

}