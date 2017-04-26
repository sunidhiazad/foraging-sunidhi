package operations;

import java.util.ArrayList;
import java.util.List;

import entities.Cell;
import enums.MovementType;

public class PathOperations {

	public static List<Cell> getPath(MovementType mtype, Cell src, Cell dest) {
		if (mtype.equals(MovementType.DELTA_X))
			return getDeltaXPath(src, dest);
		if (mtype.equals(MovementType.DELTA_Y))
			return getDeltaYPath(src, dest);
		if (mtype.equals(MovementType.MAX_DELTA))
			return getMaxDeltaPath(src, dest);
		return null;
	}

	public static List<Cell> getMaxDeltaPath(Cell src, Cell dest) {
		List<Cell> path = new ArrayList<Cell>();
		Cell s = src;
		path.add(s);
		for (int i = 0; i < Operations.getDistance(src, dest); i++) {
			s = getNextCell(s, dest);
			path.add(s);
			if (s.equals(dest))
				break;
		}
		return path;
	}

	public static List<Cell> getValidRandomPath(Cell src,Cell dest,int gridSize){
		List<Cell> path=new ArrayList<Cell>();
		Cell curr=src;
		List<Cell> options=new ArrayList<Cell>();
		while(Operations.getDistance(curr, dest)!=1){
			path.add(curr);
			options=Operations.getNeighbours(curr, 1, gridSize);
			curr=selectBestOption(options,dest);
		}
		path.add(curr);
		return path;
	}
	
	private static Cell selectBestOption(List<Cell> options, Cell dest) {
		Cell best=options.get(0);
		if(options.contains(dest))
			return dest;
		for(Cell c:options){
			if(Operations.getDistance(c, dest)<Operations.getDistance(best, dest))
				best=c;
		}
		return best;
	}

	private static Cell getNextCell(Cell s, Cell dest) {
		int deltaX = getDeltaX(s, dest);
		int deltaY = getDeltaY(s, dest);
		if (deltaX == 0 && deltaY == 0)
			return s;
		if (deltaX == 0 && deltaY != 0)
			s = moveY(s, deltaY);
		else if (deltaY == 0 && deltaX != 0)
			s = moveX(s, deltaX);
		else if (Math.abs(deltaX) >= Math.abs(deltaY))
			s = moveX(s, deltaX);
		else if (Math.abs(deltaX) < Math.abs(deltaY))
			s = moveY(s, deltaY);
		return s;
	}

	public static List<Cell> getDeltaYPath(Cell src, Cell dest) {
		List<Cell> path = new ArrayList<Cell>();
		int deltaX = getDeltaX(src, dest);
		int deltaY = getDeltaY(src, dest);
		Cell s = src;
		while (deltaY != 0) {
			path.add(s);
			s = moveY(s, deltaY);
			deltaY = getDeltaY(s, dest);
		}
		while (deltaX != 0) {
			path.add(s);
			s = moveX(s, deltaX);
			deltaX = getDeltaX(s, dest);
		}
		return path;
	}

	public static List<Cell> getValidPath(Cell src, Cell dest, int gridSize) {
		List<Cell> validPath = new ArrayList<Cell>();
		validPath = getDeltaXPath(src, dest);
		if (!checkValidity(validPath, gridSize))
			validPath = getDeltaYPath(src, dest);
		if (!checkValidity(validPath, gridSize))
			validPath = getMaxDeltaPath(src, dest);
		return validPath;
	}

	public static boolean checkValidity(List<Cell> path, int gridSize) {
		for (Cell c : path) {
			if (c.getX() < 0 || c.getY() < 0)
				return false;
			if (c.getX() >= gridSize || c.getY() >= gridSize)
				return false;
		}
		return true;
	}

	public static List<Cell> getDeltaXPath(Cell src, Cell dest) {
		List<Cell> path = new ArrayList<Cell>();
		int deltaX = getDeltaX(src, dest);
		int deltaY = getDeltaY(src, dest);
		Cell s = src;
		while (deltaX != 0) {
			path.add(s);
			s = moveX(s, deltaX);
			deltaX = getDeltaX(s, dest);
		}
		while (deltaY != 0) {
			path.add(s);
			s = moveY(s, deltaY);
			deltaY = getDeltaY(s, dest);
		}
		return path;
	}

	private static int getDeltaX(Cell s, Cell d) {
		return d.getX() - s.getX();
	}

	private static int getDeltaY(Cell s, Cell d) {
		return d.getY() - s.getY();
	}

	private static Cell moveX(Cell cell, int deltaX) {
		if (deltaX > 0)
			return Operations.plusX(cell, 1);
		else
			return Operations.minusX(cell, 1);
	}

	private static Cell moveY(Cell cell, int deltaY) {
		if (deltaY > 0)
			return Operations.plusY(cell, 1);
		else
			return Operations.minusY(cell, 1);
	}

	public static void main(String[] args) {
		GeneralOperations.displayCollection(getValidRandomPath(new Cell(0, 0), new Cell(3, 5),21));
	}

}
