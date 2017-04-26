package oneagent;

import java.util.List;

import entities.Cell;
import enums.Pattern;

public class OneAgentBean {

	private Cell food;
	private List<Cell> path;
	private int id;
	private int gridSize;
	private Pattern pattern;
	private int separation;
	private int expTime;

	public OneAgentBean() {}

	public OneAgentBean(int id, int gridSize, Pattern pattern, Cell food, int expTime, int separation) {
		super();
		this.id = id;
		this.gridSize = gridSize;
		this.pattern = pattern;
		this.food = food;
		this.expTime = expTime;
		this.separation = separation;
	}

	public OneAgentBean getResult(int id, int gridSize, Pattern pattern, Cell food, int separation) {
		this.food = food;
		this.path = new OneAgentOperations().getPath(gridSize, pattern);
		this.expTime = getExpTime();
		return new OneAgentBean(id, gridSize, pattern, food, expTime, separation);
	}

	private int getExpTime() {
		int i = 0;
		for (Cell c : path) {
			i++;
			if (c.equals(food))
				break;
		}
		return i;
	}

	public Cell getFood() {
		return food;
	}

	public List<Cell> getPath() {
		return path;
	}

	public int getId() {
		return id;
	}

	public int getGridSize() {
		return gridSize;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public int getSeparation() {
		return separation;
	}

	@Override
	public String toString() {
		return id + "  " + pattern + "  " + separation + "   (" + food.getX() + "," + food.getY() + ")" + "  " + expTime;
	}

}