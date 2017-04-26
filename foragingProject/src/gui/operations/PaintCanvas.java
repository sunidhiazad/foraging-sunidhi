package gui.operations;

import javax.swing.JPanel;

import operations.GeneralOperations;

public class PaintCanvas extends JPanel{

	private static final long serialVersionUID = 1603573144330543594L;

	private static PaintCanvas instance = null;

	private PaintCanvas(int gridSize) {
		setBounds(10, 10, 500, 500);
		setBackground(GeneralOperations.getRandomBrightColor());
		addGrid();
	}

	private void addGrid() {
		
	}

	public static PaintCanvas getInstance(int gridSize) {
		if (instance == null) {
			instance = new PaintCanvas(gridSize);
		}
		return instance;
	}
}
