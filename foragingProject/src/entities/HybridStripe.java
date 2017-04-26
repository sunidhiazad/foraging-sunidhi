package entities;

import java.util.ArrayList;
import java.util.List;

import operations.Operations;
import enums.HybStripeOrient;

public class HybridStripe {

	private int id;
	private Cell start;
	private int length;
	private int breadth;
	private HybStripeOrient orient;
	private List<Cell> path;

	public HybridStripe(int id) {
		super();
		this.id = id;
	}

	public HybridStripe(int id, Cell start, int length, int breadth, HybStripeOrient orient) {
		super();
		this.setId(id);
		this.start = start;
		this.length = length;
		this.breadth = breadth;
		this.orient = orient;
		setPath();
	}

	public int getQuad() {
		return id / 10;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cell getStart() {
		return start;
	}

	public void setStart(Cell start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getBreadth() {
		return breadth;
	}

	public void setBreadth(int breadth) {
		this.breadth = breadth;
	}

	public HybStripeOrient getOrient() {
		return orient;
	}

	public void setOrient(HybStripeOrient orient) {
		this.orient = orient;
	}

	public List<Cell> getPath() {
		return path;
	}

	public void setPath() {
		path = new ArrayList<Cell>();
		Cell temp = start;
		switch (orient) {
		case HORIZONTAL_UPPER_LEFT:
			for (int i = 0; i < length; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.minusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusY(temp, 1);
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.plusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusY(temp, 1);
			}
			break;
		case HORIZONTAL_LOWER_LEFT:
			for (int i = 0; i < length; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.minusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusY(temp, 1);
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.plusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusY(temp, 1);
			}
			break;
		case VERTICAL_UPPER_LEFT:
			for (int i = 0; i < breadth; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.minusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusX(temp, 1);
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.plusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusX(temp, 1);
			}
			break;
		case VERTICAL_LOWER_LEFT:
			for (int i = 0; i < breadth; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.plusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusX(temp, 1);
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.minusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusX(temp, 1);
			}
			break;
		case HORIZONTAL_UPPER_RIGHT:
			for (int i = 0; i < length; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.plusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusY(temp, 1);
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.minusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.minusY(temp, 1);
			}
			break;
		case HORIZONTAL_LOWER_RIGHT:
			for (int i = 0; i < length; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.plusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusY(temp, 1);
				path.add(temp);
				for (int j = 1; j < breadth; j++) {
					temp = Operations.minusX(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusY(temp, 1);
			}
			break;
		case VERTICAL_UPPER_RIGHT:
			for (int i = 0; i < breadth; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.minusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusX(temp, 1);
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.plusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusX(temp, 1);
			}
			break;
		case VERTICAL_LOWER_RIGHT:
			for (int i = 0; i < breadth; i = i + 2) {
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.plusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusX(temp, 1);
				path.add(temp);
				for (int j = 1; j < length; j++) {
					temp = Operations.minusY(temp, 1);
					path.add(temp);
				}
				temp = Operations.plusX(temp, 1);
			}
			break;
		case NULL:
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		return "HybridStripe [id=" + id + ", start=" + start + ", length=" + length + ", breadth=" + breadth + ", orient=" + orient + "]";
	}

}
