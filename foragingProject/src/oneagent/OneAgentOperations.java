package oneagent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import operations.Operations;
import operations.PathOperations;
import operations.VerifyPattern;
import patterns.Hilbert;
import entities.Cell;
import entities.Path;
import enums.HilOrient;
import enums.LoOrient;
import enums.MoveCode;
import enums.Pattern;
import enums.Phase;

public class OneAgentOperations {

	private int gs;

	public List<Cell> getPath(int gridSize, Pattern pattern) {
		gs = gridSize;
		List<Cell> cells = new ArrayList<Cell>();
		switch (pattern) {
		case STRIPES:
			cells = getStripPath();
			break;
		case ZIG_ZAG:
			cells = setPhase(getZigZagpath(), Phase.EXPLORATION);
			break;
		case HILBERT:
			cells = getHilbertPath();
			break;
		case LOPEZ_ORTIZ1:
			cells = setPhase(getLopezPath(), Phase.EXPLORATION);
			break;
		default:
			break;
		}
		//System.out.println(Operations.isPathValid(cells));
		VerifyPattern.drawIt(getPathObject(cells), 1000);
		return cells;
	}

	private List<Cell> getLopezPath() {
		List<MoveCode> codes = new ArrayList<MoveCode>();
		for (int i = 1; i < 150; i = i + 2) {
			codes.add(MoveCode.MINUS_Y);
			for (int j = 0; j < i; j++) {
				codes.add(LoOrient.get(8).getFirst());
				codes.add(LoOrient.get(8).getSecond());
			}
			for (int j = 0; j < i; j++) {
				codes.add(LoOrient.get(7).getFirst());
				codes.add(LoOrient.get(7).getSecond());
			}
			codes.add(MoveCode.PLUS_Y);
			for (int j = 0; j < i + 1; j++) {
				codes.add(LoOrient.get(6).getFirst());
				codes.add(LoOrient.get(6).getSecond());
			}
			for (int j = 0; j < i + 1; j++) {
				codes.add(LoOrient.get(5).getFirst());
				codes.add(LoOrient.get(5).getSecond());
			}
		}
		return validatePaths(truncate(getPath(codes, Operations.getOrigin(gs))));
	}

	private List<Cell> validatePaths(List<Cell> cells) {
		List<Cell> ucells = new ArrayList<Cell>();
		Cell src = new Cell();
		Cell dest = new Cell();
		for (int i = 0; i < cells.size() - 1; i++) {
			src = cells.get(i);
			dest = cells.get(i + 1);
			if (Operations.getDistance(src, dest) == 1)
				ucells.add(src);
			else {
				ucells.addAll(PathOperations.getValidPath(src, dest, gs));
			}
		}
		ucells.add(dest);
		return ucells;
	}

	// private List<Cell> getHilbertPath() {
	// List<Cell> hil = new ArrayList<Cell>();
	// hil.addAll(getPathToTheEnd());
	// hil.addAll(setPhase(new Hilbert().getHilbertPath(gs, HilOrient.get(1),
	// new Cell(0, gs - 1)), Phase.EXPLORATION));
	// return hil;
	// }

	private List<Cell> getHilbertPath() {
		List<Cell> hil = new ArrayList<Cell>();
		hil.addAll(new Hilbert().getHilbertPath(gs / 2, HilOrient.get(5), new Cell(gs / 2, gs / 2 - 1)));
		hil.addAll(new Hilbert().getHilbertPath(gs / 2, HilOrient.get(4), new Cell(gs - 1, gs / 2)));
		hil.addAll(new Hilbert().getHilbertPath(gs / 2, HilOrient.get(4), new Cell(gs / 2 - 1, gs / 2)));
		hil.addAll(new Hilbert().getHilbertPath(gs / 2, HilOrient.get(5), new Cell(0, gs / 2 - 1)));
		return hil;
	}

	private List<Cell> getZigZagpath() {
		List<MoveCode> codes = new ArrayList<MoveCode>();
		for (int i = 1; i < 150; i = i + 2) {
			for (int j = 0; j < i; j++)
				codes.add(MoveCode.MINUS_Y);
			for (int j = 0; j < i; j++)
				codes.add(MoveCode.MINUS_X);
			for (int j = 0; j < i + 1; j++)
				codes.add(MoveCode.PLUS_Y);
			for (int j = 0; j < i + 1; j++)
				codes.add(MoveCode.PLUS_X);

		}
		return truncate(getPath(codes, Operations.getOrigin(gs)));
	}

	public List<Path> getPathObject(List<Cell> cells) {
		return new ArrayList<Path>(Arrays.asList(new Path(cells)));
	}

	private List<Cell> getStripPath() {
		List<Cell> strip = new ArrayList<Cell>();
		List<Cell> explore = new ArrayList<Cell>();
		strip.addAll(getPathToTheEnd());
		Cell end = new Cell(0, gs - 1);
		for (int i = 0; i < gs; i = i + 2) {
			explore.add(end);
			for (int j = 1; j < gs; j++) {
				explore.add(Operations.minusY(end, j));
			}
			explore.add(Operations.plusX(explore.get(explore.size() - 1), 1));
			end = explore.get(explore.size() - 1);
			for (int j = 1; j < gs; j++) {
				explore.add(Operations.plusY(end, j));
			}
			end = Operations.plusX(explore.get(explore.size() - 1), 1);
		}
		strip.addAll(setPhase(explore, Phase.EXPLORATION));
		return strip;
	}

	private List<Cell> getPathToTheEnd() {
		List<Cell> end = new ArrayList<Cell>();
		Cell origin = Operations.getOrigin(gs);
		end.addAll(PathOperations.getDeltaXPath(origin, new Cell(0, origin.getY())));
		end.addAll(PathOperations.getDeltaYPath(new Cell(0, origin.getY()), new Cell(0, gs - 1)));
		return setPhase(end, Phase.INITIAL_DEPLOYMENT);
	}

	private List<Cell> setPhase(List<Cell> end, Phase phase) {
		for (Cell c : end)
			c.setPhase(phase);
		return end;
	}

	private List<Cell> getPath(List<MoveCode> codes, Cell start) {
		List<Cell> lstCells = new ArrayList<Cell>();
		lstCells.add(start);
		for (int i = 0; i < codes.size(); i++) {
			switch (codes.get(i)) {
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

	private List<Cell> truncate(List<Cell> path) {
		List<Cell> ucells = new ArrayList<Cell>();
		for (Cell c : path) {
			if (Operations.isCellValid(c, gs))
				ucells.add(c);
		}
		return ucells;
	}
	
	public static void main(String[] args) {
		new OneAgentOperations().getPath(64, Pattern.ZIG_ZAG);
	}

}
