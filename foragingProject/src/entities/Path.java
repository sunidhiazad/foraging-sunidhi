package entities;

import java.util.List;

import commHelpers.RepeatPath;

import enums.Phase;

public class Path {

	private int id;
	private Cell start;
	private List<Cell> path;
	private List<Cell> rptPath;
	private int startTime;
	private List<Cell> depPath;
	private int depTime;
	private int expTime;
	private List<Cell> completePath;
	private Cell last;
	private int quad;
	private RepeatPath repeat;

	public Path() {
		super();
	}

	public Path(int id) {
		super();
		this.id = id;
	}

	public Path(int id, Cell start, List<Cell> path) {
		super();
		this.id = id;
		this.start = start;
		this.path = path;
	}

	public Path(List<Cell> path) {
		super();
		this.path = path;
	}

	public Path(int id, List<Cell> path) {
		super();
		this.id = id;
		this.setPath(path);
	}

	public Path(int id, List<Cell> path, int startTime) {
		super();
		this.id = id;
		this.setPath(path);
		this.startTime = startTime;
	}

	public Path(Cell start, List<Cell> path, List<Cell> rptPath) {
		super();
		this.start = start;
		this.path = path;
		this.rptPath = rptPath;
	}

	public Cell getStart() {
		return start;
	}

	public List<Cell> getPath() {
		return path;
	}

	public List<Cell> getRptPath() {
		return rptPath;
	}

	public void setStart(Cell start) {
		this.start = start;
	}

	public void setPath(List<Cell> path) {
		this.path = setPhaseForPath(path, Phase.EXPLORATION);
	}

	public void setRptPath(List<Cell> rptPath) {
		this.rptPath = rptPath;
	}

	public List<Cell> getDepPath() {
		return depPath;
	}

	public void setDepPath(List<Cell> depPath) {
		this.depPath = setPhaseForPath(depPath, Phase.INITIAL_DEPLOYMENT);
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getDepTime() {
		return depTime;
	}

	public void setDepTime(int depTime) {
		this.depTime = depTime;
	}

	public int getExpTime() {
		return expTime;
	}

	public void setExpTime(int expTime) {
		this.expTime = expTime;
	}

	// @Override
	// public String toString() {
	// // return id + "\t" + start + "\t" + quad;
	// StringBuilder sb = new StringBuilder("Path ID=" + id + "\n");
	// sb.append("StartCell=" + start + ", StartTime=" + startTime +
	// ", DeploymentTime=" + depTime + ", ExplorationTime=" + expTime +
	// ", PathSize=" + ((id == 0) ? 0 : depPath.size()) + "+" + path.size() +
	// "\n");
	// sb.append("DeploymentPath=" + depPath + "\n");
	// sb.append("ExplorationPath=" + path + "\n");
	// sb.append("CompletePath=" + completePath + "\n");
	// return sb.toString();
	// }

	@Override
	public String toString() {
		return id+" "+start;
//		StringBuilder sb = new StringBuilder("Path ID=" + id + ", StartTime=" + startTime + "\n");
//		sb.append("DepPath=" + depPath + "\n");
//		sb.append("ExpPath=" + path + "\n");
//		sb.append("CompPath=" + completePath + "\n");
//		return sb.toString();
	}

	public List<Cell> setPhaseForPath(List<Cell> cells, Phase ph) {
		for (Cell c : cells) {
			c.setPhase(ph);
		}
		return cells;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Cell> getCompletePath() {
		return completePath;
	}

	public void setCompletePath(List<Cell> completePath) {
		this.completePath = completePath;
		if (completePath.size() == 0) {
			if (path.size() == 0)
				setLast(Cell.NULL_CELL);
			else
				setLast(path.get(path.size() - 1));
		} else
			setLast(completePath.get(completePath.size() - 1));
	}

	public Cell getLast() {
		return last;
	}

	public void setLast(Cell last) {
		this.last = last;
	}

	public int getQuad() {
		return quad;
	}

	public void setQuad(int quad) {
		this.quad = quad;
	}

	public RepeatPath getRepeat() {
		return repeat;
	}

	public void setRepeat(RepeatPath repeat) {
		this.repeat = repeat;
	}

}
