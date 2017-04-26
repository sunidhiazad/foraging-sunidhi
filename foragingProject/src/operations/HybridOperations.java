package operations;

import java.util.ArrayList;
import java.util.List;

import patterns.SectorWiseDivision;
import entities.Cell;
import entities.Path;
import entities.StripeQuadDivision;

public class HybridOperations {

	public static List<Path> getHybridSectorPaths(int gridSize, int numAgents) {
		List<Path> secPaths = new SectorWiseDivision(gridSize, numAgents).getPaths();
		for (int i = 0; i < secPaths.size(); i++) {
			Path p = secPaths.get(i);
			List<Cell> cells = p.getPath();
			cells = truncateCells(cells, gridSize);
			p.setPath(cells);
			secPaths.set(i, p);
		}
		return secPaths;
	}

	private static List<Cell> truncateCells(List<Cell> cells, int gridSize) {
		List<Cell> trunc = new ArrayList<Cell>();
		int low = gridSize / 8 - 1;
		int high = (7 * gridSize) / 8 - 1;
		for (Cell c : cells) {
			if (c.getX() > low && c.getX() <= high && c.getY() > low && c.getY() <= high)
				trunc.add(c);
		}
		return trunc;
	}

	public static List<Path> getHybridStripePaths(int gridSize, int numAgents) {
		return new StripeQuadDivision(gridSize, numAgents).getPaths();
	}
	
}
