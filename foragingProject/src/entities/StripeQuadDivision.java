package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import operations.GeneralOperations;
import operations.Operations;
import operations.VerifyPattern;
import enums.HybStripeOrient;

public class StripeQuadDivision {

	private int gridSize;
	private int agentsInAQuad;
	private List<HybridStripe> lstQStrips;
	private Map<Integer, Cell> map = new HashMap<Integer, Cell>();

	public StripeQuadDivision(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.agentsInAQuad = numAgents / 4;
		this.lstQStrips = new ArrayList<HybridStripe>();
		for (int i = 1; i <= agentsInAQuad; i++) {
			lstQStrips.add(new HybridStripe(10 + i));
			lstQStrips.add(new HybridStripe(20 + i));
			lstQStrips.add(new HybridStripe(30 + i));
			lstQStrips.add(new HybridStripe(40 + i));
		}
		prepareMap();
		setStripes();
		GeneralOperations.displayCollection(lstQStrips);
	}

	private void prepareMap() {
		Cell temp;
		int total = 7 * gridSize / 8;
		int k = 1;
		// quadrant 1
		temp = new Cell(gridSize / 8, gridSize / 8 - 1);
		for (int i = total / agentsInAQuad - 1; i < total; i = i + 2 * total / agentsInAQuad) {
			map.put(10 + (k++), Operations.plusX(temp, i));
			map.put(10 + (k++), Operations.plusX(temp, i + 1));
		}
		// quadrant 2
		temp = new Cell(gridSize / 8 - 1, 7 * gridSize / 8 - 1);
		k = 1;
		for (int i = total / agentsInAQuad - 1; i < total; i = i + 2 * total / agentsInAQuad) {
			map.put(20 + (k++), Operations.minusY(temp, i));
			map.put(20 + (k++), Operations.minusY(temp, i + 1));
		}
		// quadrant 3
		temp = new Cell(7 * gridSize / 8 - 1, 7 * gridSize / 8);
		k = 1;
		for (int i = total / agentsInAQuad - 1; i < total; i = i + 2 * total / agentsInAQuad) {
			map.put(30 + (k++), Operations.minusX(temp, i));
			map.put(30 + (k++), Operations.minusX(temp, i + 1));
		}
		// quadrant 4
		temp = new Cell(7 * gridSize / 8, gridSize / 8);
		k = 1;
		for (int i = total / agentsInAQuad - 1; i < total; i = i + 2 * total / agentsInAQuad) {
			map.put(40 + (k++), Operations.plusY(temp, i));
			map.put(40 + (k++), Operations.plusY(temp, i + 1));
		}
	}

	private void setStripes() {
		if (agentsInAQuad == 1) {
			for (HybridStripe hs : lstQStrips) {
				switch (hs.getQuad()) {
				case 1:
					hs.setStart(new Cell(gridSize / 8, gridSize / 8 - 1));
					hs.setLength(gridSize / 8);
					hs.setBreadth(7 * gridSize / 8);
					hs.setOrient(HybStripeOrient.getRandomOrientByQuad(1));
					hs.setPath();
					break;
				case 2:
					hs.setStart(new Cell(gridSize / 8 - 1, 7 * gridSize / 8 - 1));
					hs.setLength(7 * gridSize / 8);
					hs.setBreadth(gridSize / 8);
					hs.setOrient(HybStripeOrient.getRandomOrientByQuad(2));
					hs.setPath();
					break;
				case 3:
					hs.setStart(new Cell(7 * gridSize / 8 - 1, 7 * gridSize / 8));
					hs.setLength(gridSize / 8);
					hs.setBreadth(7 * gridSize / 8);
					hs.setOrient(HybStripeOrient.getRandomOrientByQuad(3));
					hs.setPath();
					break;
				case 4:
					hs.setStart(new Cell(7 * gridSize / 8, gridSize / 8));
					hs.setLength(7 * gridSize / 8);
					hs.setBreadth(gridSize / 8);
					hs.setOrient(HybStripeOrient.getRandomOrientByQuad(4));
					hs.setPath();
					break;
				}
			}
		} else {
			HybridStripe hs;
			int id;
			for (int i = 0; i < lstQStrips.size(); i++) {
				hs = lstQStrips.get(i);
				id = hs.getId();
				hs.setStart(getStart(id));
				hs.setLength(getLength(id));
				hs.setBreadth(getBreadth(id));
				hs.setOrient(getOrientation(id));
				hs.setPath();
				lstQStrips.set(i, hs);
			}
		}
	}

	public HybStripeOrient getOrientation(int id) {

		switch (id / 10) {
		case 1:
			if (id % 2 == 1)
				return HybStripeOrient.HORIZONTAL_UPPER_LEFT;
			else
				return HybStripeOrient.HORIZONTAL_UPPER_RIGHT;
		case 2:
			if (id % 2 == 1)
				return HybStripeOrient.VERTICAL_LOWER_LEFT;
			else
				return HybStripeOrient.VERTICAL_UPPER_LEFT;
		case 3:
			if (id % 2 == 1)
				return HybStripeOrient.HORIZONTAL_LOWER_RIGHT;
			else
				return HybStripeOrient.HORIZONTAL_LOWER_LEFT;
		case 4:
			if (id % 2 == 1)
				return HybStripeOrient.VERTICAL_UPPER_RIGHT;
			else
				return HybStripeOrient.VERTICAL_LOWER_RIGHT;
		}

		return HybStripeOrient.NULL;
	}

	public int getLength(int id) {
		if (id / 10 == 1 || id / 10 == 3) {
			return gridSize / 8;
		} else {
			return (7 * gridSize) / (8 * agentsInAQuad);
		}
	}

	public int getBreadth(int id) {
		if (id / 10 == 1 || id / 10 == 3) {
			return (7 * gridSize) / (8 * agentsInAQuad);
		} else {
			return gridSize / 8;
		}
	}

	public Cell getStart(int id) {
		return map.get(id);
	}

	public List<HybridStripe> getLstQStrips() {
		return lstQStrips;
	}

	public List<Path> getPaths() {
		Path p;
		List<Path> lst = new ArrayList<Path>();
		for (HybridStripe hs : lstQStrips) {
			p = new Path(hs.getPath());
			lst.add(p);
		}
		return lst;
	}

	public static void main(String[] args) {
		VerifyPattern.drawIt(new StripeQuadDivision(64, 16).getPaths(), 1000);
	}
}
