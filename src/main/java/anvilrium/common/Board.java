package anvilrium.common;

import java.awt.Graphics;
import java.util.Collection;

public class Board implements Element {
	
	protected TwoDimensionaArraylList<BoardTile> tiles;
	
	protected final int tileSize;
	
	protected Table parentTable;
	
	public Board(int width, int height, int tileSize) {
		this.tiles = new TwoDimensionaArraylList<>(width, height);
		this.tileSize = tileSize;
		
		// TODO remove test code
		ImageResource imageResource = new ImageResource("/image.png");
		for (int row = 0; row < tiles.getRowCount(); row++) {
			for (int column = 0; column < tiles.getColumnCount(); column++) {
				BoardTile tile = new BoardTile(this);
				tile.setTexture(imageResource);
				setTile(tile, row, column);
			}
		}
	}
	
	public TwoDimensionaArraylList<BoardTile> getTiles() {
		return tiles;
	}
	
	public void setTile(BoardTile tile, int x, int y) {
		tiles.set(x, y, tile);
	}
	
	@Override
	public void render(Graphics g) {
		int size = tileSize;
		int zoom = parentTable.getZoom();
		for (int row = 0; row < tiles.getRowCount(); row++) {
			for (int column = 0; column < tiles.getColumnCount(); column++) {
				BoardTile tile = tiles.get(row, column);
				g.drawImage(tile.getTexture(), row*zoom*size, column*zoom*size, zoom*size, zoom*size, null);
			}
		}
	}
	
	@Override
	public Element getParent() {
		return parentTable;
	}

	@Override
	public Collection<? extends Element> getChildren() {
		return tiles.toArrayList();
	}

}
