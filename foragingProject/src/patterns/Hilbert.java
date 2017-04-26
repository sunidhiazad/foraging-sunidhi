package patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparators.PathComparator;
import operations.GeneralOperations;
import operations.Operations;
import operations.VerifyPattern;
import entities.Cell;
import entities.HierDividedArea;
import entities.Path;
import enums.HilOrient;
import enums.MoveCode;

public class Hilbert {

	private int gridSize;
	private int numAgents;
	private List<HierDividedArea> lstAreas;
	private List<Path> lstPaths;

	public Hilbert() {

	}

	public Hilbert(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		lstAreas = Operations.getHierDividedAreasForHilbert(gridSize, numAgents);
		lstPaths = new ArrayList<Path>();
	}

	public List<Path> getPaths() {
		int id = 0;
		for (HierDividedArea hda : lstAreas) {
			lstPaths.add(new Path(id++, hda.getStart(), getHilbertPath(hda.getSize(), hda.getHilOrientation(), hda.getStart())));
		}
		Cell start;
		for (Path p : lstPaths) {
			start = p.getStart();
			start.setPathid(p.getId());
			p.setStart(start);
		}
		lstPaths = Operations.setTimesAndDepPathsAndPathsFromOriginForHierDivision(lstPaths, numAgents, gridSize);
		Collections.sort(lstPaths, new PathComparator());
		return lstPaths;
	}

	public List<Cell> getHilbertPath(int size, HilOrient orientation, Cell start) {
		int order = GeneralOperations.getLog(size * size, 4);
		List<Integer> lstOrients = new ArrayList<Integer>();
		List<MoveCode> starts = new ArrayList<MoveCode>();
		lstOrients.add(orientation.getCode());
		starts = Operations.getUpdatedMCodeList(MoveCode.START, orientation.getMcode());
		int k = order;
		for (int j = 0; j < lstOrients.size(); j++) {
			if (k <= 0)
				break;
			starts = getMoveCodeList(lstOrients, starts);
			lstOrients = getSubOrientList(lstOrients);
			k--;
		}
		return getPath(starts, start);
	}

	private List<Cell> getPath(List<MoveCode> starts, Cell start) {
		List<Cell> lstCells = new ArrayList<Cell>();
		lstCells.add(start);
		for (int i = 0; i < starts.size(); i++) {
			switch (starts.get(i)) {
			case START:
				break;
			case PLUS_X:
				start = Operations.plusX(start, 1);
				lstCells.add(start);
				break;
			case MINUS_X:
				start = Operations.minusX(start, 1);
				lstCells.add(start);
				break;
			case PLUS_Y:
				start = Operations.plusY(start, 1);
				lstCells.add(start);
				break;
			case MINUS_Y:
				start = Operations.minusY(start, 1);
				lstCells.add(start);
				break;
			}
		}
		return lstCells;
	}

	private List<MoveCode> getMoveCodeList(List<Integer> lstOrients, List<MoveCode> starts) {
		List<MoveCode> lst = new ArrayList<MoveCode>();
		for (int i = 0; i < lstOrients.size(); i++) {
			lst.addAll(Operations.getUpdatedMCodeList(starts.get(i), HilOrient.getMCList(lstOrients.get(i))));
		}
		return lst;
	}

	private List<Integer> getSubOrientList(List<Integer> lstOrients) {
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < lstOrients.size(); i++) {
			lst.addAll(HilOrient.getSubs(lstOrients.get(i)));
		}
		return lst;
	}

	public static void main(String[] args) {
		// GeneralOperations.displayCollection(new Hilbert(16,4).getPaths());
		VerifyPattern.drawIt(new Hilbert(32, 4).getPaths(), 1000);
	}

}
