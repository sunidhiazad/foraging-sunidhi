package operations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entities.Cell;
import entities.Path;

public class VerifyPattern extends JFrame {

	private static final long serialVersionUID = 3180963124661102326L;

	class Surface extends JPanel {
		List<Path> paths;

		public Surface(List<Path> paths) {
			this.paths = paths;
		}

		private static final long serialVersionUID = -6818568861920914025L;

		private void doDrawing(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < paths.size(); i++) {
				List<Cell> p = paths.get(i).getPath();
				// List<Cell> p = paths.get(i).getCompletePath();
				g2d.setColor(GeneralOperations.getRandomBrightColor());
//				for (int j = 0; j < p.size() - 1; j++) {
//					g2d.drawLine(p.get(j).getX() * 10, p.get(j).getY() * 10, p.get(j + 1).getX() * 10, p.get(j + 1).getY() * 10);
//				}
				for (int j = 0; j < p.size() - 1; j++) {
					g2d.drawLine(p.get(j).getX() * 7, p.get(j).getY() * 7, p.get(j + 1).getX() * 7, p.get(j + 1).getY() * 7);
				}
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}
	}

	List<Path> paths;

	public VerifyPattern(List<Path> paths, int scrSize) {
		this.paths = paths;
		initUI(scrSize);
	}

	private void initUI(int scrSize) {
		setTitle("Patterns");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Surface(paths));
		setSize(scrSize, scrSize);
		setLocationRelativeTo(null);
	}

	public static void drawIt(List<Path> pathList, int screensize) {
		final int scrSize = screensize;
		final List<Path> paths = pathList;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				VerifyPattern lines = new VerifyPattern(paths, scrSize);
				lines.setVisible(true);
			}
		});
	}

}
