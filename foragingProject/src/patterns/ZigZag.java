package patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import operations.GeneralOperations;
import operations.Operations;
import operations.VerifyPattern;
import comparators.PathComparator;
import entities.Cell;
import entities.HierDividedArea;
import entities.Path;
import enums.MoveCode;
import enums.ZigZagOrient;

public class ZigZag {
	private int gridSize;
	private int numAgents;

	public ZigZag(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
	}

	public List<Path> getPaths() {
		List<HierDividedArea> lstAreas = Operations.getHierDividedAreasForZigZag(gridSize, numAgents);
		List<Path> lstPaths = new ArrayList<Path>();
		int id = 0;
		for (HierDividedArea hda : lstAreas) {
			lstPaths.add(new Path(id++, hda.getStart(), getZigZagPath(hda.getSize(), hda.getZigOrientation(), hda.getStart())));
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

	public List<Cell> getZigZagPath(int size, ZigZagOrient orientation, Cell start) {
		List<MoveCode> lstMcodes = new ArrayList<MoveCode>();
		lstMcodes.add(MoveCode.START);
		for (int i = 1; i < size; i++) {
			if (i % 2 == 1) {
				lstMcodes.add(orientation.getOddTransMCode());
				for (int j = 0; j < i; j++) {
					lstMcodes.add(orientation.getMcArray()[0][0]);
				}
				for (int j = 0; j < i; j++) {
					lstMcodes.add(orientation.getMcArray()[0][1]);
				}
			} else {
				lstMcodes.add(orientation.getEvenTransMCode());
				for (int j = 0; j < i; j++) {
					lstMcodes.add(orientation.getMcArray()[1][0]);
				}
				for (int j = 0; j < i; j++) {
					lstMcodes.add(orientation.getMcArray()[1][1]);
				}
			}
		}
		return getPath(lstMcodes, start);
	}

	public void displayMCodeList(List<MoveCode> mcode, int i) {
		System.out.print(i + " : ");
		for (MoveCode m : mcode) {
			System.out.print(m.getCode());
		}
		System.out.println();
	}

	private List<Cell> getPath(List<MoveCode> lstMcodes, Cell start) {
		List<Cell> lstCells = new ArrayList<Cell>();
		lstCells.add(start);
		for (int i = 0; i < lstMcodes.size(); i++) {
			switch (lstMcodes.get(i)) {
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

	public static void main(String[] args) {
		// GeneralOperations.displayCollection(new ZigZag(64, 4).getPaths());
		VerifyPattern.drawIt(new ZigZag(32,4).getPaths(), 1000);
	}
}
