package anvilrium.common;

import java.util.Collection;
import java.awt.Graphics;

public interface Element extends Renderable {
	
	Element getParent();
	
	Collection<? extends Element> getChildren();
	
	default void renderElement(Graphics g) {
		this.render(g);
		this.getChildren().forEach(element -> element.renderElement(g));
	}

}
