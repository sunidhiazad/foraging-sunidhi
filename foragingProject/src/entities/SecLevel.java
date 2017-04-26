package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.SectorOperations;
import enums.MoveCode;

public class SecLevel {

	private MoveCode mCode;
	private List<Cell> cells;
	private Map<Integer, List<Cell>> secMap = new HashMap<Integer, List<Cell>>();

	public SecLevel() {
		super();
	}

	public SecLevel(MoveCode mCode, List<Cell> cells) {
		super();
		this.mCode = mCode;
		this.cells = cells;
		setSecMap(SectorOperations.getSecMapByCodeAndCells(mCode, cells));
	}

	public MoveCode getmCode() {
		return mCode;
	}

	public void setmCode(MoveCode mCode) {
		this.mCode = mCode;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public Map<Integer, List<Cell>> getSecMap() {
		return secMap;
	}

	public void setSecMap(Map<Integer, List<Cell>> secMap) {
		this.secMap = secMap;
	}

}
