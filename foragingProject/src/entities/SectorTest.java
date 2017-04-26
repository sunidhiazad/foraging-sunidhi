package entities;

import java.math.BigDecimal;
import java.util.List;

public class SectorTest {
	private int id;
	// Input Parameters
	private int gridSize = 0;
	private int numAgents = 0;
	private Boolean inPath = false;

	// Output Parameters
	private Boolean gridCovered = false;
	private int numCollisions = 0;
	private Boolean validPaths = false;
	private BigDecimal percentExtraCoverage;
	private BigDecimal stdDeviation;
	private BigDecimal average;
	private List<Collision> collisions;

	public SectorTest() {
		super();
	}

	public SectorTest(int id, int gridSize, int numAgents, Boolean inPath) {
		super();
		this.id = id;
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.inPath = inPath;
	}

	public SectorTest(int id, int gridSize, int numAgents, Boolean inPath, Boolean gridCovered, int numCollisions, Boolean validPaths, BigDecimal percentExtraCoverage, BigDecimal stdDeviation, BigDecimal average) {
		super();
		this.id = id;
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.inPath = inPath;
		this.gridCovered = gridCovered;
		this.numCollisions = numCollisions;
		this.validPaths = validPaths;
		this.percentExtraCoverage = percentExtraCoverage;
		this.stdDeviation = stdDeviation;
		this.average = average;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Boolean getInPath() {
		return inPath;
	}

	public void setInPath(Boolean inPath) {
		this.inPath = inPath;
	}

	public Boolean getGridCovered() {
		return gridCovered;
	}

	public void setGridCovered(Boolean gridCovered) {
		this.gridCovered = gridCovered;
	}

	public int getNumCollisions() {
		return numCollisions;
	}

	public void setNumCollisions(int numCollisions) {
		this.numCollisions = numCollisions;
	}

	public Boolean getValidPaths() {
		return validPaths;
	}

	public void setValidPaths(Boolean validPaths) {
		this.validPaths = validPaths;
	}

	public BigDecimal getPercentExtraCoverage() {
		return percentExtraCoverage;
	}

	public void setPercentExtraCoverage(BigDecimal percentExtraCoverage) {
		this.percentExtraCoverage = percentExtraCoverage;
	}

	public BigDecimal getStdDeviation() {
		return stdDeviation;
	}

	public void setStdDeviation(BigDecimal stdDeviation) {
		this.stdDeviation = stdDeviation;
	}

	public BigDecimal getAverage() {
		return average;
	}

	public void setAverage(BigDecimal average) {
		this.average = average;
	}

	@Override
	public String toString() {
		return "SectorTest [id=" + id + ", gridSize=" + gridSize + ", numAgents=" + numAgents + "]";
	}

	public List<Collision> getCollisions() {
		return collisions;
	}

	public void setCollisions(List<Collision> collisions) {
		this.collisions = collisions;
	}
}
