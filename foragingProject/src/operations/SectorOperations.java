package operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entities.AngularRange;
import entities.Cell;
import entities.SecLevel;
import enums.MoveCode;

public class SectorOperations {

	public static List<AngularRange> getRanges(int numAgents) {

		List<AngularRange> angRanges = new ArrayList<AngularRange>();
		List<BigDecimal> slopes = getSlopes(numAgents);
		List<AngularRange> ranges = new ArrayList<AngularRange>();
		BigDecimal from = GeneralOperations.tanInverse(new BigDecimal(0));
		BigDecimal to;
		for (int i = 0; i < slopes.size(); i++) {
			to = GeneralOperations.tanInverse(slopes.get(i));
			ranges.add(new AngularRange(from, to));
			from = to;
		}
		ranges.add(new AngularRange(ranges.get(ranges.size() - 1).getTo(), new BigDecimal(90)));
		angRanges.addAll(ranges);
		angRanges.addAll(addDegrees(ranges, new BigDecimal(90)));
		angRanges.addAll(addDegrees(ranges, new BigDecimal(180)));
		angRanges.addAll(addDegrees(ranges, new BigDecimal(270)));
		return angRanges;

	}

	public static List<AngularRange> getRangesForQuad1(int numAgents) {
		List<AngularRange> angRanges = new ArrayList<AngularRange>();
		List<BigDecimal> slopes = getSlopes(numAgents);
		List<AngularRange> ranges = new ArrayList<AngularRange>();
		BigDecimal from = GeneralOperations.tanInverse(new BigDecimal(0));
		BigDecimal to;
		for (int i = 0; i < slopes.size(); i++) {
			to = GeneralOperations.tanInverse(slopes.get(i));
			ranges.add(new AngularRange(from, to));
			from = to;
		}
		ranges.add(new AngularRange(ranges.get(ranges.size() - 1).getTo(), new BigDecimal(90)));
		angRanges.addAll(ranges);
		return angRanges;
	}

	private static List<AngularRange> addDegrees(List<AngularRange> ranges, BigDecimal angle) {
		List<AngularRange> lstRanges = new ArrayList<AngularRange>();
		for (AngularRange ar : ranges) {
			lstRanges.add(new AngularRange(ar.getFrom().add(angle), ar.getTo().add(angle)));
		}
		return lstRanges;
	}

	private static List<BigDecimal> getSlopes(int numAgents) {
		List<BigDecimal> slopes = new ArrayList<BigDecimal>();
		int part = numAgents / 8;
		List<BigDecimal> lst = new ArrayList<BigDecimal>();
		BigDecimal fraction = GeneralOperations.divideBD(1, part);
		BigDecimal next = new BigDecimal(0);
		for (int i = 0; i < part - 1; i++) {
			next = next.add(fraction);
			lst.add(next);
		}
		slopes.addAll(lst);
		slopes.add(new BigDecimal(1));
		for (int i = lst.size() - 1; i >= 0; i--) {
			slopes.add(GeneralOperations.divideBD(new BigDecimal(1), lst.get(i)));
		}
		return slopes;
	}

	@SuppressWarnings("unchecked")
	public static List<Cell> sortCells(List<Cell> cells, MoveCode levelMCode) {
		Map<Cell, Integer> map = new HashMap<Cell, Integer>();
		for (Cell c : cells) {
			if (levelMCode.equals(MoveCode.PLUS_X) || levelMCode.equals(MoveCode.MINUS_X)) {
				map.put(c, c.getX());
			} else if (levelMCode.equals(MoveCode.PLUS_Y) || levelMCode.equals(MoveCode.MINUS_Y)) {
				map.put(c, c.getY());
			}
		}
		map = GeneralOperations.sortMapByValue(map);
		List<Cell> lst = new ArrayList<Cell>(map.keySet());
		if (levelMCode.equals(MoveCode.MINUS_X) || levelMCode.equals(MoveCode.MINUS_Y)) {
			Collections.reverse(lst);
		}
		return lst;
	}

	public static Map<Integer, List<Cell>> getSecMapByCodeAndCells(MoveCode mCode, List<Cell> cells) {
		List<Integer> values = new ArrayList<Integer>();
		Boolean isX = (mCode.equals(MoveCode.MINUS_X) || mCode.equals(MoveCode.PLUS_X)) ? true : false;
		int value = 0;
		if (isX) {
			for (Cell c : cells) {
				value = c.getX();
				if (!values.contains(values))
					values.add(value);
			}
		} else {
			for (Cell c : cells) {
				value = c.getY();
				if (!values.contains(values))
					values.add(value);
			}
		}
		Map<Integer, List<Cell>> map = new TreeMap<Integer, List<Cell>>();
		if (mCode.equals(MoveCode.MINUS_X) || mCode.equals(MoveCode.MINUS_Y)) {
			map = new TreeMap<Integer, List<Cell>>(Collections.reverseOrder());
		}
		for (Integer val : values) {
			map.put(val, getCellList(val, cells, isX));
		}
		return map;
	}

	private static List<Cell> getCellList(Integer val, List<Cell> cells, Boolean isX) {
		List<Cell> lst = new ArrayList<Cell>();
		if (isX) {
			for (Cell c : cells) {
				if (c.getX() == val)
					lst.add(c);
			}
		} else {
			for (Cell c : cells) {
				if (c.getY() == val)
					lst.add(c);
			}
		}
		return lst;
	}

	public static List<Cell> getSortedCells(SecLevel slevel) {
		Map<Integer, List<Cell>> secMap = slevel.getSecMap();
		List<Cell> lstCells = new ArrayList<Cell>();
		Boolean isX = (slevel.getmCode().equals(MoveCode.MINUS_X) || slevel.getmCode().equals(MoveCode.PLUS_X)) ? true : false;
		Boolean asc = true;

		for (Map.Entry<Integer, List<Cell>> entry : secMap.entrySet()) {
			if (isX) {
				lstCells.addAll(Operations.sortCellsOnY(entry.getValue(), asc));
			} else {
				lstCells.addAll(Operations.sortCellsOnX(entry.getValue(), asc));
			}
			asc = (asc == true) ? false : true;
		}
		return lstCells;
	}
	
	public static void main(String[] args) {
		GeneralOperations.displayCollection(getSlopes(32));
	}
}
