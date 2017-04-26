package entities;

public class WaitingCell {

	private int wait;
	private Cell cell;

	public WaitingCell() {
		super();
	}

	public WaitingCell(int wait, Cell cell) {
		super();
		this.wait = wait;
		this.cell = cell;
	}

	public int getWait() {
		return wait;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	@Override
	public String toString() {
		return "WaitingCell [wait=" + wait + ", cell=" + cell + "]";
	}

}
