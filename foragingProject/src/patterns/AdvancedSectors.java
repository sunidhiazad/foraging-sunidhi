package patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import operations.PathOperations;
import operations.VerifyPattern;

import commHelpers.RepeatPath;

import entities.Cell;
import entities.Path;

public class AdvancedSectors {
	private int gridSize;
	private int numAgents;
	private List<Path> zzpaths;
	private List<Path> paths;

	public AdvancedSectors() {
		this.gridSize = 128;
		this.numAgents = 8;
		this.zzpaths = new ZigZag(gridSize, numAgents / 2).getPaths();
		this.paths = new ArrayList<Path>();
		initializePaths();
		adjustDeploymentPaths();
		adjustExplorationPaths();
		assignRepeatPaths();
		assignTimeSteps();
		// GeneralOperations.displayCollection(paths);
		// VerifyPattern.drawIt(paths, 1300);
	}

	private void assignRepeatPaths() {
		List<Cell> rpt0 = PathOperations.getDeltaXPath(new Cell(62, 18), new Cell(18, 63));
		List<Cell> rpt1 = PathOperations.getDeltaXPath(new Cell(62, 19), new Cell(19, 63));

		List<Cell> rpt2 = PathOperations.getDeltaXPath(new Cell(65, 18), new Cell(109, 63));
		List<Cell> rpt3 = PathOperations.getDeltaXPath(new Cell(65, 19), new Cell(108, 63));

		List<Cell> rpt4 = PathOperations.getDeltaXPath(new Cell(65, 109), new Cell(109, 64));
		List<Cell> rpt5 = PathOperations.getDeltaXPath(new Cell(65, 108), new Cell(108, 64));

		List<Cell> rpt6 = PathOperations.getDeltaXPath(new Cell(62, 109), new Cell(18, 64));
		List<Cell> rpt7 = PathOperations.getDeltaXPath(new Cell(62, 108), new Cell(19, 64));

		rpt0.add(new Cell(18, 63));
		rpt1.add(new Cell(19, 63));
		rpt2.add(new Cell(109, 63));
		rpt3.add(new Cell(108, 63));
		rpt4.add(new Cell(109, 64));
		rpt5.add(new Cell(108, 64));
		rpt6.add(new Cell(18, 64));
		rpt7.add(new Cell(19, 64));

		getPathWithID(0).setRepeat(new RepeatPath(rpt0));
		getPathWithID(1).setRepeat(new RepeatPath(rpt1));
		getPathWithID(2).setRepeat(new RepeatPath(rpt2));
		getPathWithID(3).setRepeat(new RepeatPath(rpt3));
		getPathWithID(4).setRepeat(new RepeatPath(rpt4));
		getPathWithID(5).setRepeat(new RepeatPath(rpt5));
		getPathWithID(6).setRepeat(new RepeatPath(rpt6));
		getPathWithID(7).setRepeat(new RepeatPath(rpt7));

	}

	private void assignTimeSteps() {
		Path temp;
		List<Cell> cells;
		for (int i = 0; i < paths.size(); i++) {
			temp = paths.get(i);
			cells = temp.getPath();
			cells = assign(cells, temp.getDepPath().size());
			temp.setPath(cells);
			paths.set(i, temp);
		}
	}

	private List<Cell> assign(List<Cell> cells, int start) {
		for (Cell c : cells) {
			c.setTimeStep(start++);
		}
		return cells;
	}

	public List<Path> getPaths() {
		setLasts();
		return paths;
	}

	private void setLasts() {
		for (Path p : paths) {
			p.setLast(p.getPath().get(p.getPath().size() - 1));
		}
	}

