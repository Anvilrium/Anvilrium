package anvilrium.client;

import java.awt.Color;
import java.io.Serial;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	@Serial
	private static final long serialVersionUID = 1450163981258734440L;

	public final static MainWindow INSTANCE = new MainWindow();

	private MainWindow() {
		this.setIconImage(null);
		this.setTitle("TEst");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setBackground(new Color(0));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void setBackground(Color bgColor) {
		super.setBackground(bgColor);
		this.getContentPane().setBackground(bgColor);
	}
	
	@Override
	public void setForeground(Color c) {
		super.setForeground(c);
		this.getContentPane().setForeground(c);
	}

}
