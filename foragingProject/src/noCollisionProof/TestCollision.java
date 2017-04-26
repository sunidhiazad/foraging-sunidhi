package noCollisionProof;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comparators.CagentComparator;
import operations.GeneralOperations;
import operations.Operations;
import operations.SectorOperations;
import entities.AngularRange;
import entities.Cell;

public class TestCollision {
	private int numAgents;
	private List<Cagent> agents;
	private List<AngularRange> ranges;
	private List<Cell> quad1;
	private Cell origin = new Cell(0, 0);

	public TestCollision(int numAgents) {
		this.numAgents = numAgents;
		ranges = SectorOperations.getRangesForQuad1(numAgents);
		agents = new ArrayList<Cagent>();
		for (int i = 0; i < numAgents / 4; i++) {
			Cagent ca = new Cagent(i);
			ca.setRange(ranges.get(i));
			if (i < numAgents / 8)
				ca.setSubQuad(1);
			else
				ca.setSubQuad(2);
			agents.add(ca);
		}
		quad1 = setQuadCells();
		assignFirstPoints();
		assignDistances();
		//GeneralOperations.displayCollection(agents);
		System.out.println("#of agents in a quadrant: " + numAgents / 4+" Difference: "+(this.getMaxDepTime()-this.getMinDepTime()));
	}

	private Integer getMinDepTime() {
		List<Integer> deptimes = new ArrayList<Integer>();
		for (Cagent ca : agents) {
			deptimes.add(ca.getDepTime());
		}
		return Collections.min(deptimes);
	}

	private Integer getMaxDepTime() {
		List<Integer> deptimes = new ArrayList<Integer>();
		for (Cagent ca : agents) {
			deptimes.add(ca.getDepTime());
		}
		return Collections.max(deptimes);
	}

	private void assignDistances() {
		for (Cagent ca : agents) {
			ca.setDistance(Operations.getDistance(ca.getFirstPoint(), origin));
		}
		Collections.sort(agents, new CagentComparator());
		int wait = numAgents / 4 - 1;
		for (Cagent ca : agents) {
			ca.setWait(wait--);
			ca.setDepTime(ca.getDistance() + ca.getWait());
		}
	}

	private void assignFirstPoints() {
		int level = 1;
		int frstPointAssigned = 0;
		List<Cell> diagonal;
		BigDecimal angle;
		int sectorId;
		while (frstPointAssigned != numAgents / 4) {
			diagonal = getDiagonal(level);
			for (Cell c : diagonal) {
				angle = findAngle(c);
				sectorId = getSectorId(angle);
				if (agents.get(sectorId).getFirstPoint().equals(new Cell(-1, -1))) {
					agents.get(sectorId).setFirstPoint(c);
					frstPointAssigned++;
				}
			}
			level++;
		}
	}

	private BigDecimal findAngle(Cell c) {
		return GeneralOperations.tanInverse(GeneralOperations.divideBD(c.getY(), c.getX()));
	}

	private List<Cell> setQuadCells() {
		List<Cell> cells = new ArrayList<Cell>();
		for (int x = 1; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				cells.add(new Cell(x, y));
			}
		}
		return cells;
	}

	private int getSectorId(BigDecimal angle) {
		AngularRange ar;
		for (int i = 0; i < ranges.size(); i++) {
			ar = ranges.get(i);
			if (angle.doubleValue() >= ar.getFrom().doubleValue() && angle.doubleValue() < ar.getTo().doubleValue())
				return i;
		}
		return 0;
	}

	private List<Cell> getDiagonal(int level) {
		List<Cell> diagonal = new ArrayList<Cell>();
		for (int i = 0; i < quad1.size(); i++) {
			if (Operations.getDistance(quad1.get(i), origin) == level) {
				diagonal.add(quad1.get(i));
			}
		}
		return diagonal;
	}

	public static void main(String[] args) {
		
		new TestCollision(32);
		new TestCollision(40);
		new TestCollision(48);
		new TestCollision(56);
		new TestCollision(64);
		new TestCollision(72);
		new TestCollision(80);
		new TestCollision(88);
		new TestCollision(96);
		new TestCollision(104);
		new TestCollision(112);
		new TestCollision(120);
		new TestCollision(128);
		new TestCollision(136);
		
		// for (int j = 1; j < 5; j++) {
		// System.out.println("Level: " + j);
		// GeneralOperations.displayCollection(obj.getDiagonal(j));
		// }

	}
}
