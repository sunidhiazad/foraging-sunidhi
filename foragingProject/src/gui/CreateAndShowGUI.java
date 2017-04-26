package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import operations.GeneralOperations;

public class CreateAndShowGUI {

	private int numAgents;
	private int gridSize;

	JFrame guiFrame = new JFrame();
	Container pane = guiFrame.getContentPane();
	JButton[][] grid = null;
	Color colr = GeneralOperations.getRandomLightColor();

	public CreateAndShowGUI(int gridSize, int numAgents) {
		this.numAgents = numAgents;
		this.gridSize = gridSize;
		setFrameProperties();
		addGridToFrame();
	}

	private void setFrameProperties() {
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Foraging on grid of size " + gridSize + " with " + numAgents + " agents");
		guiFrame.setSize(1200, 1200);
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setResizable(false);
		guiFrame.setAlwaysOnTop(true);
		guiFrame.setLayout(null);
	}

	private void addGridToFrame() {
		grid = new JButton[gridSize][gridSize];
		JPanel btnPanel = new JPanel();
		btnPanel.setVisible(true);
		btnPanel.setLayout(new GridLayout(gridSize, gridSize));

		for (int j = 0; j < gridSize; j++) {
			for (int i = 0; i < gridSize; i++) {
				grid[i][j] = new JButton();
				setBorders(i, j);
				btnPanel.add(grid[i][j]);
			}
		}
		btnPanel.setBounds(5, 5, 1180, 1180);
		pane.add(btnPanel);
		guiFrame.setVisible(true);
	}

	private void setBorders(int i, int j) {
		grid[i][j].setBackground(colr);
		grid[i][j].setBorder(new LineBorder(Color.BLACK, 1));
		if (gridSize % 2 == 0) {
			if (i == gridSize / 2 - 1)
				grid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
			if (j == gridSize / 2 - 1)
				grid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
			if ((i == gridSize / 2 - 1 || i == gridSize / 2) && (j == gridSize / 2 - 1 || j == gridSize / 2)) {
				grid[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
			}
		} else {
			if (i == gridSize / 2 + 1 || i == gridSize / 2)
				grid[i][j].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.BLACK));
			if (j == gridSize / 2 + 1 || j == gridSize / 2)
				grid[i][j].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.BLACK));
			if (i == gridSize / 2 && j == gridSize / 2)
				grid[i][j].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
		}

	}

	public static void main(String[] args) {
		new CreateAndShowGUI(100, 25);
	}

}
