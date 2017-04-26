package entities;

import java.util.ArrayList;
import java.util.List;

import enums.MoveCode;

public class Sector {
	private int id;
	private Cell origin;
	private AngularRange range;
	private List<Cell> cells = new ArrayList<Cell>();
	private List<Cell> depPath = new ArrayList<Cell>();
	private List<Cell> finalPath = new ArrayList<Cell>();
	private List<Cell> explorationPath = new ArrayList<Cell>();
	private int quadCode;
	private MoveCode levelMCode;
	private int startTime;
	private Cell firstPoint;
	private int depDistance;
	private int depTimeStep;
	private int expTimeStep;

	public Sector() {
		super();
	}

	public Sector(int id, Cell origin, AngularRange range, List<Cell> cells) {
		super();
		this.id = id;
		this.origin = origin;
		this.range = range;
		this.cells = cells;
	}

	public Sector(int id, Cell origin, AngularRange range) {
		super();
		this.id = id;
		this.origin = origin;
		// cells.add(origin);
		this.range = range;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public Cell getOrigin() {
		return origin;
	}

	public void setOrigin(Cell origin) {
		this.origin = origin;
	}

	public AngularRange getRange() {
		return range;
	}

	public void setRange(AngularRange range) {
		this.range = range;
	}

	public int getQuadCode() {
		return quadCode;
	}

	public void setQuadCode(int quadCode) {
		this.quadCode = quadCode;
		if (quadCode == 42 || quadCode == 11) {
			setLevelMCode(MoveCode.PLUS_X);
		} else if (quadCode == 12 || quadCode == 21) {
			setLevelMCode(MoveCode.MINUS_Y);
		} else if (quadCode == 22 || quadCode == 31) {
			setLevelMCode(MoveCode.MINUS_X);
		} else if (quadCode == 32 || quadCode == 41) {
			setLevelMCode(MoveCode.PLUS_Y);
		}
	}

	public MoveCode getLevelMCode() {
		return levelMCode;
	}

	public void setLevelMCode(MoveCode levelMCode) {
		this.levelMCode = levelMCode;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public Cell getFirstPoint() {
		return firstPoint;
	}

	public void setFirstPoint(Cell firstPoint) {
		this.firstPoint = firstPoint;
	}

	public int getDepDistance() {
		return depDistance;
	}

	public void setDepDistance(int depDistance) {
		this.depDistance = depDistance;
	}

	public List<Cell> getFinalPath() {
		return finalPath;
	}

	public void setFinalPath(List<Cell> finalPath) {
		this.finalPath = finalPath;
	}

	public int getDepTimeStep() {
		return depTimeStep;
	}

	public void setDepTimeStep(int depTimeStep) {
		this.depTimeStep = depTimeStep;
	}

	public int getExpTimeStep() {
		return expTimeStep;
	}

	public void setExpTimeStep(int expTimeStep) {
		this.expTimeStep = expTimeStep;
	}

	public List<Cell> getExplorationPath() {
		return explorationPath;
	}

	public void setExplorationPath(List<Cell> explorationPath) {
		this.explorationPath = explorationPath;
	}

	public List<Cell> getDepPath() {
		return depPath;
	}

	public void setDepPath(List<Cell> depPath) {
		this.depPath = depPath;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", (without revisits)=" + cells.size() + ", (with revisits)=" + explorationPath.size() + ", Deployment Time=" + depTimeStep + "]";

		// return "Sector [id=" + id + ", range=" + range + ", startTime=" +
		// startTime + ", firstPoint=" + firstPoint + ", depDistance=" +
		// depDistance + ", depTimeStep=" + depTimeStep + ", expTimeStep=" +
		// expTimeStep + ", finalPath=" + finalPath + "]";

	}

}
