package patterns;

import java.util.ArrayList;
import java.util.List;

import operations.HybridOperations;
import operations.VerifyPattern;
import entities.Path;

public class Hybrid {
	private int gridSize;
	private int numAgents;
	//private Cell origin;
	private List<Path> paths;

	// gridSize must be divisible by 8
	public Hybrid(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		//this.origin = Operations.getOrigin(gridSize);
		this.paths = new ArrayList<Path>();
	}

	public List<Path> getPaths() {
		List<Path> secPaths = HybridOperations.getHybridSectorPaths(gridSize, numAgents / 2);
		List<Path> strPaths = HybridOperations.getHybridStripePaths(gridSize, numAgents / 2);
		paths.addAll(secPaths);
		paths.addAll(strPaths);
		return paths;
	}

	public static void main(String[] args) {
		VerifyPattern.drawIt(new Hybrid(64, 64).getPaths(), 700);
	}
}
