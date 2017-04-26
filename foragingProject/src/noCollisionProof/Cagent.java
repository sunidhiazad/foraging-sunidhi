package noCollisionProof;

import entities.AngularRange;
import entities.Cell;

public class Cagent {
	private int id;
	private Cell firstPoint;
	private AngularRange range;
	private int wait;
	private int distance;
	private int depTime;
	private int subQuad;

	public Cagent() {
		super();
		this.firstPoint = new Cell(-1, -1);
	}

	public Cagent(int id) {
		super();
		this.id = id;
		this.firstPoint = new Cell(-1, -1);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cell getFirstPoint() {
		return firstPoint;
	}

	public void setFirstPoint(Cell firstPoint) {
		this.firstPoint = firstPoint;
	}

	public AngularRange getRange() {
		return range;
	}

	public void setRange(AngularRange range) {
		this.range = range;
	}

	public int getWait() {
		return wait;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDepTime() {
		return depTime;
	}

	public void setDepTime(int depTime) {
		this.depTime = depTime;
	}

	public int getSubQuad() {
		return subQuad;
	}

	public void setSubQuad(int subQuad) {
		this.subQuad = subQuad;
	}

	@Override
	public String toString() {
		return "Cagent [id=" + id + ", firstPoint=" + firstPoint + ", range=" + range + ", wait=" + wait + ", distance=" + distance + ", depTime=" + depTime + ", subQuad=" + subQuad + "]";
	}
}
