package operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import entities.Cell;
import entities.Collision;
import entities.Sector;
import entities.SectorTest;

public class SectorCorrectness {
	static Boolean gridCovered;
	static int numCollisions;
	static Boolean validPaths;
	static BigDecimal percentExtra;
	static int gridSize;
	static int numAgents;
	static List<Sector> sectors;
	static List<Cell> grid;
	static List<Collision> collisions=new ArrayList<Collision>();

	public static SectorTest validateSectors(SectorTest st, List<Sector> sec) {
		gridSize = st.getGridSize();
		numAgents = st.getNumAgents();
		sectors = sec;
		grid = Operations.getGrid(gridSize);
		gridCovered = checkCover();
		validPaths = checkValidity();
		numCollisions = checkCollisions();
		percentExtra = getExtra();
		return new SectorTest(st.getId(), gridSize, numAgents, st.getInPath(), gridCovered, numCollisions, validPaths, percentExtra, Operations.getStdDeviation(sec), Operations.getAverage(sec));
	}

	private static BigDecimal getExtra() {
		int cells = 0;
		for (Sector s : sectors) {
			cells += s.getFinalPath().size();
		}
		int num = cells - gridSize * gridSize;
		int den = gridSize * gridSize;
		return GeneralOperations.divideBD(num, den).multiply(new BigDecimal(100));
	}

	private static int checkCollisions() {
		Map<Cell, List<Integer>> map = new HashMap<Cell, List<Integer>>();
		for (Cell c : grid) {
			if (!c.equals(Operations.getOrigin(gridSize))) {
				map.put(c, getTimeSteps(c));
			}
		}
		List<Integer> timesteps;
		int numCollisions = 0;
		for (Map.Entry<Cell, List<Integer>> entry : map.entrySet()) {
			timesteps = entry.getValue();
			if (containsDuplicates(timesteps))
				numCollisions++;

		}
		return numCollisions;
	}

	private static boolean containsDuplicates(List<Integer> timesteps) {
		int size = timesteps.size();
		int upSize = new HashSet<Integer>(timesteps).size();
		return size != upSize;
	}

	private static List<Integer> getTimeSteps(Cell c) {
		List<Integer> list = new ArrayList<Integer>();
		List<Cell> path;
		for (Sector s : sectors) {
			path = s.getFinalPath();
			if (path.contains(c)) {
				for (int i = 0; i < path.size(); i++) {
					if (path.get(i).equals(c)) {
						list.add(s.getStartTime() + i + 1);
					}
				}
			}
		}
		return list;
	}

	private static Boolean checkValidity() {
		Boolean valid = true;
		for (Sector s : sectors) {
			if (!Operations.isPathValid(s.getFinalPath())) {
				valid = false;
				break;
			}
		}
		return valid;
	}

	private static Boolean checkCover() {
		List<Cell> coveredCells = new ArrayList<Cell>();
		for (Sector s : sectors) {
			coveredCells.addAll(s.getFinalPath());
		}
		Boolean covered = true;
		for (Cell c : grid) {
			if (!coveredCells.contains(c)) {
				covered = false;
				break;
			}
		}
		return covered;
	}

}
