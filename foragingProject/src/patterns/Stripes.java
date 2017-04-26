package patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.GeneralOperations;
import operations.Operations;
import operations.PathOperations;
import operations.VerifyPattern;
import entities.Cell;
import entities.Path;

public class Stripes {
	private int gridSize;
	private int numAgents;
	private int stripSize;
	private Cell origin;
	private List<Path> lstStripes = new ArrayList<Path>();

	// n & g both should be odd
	public Stripes(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		stripSize = gridSize / numAgents;
		origin = Operations.getOrigin(gridSize);
	}

	public List<Path> getPaths() {
		List<Cell> startPoints = getStartPoints();
		Path path;
		int k = 0;
		List<Cell> depPath;
		for (int i = 0; i < startPoints.size(); i = i + 2) {
			path = makePathObject(startPoints.get(i), makeUpperList(startPoints.get(i)));
			depPath = PathOperations.getDeltaXPath(origin, startPoints.get(i));
			depPath.add(startPoints.get(i));
			path.setDepPath(depPath);
			path.setId(k++);
			lstStripes.add(path);
			if (i < startPoints.size() - 1) {
				path = makePathObject(startPoints.get(i + 1), makeLowerList(startPoints.get(i + 1)));
				depPath = PathOperations.getDeltaXPath(origin, startPoints.get(i + 1));
				depPath.add(startPoints.get(i + 1));
				path.setDepPath(depPath);
				path.setId(k++);
				lstStripes.add(path);
			}

		}

		return assignStartAndDepTimes(lstStripes);
	}

	@SuppressWarnings("unchecked")
	private List<Path> assignStartAndDepTimes(List<Path> lst) {
		Map<Path, Integer> map = new HashMap<Path, Integer>();
		for (Path p : lst) {
			map.put(p, Operations.getDistance(p.getStart(), origin));
		}
		map = GeneralOperations.sortMapByValue(map);
		lst = new ArrayList<Path>(map.keySet());
		List<Integer> deps = new ArrayList<Integer>();
		int k = numAgents;
		for (Path p : lst) {
			p.setStartTime(k--);
			p.setDepTime(p.getStartTime() + p.getDepPath().size() - 2);
			deps.add(p.getDepTime());
		}

		int startExploringTime = Collections.max(deps);

		for (Path p : lst) {
			p.setPath(Operations.assignTimeSteps(p.getPath(), startExploringTime));
			p.setExpTime(startExploringTime + p.getPath().size());
			p.setCompletePath(addPathFromOrigin(p));
		}
		return updateDepPath(lst);

	}

	private List<Path> updateDepPath(List<Path> lst) {
		for (int i = 0; i < lst.size(); i++) {
			List<Cell> up = new ArrayList<Cell>();
			for (int j = 0; j < lst.get(i).getStartTime() - 1; j++) {
				up.add(origin);
			}
			up.addAll(lst.get(i).getDepPath());
			lst.get(i).setDepPath(up);
		}
		return lst;
	}

	private List<Cell> addPathFromOrigin(Path p) {
		List<Cell> comPath = new ArrayList<Cell>();
		comPath.addAll(p.getDepPath());
		comPath.addAll(p.getPath());
		return comPath;
	}

	private Path makePathObject(Cell start, List<Cell> lstCells) {
		List<Cell> rptPath = getRepeatPath(lstCells);
		return new Path(start, lstCells, rptPath);
	}

	private List<Cell> getRepeatPath(List<Cell> lstCells) {
		List<Cell> rptPath = new ArrayList<Cell>();
		List<Cell> path = lstCells;
		for (int i = 1; i <= stripSize; i++) {
			rptPath.add(path.get(path.size() - i));
		}
		return rptPath;
	}

	private List<Cell> makeLowerList(Cell start) {
		List<Cell> lstLower = new ArrayList<Cell>();
		for (int i = 0; i < gridSize; i = i + 2) {

			lstLower.add(start);

			for (int j = 1; j < stripSize; j++) {
				lstLower.add(Operations.plusY(start, j));
			}

			lstLower.add(Operations.plusX(lstLower.get(lstLower.size() - 1), 1));
			start = lstLower.get(lstLower.size() - 1);

			for (int j = 1; j < stripSize; j++) {
				lstLower.add(Operations.minusY(start, j));
			}

			start = Operations.plusX(lstLower.get(lstLower.size() - 1), 1);
		}
		return lstLower;
	}

	private List<Cell> makeUpperList(Cell start) {
		List<Cell> lstUpper = new ArrayList<Cell>();
		for (int i = 0; i < gridSize; i = i + 2) {

			lstUpper.add(start);

			for (int j = 1; j < stripSize; j++) {
				lstUpper.add(Operations.minusY(start, j));
			}

			lstUpper.add(Operations.plusX(lstUpper.get(lstUpper.size() - 1), 1));
			start = lstUpper.get(lstUpper.size() - 1);

			for (int j = 1; j < stripSize; j++) {
				lstUpper.add(Operations.plusY(start, j));
			}

			start = Operations.plusX(lstUpper.get(lstUpper.size() - 1), 1);
		}
		return lstUpper;
	}

	private List<Cell> getStartPoints() {
		int k = 0;
		List<Cell> startPoints = new ArrayList<Cell>();
		for (int i = stripSize - 1; i < gridSize; i = i + 2 * stripSize) {
			if (k == numAgents)
				break;
			startPoints.add(new Cell(0, i));
			k++;
			if (k == numAgents)
				break;
			startPoints.add(new Cell(0, i + 1));
			k++;
		}
		return startPoints;
	}

	public static void main(String[] args) {
		GeneralOperations.displayCollection(new Stripes(20, 4).getPaths());
		//VerifyPattern.drawIt(new Stripes(35,7).getPaths(), 1000);
	}

}
