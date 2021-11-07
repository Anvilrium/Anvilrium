package anvilrium.common;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Collection;

import javax.swing.ImageIcon;

public class BoardTile implements Element {
	
	private final Board parentBoard;
	
	private ImageResource texture;

	public BoardTile(Board parentBoard) {
		this.parentBoard = parentBoard;
	}
	
	public BoardTile(Board parentBoard, ImageResource texture) {
		this(parentBoard);
		this.texture = texture;
	}

	/**
	 * @return the Board this tile belongs to
	 */
	public Board getParentBoard() {
		return parentBoard;
	}
	
	public void setTexture(ImageResource texture) {
		this.texture = texture;
	}
	
	public Image getTexture() {
		try {
			return texture.getImage();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return new ImageIcon().getImage();
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element getParent() {
		return parentBoard;
	}

	@Override
	public Collection<Element> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
}
