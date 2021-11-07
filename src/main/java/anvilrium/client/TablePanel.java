package anvilrium.client;

import java.awt.Graphics;
import java.io.Serial;

import javax.swing.JPanel;

import anvilrium.common.Table;

public class TablePanel extends JPanel {

	@Serial
	private static final long serialVersionUID = 2103833852215917226L;
	
	private int zoom = 10;
	
	private Table table;

	public TablePanel() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		table.render(g, zoom);
//		try {
//			ImageResource imageResource = new ImageResource("/image.png");
//			Image logo = imageResource.getImage();
//			ImageObserver observer = null;
//			g.setClip(getBounds());
//			this.getParent();
////			g.drawImage(logo, 100, 100, 320, 320, getFocusCycleRootAncestor());
//			g.drawImage(logo, 100, 100 + 220, logo.getWidth(observer)*zoom, logo.getHeight(observer)*zoom, observer);
//		} catch (IOException e) {
//		}
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
		this.repaint();
	}
	
	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

}
