package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import patterns.Stripes;

public class StripePattern {

	private int gridSize;
	private int numAgents;
	private List<Path> stripes;
	private int maxDepTime;
	private int minDepTime;

	public StripePattern(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		stripes = new Stripes(gridSize, numAgents).getPaths();
		List<Integer> deps=new ArrayList<Integer>();
		for(Path p:stripes){
			deps.add(p.getDepTime());
		}
		this.maxDepTime=Collections.max(deps);
		this.minDepTime=Collections.min(deps);
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getNumAgents() {
		return numAgents;
	}

	public void setNumAgents(int numAgents) {
		this.numAgents = numAgents;
	}

	public List<Path> getStripes() {
		return stripes;
	}

	public void setStripes(List<Path> stripes) {
		this.stripes = stripes;
	}

	public int getMaxDepTime() {
		return maxDepTime;
	}

	public void setMaxDepTime(int maxDepTime) {
		this.maxDepTime = maxDepTime;
	}

	public int getMinDepTime() {
		return minDepTime;
	}

	public void setMinDepTime(int minDepTime) {
		this.minDepTime = minDepTime;
	}
	
	@Override
	public String toString() {
		return "StripePattern [gridSize=" + gridSize + ", numAgents=" + numAgents + ", maxDepTime=" + maxDepTime + ", minDepTime=" + minDepTime + "]";
	}

}
