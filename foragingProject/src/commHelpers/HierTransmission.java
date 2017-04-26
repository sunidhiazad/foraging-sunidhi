package commHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import patterns.ZigZag;
import comparators.HierCellComparator;
import entities.Agent;
import entities.Cell;
import entities.Path;
import enums.Pattern;

public class HierTransmission {

	private Agent agent;

	public HierTransmission(Agent agent, List<Path> paths) {
		this.agent = agent;
		List<Cell> startcells = new ArrayList<Cell>();
		for (Path p : paths) {
			startcells.add(p.getStart());
		}
		Collections.sort(startcells, new HierCellComparator());
		int side = (int) Math.sqrt(paths.size());
		int[][] idarray = new int[side][side];
		int k = 0;
		for (int row = 0; row < idarray.length; row++) {
			for (int col = 0; col < idarray[row].length; col++) {
				idarray[row][col] = startcells.get(k++).getPathid();

			}
		}
		//printArray(idarray);
		int row = 0;
		int col = 0;
		loop: for (row = 0; row < idarray.length; row++) {
			for (col = 0; col < idarray[row].length; col++) {
				if (idarray[row][col] == agent.getId())
					break loop;
			}
		}
		setAgentsTransmitTo(idarray, row, col);
	}

	public void printArray(int[][] idarray) {
		for (int row = 0; row < idarray.length; row++) {
			for (int col = 0; col < idarray[row].length; col++) {
				System.out.print(idarray[row][col]+"\t");

			}
			System.out.println();
		}		
	}

	public Agent getAgentWithTransmissionSet() {
		return agent;
	}

	private void setAgentsTransmitTo(int[][] idarray, int row, int col) {
		int length = idarray.length;
		int nr1 = row - 1;
		int nc1 = col;
		int wr1 = row;
		int wc1 = col - 1;
		int er1 = row;
		int ec1 = col + 1;
		int sr1 = row + 1;
		int sc1 = col;
		List<Integer> neighs = new ArrayList<Integer>();
		if (isValid(nr1, length) && isValid(nc1, length))
			neighs.add(idarray[nr1][nc1]);
		if (isValid(wr1, length) && isValid(wc1, length))
			neighs.add(idarray[wr1][wc1]);
		if (isValid(er1, length) && isValid(ec1, length))
			neighs.add(idarray[er1][ec1]);
		if (isValid(sr1, length) && isValid(sc1, length))
			neighs.add(idarray[sr1][sc1]);
		if (!agent.getFoodLocator())
			neighs.remove(new Integer(agent.getReceivedFrom()));
		agent.setHierTransmitTo(neighs);
	}

	private boolean isValid(int x, int length) {
		if (x >= 0 && x < length)
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		Agent a = new Agent(56);
		a.setReceivedFrom(42, Pattern.ZIG_ZAG);
		a = new HierTransmission(a, new ZigZag(64, 64).getPaths()).getAgentWithTransmissionSet();

	}

}
