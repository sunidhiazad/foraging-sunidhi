package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import operations.Operations;
import operations.PathOperations;
import operations.VerifyPattern;
import entities.Cell;
import entities.ConfigurationLopOrt;
import entities.Path;
import enums.LoOrient;
import enums.MoveCode;

public class LopezOrtiz_numAgents4 {

	private List<ConfigurationLopOrt> lstConfigs;
	private List<Path> paths;
	private int gridSize;
	private Cell origin;
	private List<Cell> cellsToCover;

	public LopezOrtiz_numAgents4(int gridSize) {
		this.paths = new ArrayList<Path>();
		this.gridSize = gridSize;
		this.origin = Operations.getOrigin(gridSize);
		initializeConfigs();
		populatePaths();
		initializeCellsToCover();
		truncatePaths();
	}

	private void initializeCellsToCover() {
		cellsToCover = new ArrayList<Cell>();
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				cellsToCover.add(new Cell(i, j));
			}
		}
		cellsToCover.add(origin);
		cellsToCover.add(origin);
		cellsToCover.add(origin);

	}

	private void populatePaths() {
		for (int i = 0; i < lstConfigs.size(); i++) {
			paths.add(new Path(i, getPath(getCodesBy(lstConfigs.get(i)), origin)));
		}
	}

	private void truncatePaths() {
		List<Cell> cells;
		for (Path p : paths) {
			cells = truncateCells(p.getPath());
			cells = validatePaths(cells);
			cells = setTimeSteps(cells);
			p.setPath(cells);
		}
	}

	private List<Cell> setTimeSteps(List<Cell> cells) {
		Cell c;
		for (int i = 0; i < cells.size(); i++) {
			c = cells.get(i);
			c.setTimeStep(i);
			cells.set(i, c);
		}
		return cells;
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
				ucells.addAll(PathOperations.getValidPath(src, dest, gridSize));
			}
		}
		ucells.add(dest);
		return ucells;
	}

	private List<Cell> truncateCells(List<Cell> path) {
		List<Cell> ucells = new ArrayList<Cell>();
		for (Cell c : path) {
			if (Operations.isCellValid(c, gridSize) && cellsToCover.contains(c)) {
				ucells.add(c);
				cellsToCover.remove(c);
			}
		}
		return ucells;
	}

	private void initializeConfigs() {
		lstConfigs = new ArrayList<ConfigurationLopOrt>();
		List<MoveCode> lstCodes = Arrays.asList(MoveCode.PLUS_X, MoveCode.MINUS_Y, MoveCode.MINUS_X, MoveCode.PLUS_Y);
		List<LoOrient> lstOrients = Arrays.asList(LoOrient.get(1), LoOrient.get(2), LoOrient.get(3), LoOrient.get(4));
		lstConfigs.add(new ConfigurationLopOrt(lstCodes, lstOrients));

		lstCodes = Arrays.asList(MoveCode.MINUS_Y, MoveCode.MINUS_X, MoveCode.PLUS_Y, MoveCode.PLUS_X);
		lstOrients = Arrays.asList(LoOrient.get(6), LoOrient.get(7), LoOrient.get(8), LoOrient.get(5));
		lstConfigs.add(new ConfigurationLopOrt(lstCodes, lstOrients));

		lstCodes = Arrays.asList(MoveCode.MINUS_X, MoveCode.PLUS_Y, MoveCode.PLUS_X, MoveCode.MINUS_Y);
		lstOrients = Arrays.asList(LoOrient.get(3), LoOrient.get(4), LoOrient.get(1), LoOrient.get(2));
		lstConfigs.add(new ConfigurationLopOrt(lstCodes, lstOrients));

		lstCodes = Arrays.asList(MoveCode.PLUS_Y, MoveCode.PLUS_X, MoveCode.MINUS_Y, MoveCode.MINUS_X);
		lstOrients = Arrays.asList(LoOrient.get(8), LoOrient.get(5), LoOrient.get(6), LoOrient.get(7));
		lstConfigs.add(new ConfigurationLopOrt(lstCodes, lstOrients));
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

	public List<MoveCode> getCodesBy(ConfigurationLopOrt conf) {
		List<MoveCode> lstMC = new ArrayList<MoveCode>();
		MoveCode code1 = conf.getLstCodes().get(0);
		MoveCode code2 = conf.getLstCodes().get(1);
		MoveCode code3 = conf.getLstCodes().get(2);
		MoveCode code4 = conf.getLstCodes().get(3);

		List<MoveCode> orient1 = Arrays.asList(conf.getLstOrients().get(0).getFirst(), conf.getLstOrients().get(0).getSecond());
		List<MoveCode> orient2 = Arrays.asList(conf.getLstOrients().get(1).getFirst(), conf.getLstOrients().get(1).getSecond());
		List<MoveCode> orient3 = Arrays.asList(conf.getLstOrients().get(2).getFirst(), conf.getLstOrients().get(2).getSecond());
		List<MoveCode> orient4 = Arrays.asList(conf.getLstOrients().get(3).getFirst(), conf.getLstOrients().get(3).getSecond());

		int k = 0;
		while (lstMC.size() <= 10000) {
			lstMC.add(code1);

			k++;

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient1);
			}
			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient2);
			}

			lstMC.add(code2);

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient2);
			}

			k++;

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient3);
			}

			lstMC.add(code3);

			k++;

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient3);
			}

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient4);
			}

			lstMC.add(code4);

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient4);
			}

			k++;

			for (int i = 0; i < k; i++) {
				lstMC.addAll(orient1);
			}
		}
		return lstMC;
	}

	public List<Path> getPaths() {
		return paths;
	}

	public static void main(String[] args) {
		VerifyPattern.drawIt(new LopezOrtiz_numAgents4(60).getPaths(), 1100);
		;
	}

}
