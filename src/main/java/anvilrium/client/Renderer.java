package anvilrium.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import anvilrium.client.events.PreRenderEvent;
import anvilrium.common.Board;
import anvilrium.common.Table;
import anvilrium.common.eventbus.EventBus;

public class Renderer implements Runnable {

	public static final EventBus RENDER_EVENT_BUS = new EventBus("RenderEventBus");
	
	MainWindow mainWindow = MainWindow.INSTANCE;
	

	TablePanel tablePanel = new TablePanel();

	@Override
	public void run() {
		startup();
		RENDER_EVENT_BUS.register(this);
		renderLoop();
	}
	
	private void renderLoop() {
		//TODO remove while true loop
		while (true) {
			PreRenderEvent preRenderEvent = new PreRenderEvent();
			RENDER_EVENT_BUS.fire(preRenderEvent);
			try {
				preRenderEvent.getFuture().get();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				//TODO replace with Logger
				e.printStackTrace();
			} catch (ExecutionException e) {
				//TODO replace with Logger
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				//TODO replace with Logger
				e.printStackTrace();
			}
			tablePanel.paint(tablePanel.getGraphics());
		}
	}
	
	public void startup() {
		Board board = new Board(2, 2, 32);
		Table table = new Table();
		table.addElement(board);
		tablePanel.setTable(table);
		tablePanel.setPreferredSize(new Dimension(1920, 540));
		mainWindow.add(tablePanel, BorderLayout.NORTH);
		JPanel pna = new JPanel();
		pna.setBackground(new Color(0));
		JButton bplus = new JButton("+");
		final JLabel zoomText = new JLabel();
		zoomText.setForeground(new Color(0xFFFFFF));
		zoomText.setText(Integer.toString(tablePanel.getZoom()));
		zoomText.setBounds(50, 50, 150, 20);
		bplus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablePanel.setZoom(tablePanel.getZoom() + 1);
				zoomText.setText(Integer.toString(tablePanel.getZoom()));
			}
		});
		JButton bminus = new JButton("-");
		bminus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tablePanel.setZoom(tablePanel.getZoom() - 1);
				zoomText.setText(Integer.toString(tablePanel.getZoom()));
			}
		});
		pna.add(bminus);
		pna.add(bplus);
		pna.add(zoomText);
		mainWindow.add(pna, BorderLayout.SOUTH);
		mainWindow.setVisible(true);
	}

}