	private void adjustExplorationPaths() {
		List<Cell> exp1 = new ArrayList<Cell>();
		for (int i = 0; i < 2024; i++) {
			exp1.add(getZZPathWithID(0).getPath().remove(0));
		}

		List<Cell> exp0 = reverseIt(getZZPathWithID(0).getPath());

		List<Cell> exp3 = new ArrayList<Cell>();
		for (int i = 0; i < 2024; i++) {
			exp3.add(getZZPathWithID(1).getPath().remove(0));
		}

		List<Cell> exp2 = reverseIt(getZZPathWithID(1).getPath());

		List<Cell> exp5 = new ArrayList<Cell>();
		for (int i = 0; i < 2024; i++) {
			exp5.add(getZZPathWithID(2).getPath().remove(0));
		}

		List<Cell> exp4 = reverseIt(getZZPathWithID(2).getPath());

		List<Cell> exp7 = new ArrayList<Cell>();
		for (int i = 0; i < 2024; i++) {
			exp7.add(getZZPathWithID(3).getPath().remove(0));
		}

		List<Cell> exp6 = reverseIt(getZZPathWithID(3).getPath());
		getPathWithID(0).setPath(exp0);
		getPathWithID(1).setPath(exp1);
		getPathWithID(2).setPath(exp2);
		getPathWithID(3).setPath(exp3);
		getPathWithID(4).setPath(exp4);
		getPathWithID(5).setPath(exp5);
		getPathWithID(6).setPath(exp6);
		getPathWithID(7).setPath(exp7);

	}

	private void initializePaths() {
		for (int i = 0; i < numAgents; i++) {
			paths.add(new Path(i));
		}
	}

	private void adjustDeploymentPaths() {
		List<Cell> dep0 = PathOperations.getValidPath(new Cell(63, 63), new Cell(0, 63), gridSize);
		List<Cell> dep2 = PathOperations.getValidPath(new Cell(64, 63), new Cell(127, 63), gridSize);
		List<Cell> dep4 = PathOperations.getValidPath(new Cell(64, 64), new Cell(127, 64), gridSize);
		List<Cell> dep6 = PathOperations.getValidPath(new Cell(63, 64), new Cell(0, 64), gridSize);

		List<Cell> dep1 = new ArrayList<Cell>();
		dep1.add(new Cell(63, 63));
		dep1.addAll(dep0);
		dep1.add(new Cell(0, 63));
		dep1.addAll(reverseIt(dep0));

		List<Cell> dep3 = new ArrayList<Cell>();
		dep3.add(new Cell(64, 63));
		dep3.addAll(dep2);
		dep3.add(new Cell(127, 63));
		dep3.addAll(reverseIt(dep2));

		List<Cell> dep5 = new ArrayList<Cell>();
		dep5.add(new Cell(64, 64));
		dep5.addAll(dep4);
		dep5.add(new Cell(127, 64));
		dep5.addAll(reverseIt(dep4));

		List<Cell> dep7 = new ArrayList<Cell>();
		dep7.add(new Cell(63, 64));
		dep7.addAll(dep6);
		dep7.add(new Cell(0, 64));
		dep7.addAll(reverseIt(dep6));

		getPathWithID(0).setDepPath(dep0);
		getPathWithID(1).setDepPath(dep1);
		getPathWithID(2).setDepPath(dep2);
		getPathWithID(3).setDepPath(dep3);
		getPathWithID(4).setDepPath(dep4);
		getPathWithID(5).setDepPath(dep5);
		getPathWithID(6).setDepPath(dep6);
		getPathWithID(7).setDepPath(dep7);

	}

	private List<Cell> reverseIt(List<Cell> dep) {
		List<Cell> list = new ArrayList<Cell>(dep);
		Collections.reverse(list);
		return list;
	}

	private Path getPathWithID(int n) {
		for (Path p : paths) {
			if (p.getId() == n)
				return p;
		}
		return null;
	}

	private Path getZZPathWithID(int n) {
		for (Path p : zzpaths) {
			if (p.getId() == n)
				return p;
		}
		return null;
	}

	public static void main(String[] args) {
		VerifyPattern.drawIt(new AdvancedSectors().getPaths(), 1300);;
	}
}
