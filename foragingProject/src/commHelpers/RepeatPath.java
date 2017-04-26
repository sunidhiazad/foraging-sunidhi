package commHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import operations.PathOperations;
import entities.Cell;

public class RepeatPath {

	private List<Cell> path;
	private List<Cell> temp;

	private Cell start;
	private Cell end;

	public RepeatPath(List<Cell> path) {
		this.path = path;
		this.start = path.get(0);
		this.end = path.get(path.size() - 1);
		this.temp = new ArrayList<Cell>();
	}

	public Cell getNext(Cell current) {
		if (temp.size() == 0 || current.equals(start))
			this.temp = new ArrayList<Cell>(path.subList(1, path.size()));

		if (current.equals(end)) {
			this.temp = new ArrayList<Cell>(path.subList(0, path.size()-1));
			Collections.reverse(temp);
		}

		return temp.remove(0);
	}

	public static void main(String[] args) {
		List<Cell> path=PathOperations.getDeltaXPath(new Cell(50, 50), new Cell(60, 60));
		path.add(new Cell(60,60));
		RepeatPath obj = new RepeatPath(path);
		Cell cell=new Cell(50,50);
		for (int i = 0; i < 90; i++) {
			cell=obj.getNext(cell);
			System.out.println(cell);
		}


	}
}
