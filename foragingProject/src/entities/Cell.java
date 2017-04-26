package entities;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlTransient;

import enums.Phase;

public class Cell {

	private int id;
	private int x;
	private int y;
	// Used only for sector-wise division
	private BigDecimal angle;
	private int timeStep = 0;
	private int pathid = -1;

	private Phase phase = Phase.NULL;

	public static Cell NULL_CELL = new Cell(-1, -1);
	public static Cell TCELL = new Cell(-10, -10);
	public static Cell COCELL = new Cell(-100, -100);

	public Cell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Cell(int x, int y, BigDecimal angle) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public Cell() {
		super();
	}

	@XmlTransient
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@XmlTransient
	public BigDecimal getAngle() {
		return angle;
	}

	public void setAngle(BigDecimal angle) {
		this.angle = angle;
	}

	@XmlTransient
	public int getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(int timeStep) {
		this.timeStep = timeStep;
	}

	public int getPathid() {
		return pathid;
	}

	public void setPathid(int pathid) {
		this.pathid = pathid;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	@Override
	public String toString() {
		if (x == -10 && y == -10)
			return "TCELL";
		if (x == -1 && y == -1)
			return "NULL_CELL";
		if (x == -100 && y == -100)
			return "COCELL";
		return "(" + x + "," + y + ")-" + timeStep;
		// StringBuffer sb = new StringBuffer("(" + x + "," + y + ")");
		// sb.append((timeStep != 0) ? "|" + "T-" + timeStep : "");
		// sb.append((sector != -1) ? "|" + "S-" + sector : "");
		// sb.append((angle != null) ? "|" + "A-" + angle : "");
		// sb.append("|" + phase.getAlias());
		// return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		Cell c = (Cell) obj;
		return this.getX() == c.getX() && this.getY() == c.getY();
	}

}
