package entities;

import java.util.ArrayList;
import java.util.List;

import operations.PathOperations;

public class SubConfiguration {

	private Cell frstContact;
	private Cell scndContact;
	private Cell neighFrstContact;
	private Cell neighScndContact;
	private int maxTS;
	private Cell locator;
	private int timeStep1;
	private int timeStep2;
	private int gridSize;

	private WaitingCell wcell1;
	private WaitingCell wcell2;

	private List<Cell> commPath;

	public SubConfiguration() {
		super();
	}

	public SubConfiguration(Cell frstContact, Cell scndContact, Cell neighFrstContact, Cell neighScndContact, Cell locator, int gridSize) {
		super();
		this.frstContact = frstContact;
		this.scndContact = scndContact;
		this.locator = locator;
		this.gridSize = gridSize;

		this.neighFrstContact = neighFrstContact;
		this.neighScndContact = neighScndContact;
		this.timeStep1 = frstContact.getTimeStep();
		this.timeStep2 = scndContact.getTimeStep();
		this.setMaxTS();
	}

	public Cell getFrstContact() {
		return frstContact;
	}

	public void setFrstContact(Cell frstContact) {
		this.frstContact = frstContact;
	}

	public Cell getScndContact() {
		return scndContact;
	}

	public void setScndContact(Cell scndContact) {
		this.scndContact = scndContact;
	}

	public Cell getNeighFrstContact() {
		return neighFrstContact;
	}

	public void setNeighFrstContact(Cell neighFrstContact) {
		this.neighFrstContact = neighFrstContact;
	}

	public Cell getNeighScndContact() {
		return neighScndContact;
	}

	public void setNeighScndContact(Cell neighScndContact) {
		this.neighScndContact = neighScndContact;
	}

	public int getMaxTS() {
		return maxTS;
	}

	public void setMaxTS() {
		if (timeStep1 >= timeStep2)
			this.maxTS = timeStep1;
		else
			this.maxTS = timeStep2;
	}

	public Cell getLocator() {
		return locator;
	}

	public void setLocator(Cell locator) {
		this.locator = locator;
	}

	public int getTimeStep1() {
		return timeStep1;
	}

	public void setTimeStep1(int timeStep1) {
		this.timeStep1 = timeStep1;
	}

	public int getTimeStep2() {
		return timeStep2;
	}

	public void setTimeStep2(int timeStep2) {
		this.timeStep2 = timeStep2;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public WaitingCell getWcell1() {
		return wcell1;
	}

	public void setWaitingCells(WaitingCell wcell1, WaitingCell wcell2) {
		this.wcell1 = wcell1;
		this.wcell2 = wcell2;
		
		List<Cell> cpath = new ArrayList<Cell>();
		cpath.addAll(PathOperations.getValidRandomPath(locator, neighFrstContact, gridSize));
		cpath=cpath.subList(1, cpath.size());
		cpath.add(neighFrstContact);
		if (wcell1.getWait() > 0) {
			//for (int i = 0; i < wcell1.getWait()-1; i++) {
			for (int i = 0; i < wcell1.getWait(); i++) {
				cpath.add(neighFrstContact);
			}
		}

		cpath.addAll(PathOperations.getValidRandomPath(cpath.remove(cpath.size() - 1), neighScndContact, gridSize));
		if (wcell2.getWait() > 0) {
			for (int i = 0; i < wcell2.getWait(); i++) {
				cpath.add(neighScndContact);
			}
		}
		cpath.add(neighScndContact);
		setCommPath(cpath);
	}

	public WaitingCell getWcell2() {
		return wcell2;
	}

	public List<Cell> getCommPath() {
		return commPath;
	}

	public void setCommPath(List<Cell> commPath) {
		this.commPath = commPath;
	}

	public void setMaxTS(int maxTS) {
		this.maxTS = maxTS;
	}

	@Override
	public String toString() {
		return "SubConfiguration [frstContact=" + frstContact + ", scndContact=" + scndContact + ", neighFrstContact=" + neighFrstContact + ", neighScndContact=" + neighScndContact + ", locator=" + locator + ", timeStep1=" + timeStep1 + ", timeStep2=" + timeStep2 + ", wcell1=" + wcell1 + ", wcell2=" + wcell2 + ", commPath=" + commPath + "]";
	}

}
