package anvilrium.common;

import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import anvilrium.client.TablePanel;

public class Table implements Element {
	
	protected TablePanel parentPanel;
	
	protected List<Element> elements = new LinkedList<>();
	
	public TablePanel getParentPanel() {
		return parentPanel;
	}
	
	public void addElementAtIndex(Element element, int index) {
		elements.add(index, element);
	}
	
	public void addElement(Element element) {
		elements.add(element);
	}

	/**
	 * Don't use this
	 */
	public void setParentPanel(TablePanel parentPanel) {
		if (parentPanel == null) {
			this.parentPanel = parentPanel;
		} else {
			throw new UnsupportedOperationException("Table#setParentPanel should only be called once");
		}	
	}

	public void render(Graphics g, int zoom) {
		
	}

	public int getZoom() {
		return parentPanel.getZoom();
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public Element getParent() {
		return null;
	}

	@Override
	public Collection<Element> getChildren() {
		return elements;
	}

}
