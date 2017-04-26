package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import operations.GeneralOperations;
import entities.Cell;

public class Draw extends JFrame {

	private static final long serialVersionUID = -7921632113264136091L;

	class Surface extends JPanel {
		List<Cell> cells;
		Cell main;

		public Surface(Cell main, List<Cell> cells) {
			this.cells = cells;
			this.main = main;
		}

		private static final long serialVersionUID = -6818568861920914025L;

		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			for (int i = 0; i < 1000; i = i + 10) {
				g2d.drawLine(0, i, 1000, i);
				g2d.drawLine(i, 0, i, 1000);

			}
			for (int i = 0; i < cells.size(); i++) {
				g2d.setColor(GeneralOperations.getRandomBrightColor());
				g2d.fillRect(cells.get(i).getX() * 10, cells.get(i).getY() * 10, 10, 10);
			}
			g2d.setColor(Color.BLACK);
			g2d.fillRect(main.getX() * 10, main.getY() * 10, 10, 10);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}
	}

	List<Cell> cells;
	Cell main;

	public Draw(Cell main, List<Cell> cells, int i) {
		this.main = main;
		this.cells = cells;
		initUI(i);
	}

	private void initUI(int i) {
		setTitle("Pattern# " + i);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Surface(main, cells));
		setSize(1000, 1000);
		setLocationRelativeTo(null);
	}

	public static void drawIt(final Cell main, final List<Cell> cells, final int i) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Draw lines = new Draw(main, cells, i);
				lines.setVisible(true);
			}
		});
	}

}
