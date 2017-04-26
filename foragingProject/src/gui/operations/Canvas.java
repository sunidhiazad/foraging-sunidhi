package gui.operations;

import javax.swing.JFrame;

import operations.GeneralOperations;

public class Canvas extends JFrame {

	private static final long serialVersionUID = 8408895939284859380L;
	private static Canvas instance = null;

	private Canvas(int gridSize) {
		setSize(1200, 1200);
		setBackground(GeneralOperations.getRandomBrightColor());
		getContentPane().add(PaintCanvas.getInstance(gridSize));
	}

	public static Canvas getInstance(int gridSize) {
		if (instance == null) {
			instance = new Canvas(gridSize);
		}
		return instance;
	}

	public static void main(String[] args) {
		Canvas obj = getInstance(100);
		obj.setVisible(true);
	}
}
