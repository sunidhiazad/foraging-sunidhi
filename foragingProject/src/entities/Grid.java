package entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Grid {
	private List<Cell> cells;

	public Grid() {
		super();
	}

	public Grid(List<Cell> cells) {
		super();
		this.cells = cells;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
}
