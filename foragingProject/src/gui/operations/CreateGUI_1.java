package gui.operations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Cell;

public class CreateGUI_1 extends JPanel {

	private static final long serialVersionUID = 9060555925259049531L;
	private int gridSize;
	// private List<List<Cell>> paths;

	private JFrame guiFrame = new JFrame();

	// private Container pane = guiFrame.getContentPane();

	// private List<Line> grid = new ArrayList<Line>();

	public CreateGUI_1(int gridSize, List<List<Cell>> paths) {
		this.gridSize = gridSize;
		// this.paths = paths;
		setFrameProperties();

		guiFrame.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		for (int i = 0; i <= gridSize * 10; i = i + 10) {
			g2d.drawLine(0, i, 10 * gridSize, i);
			g2d.drawLine(i, 0, i, 10 * gridSize);
		}
	}

	private void setFrameProperties() {
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Foraging on grid of size " + gridSize);
		guiFrame.setSize(gridSize * 10 + 50, gridSize * 10 + 50);
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setResizable(false);
		guiFrame.setAlwaysOnTop(true);
		guiFrame.setLayout(null);
	}

	public static void main(String[] args) {
		new CreateGUI_1(100, null);
	}

}
