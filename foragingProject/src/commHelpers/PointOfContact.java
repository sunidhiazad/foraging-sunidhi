package commHelpers;

import java.util.ArrayList;
import java.util.List;

import operations.Operations;
import entities.Cell;
import entities.Path;
import entities.WaitingCell;

public class PointOfContact {

	private Cell src;
	private Path path;
	private List<ContactDetails> lstTotContacts = new ArrayList<PointOfContact.ContactDetails>();;
	private List<ContactDetails> lstPosContacts = new ArrayList<PointOfContact.ContactDetails>();;
	private List<Cell> posCells = new ArrayList<Cell>();
	private int clock;

	public PointOfContact() {}

	public PointOfContact(Cell src, Path path, int clock) {
		this.src = src;
		this.path = path;
		this.clock = clock;
		if (getTruncatedPath(path.getPath()).size() != 0) {
			// Add all the contact details
			for (Cell c : path.getPath()) {
				lstTotContacts.add(new ContactDetails(c, src));
			}

			// Filter out the possible details
			for (ContactDetails cd : lstTotContacts) {
				if (cd.getPossFlag()) {
					lstPosContacts.add(cd);
					posCells.add(cd.getCell());
				}
			}
		}
	}

	private ContactDetails getLastCellContactInfo() {
		Cell last = path.getLast();
		ContactDetails cdlast = new ContactDetails(last, src);
		last.setTimeStep(cdlast.getDistance() + src.getTimeStep());
		cdlast.setWait(0);
		cdlast.setPossFlag(true);
		return cdlast;
	}

	public WaitingCell get() {
		if (getTruncatedPath(path.getPath()).size() == 0) {
			ContactDetails cd = getLastCellContactInfo();
			return new WaitingCell(cd.getWait(), cd.getCell());
		}
		if (posCells.size() == 0) {
			ContactDetails cd = getLastCellContactInfo();
			return new WaitingCell(cd.getWait(), cd.getCell());
		}
		ContactDetails mincntct = lstPosContacts.get(0);
		for (ContactDetails cd : lstPosContacts) {
			if (cd.getCell().getTimeStep() < mincntct.getCell().getTimeStep()) {
				mincntct = cd;
			}
		}
		return new WaitingCell(mincntct.getWait(), mincntct.getCell());
	}

	private List<Cell> getTruncatedPath(List<Cell> path) {
		List<Cell> cells = new ArrayList<Cell>();
		for (Cell c : path) {
			if (c.getTimeStep() > clock)
				cells.add(c);
		}
		return cells;
	}

	public Cell getSrc() {
		return src;
	}

	public void setSrc(Cell src) {
		this.src = src;
	}

	public List<ContactDetails> getLstTotContacts() {
		return lstTotContacts;
	}

	public void setLstTotContacts(List<ContactDetails> lstTotContacts) {
		this.lstTotContacts = lstTotContacts;
	}

	public List<ContactDetails> getLstPosContacts() {
		return lstPosContacts;
	}

	public void setLstPosContacts(List<ContactDetails> lstPosContacts) {
		this.lstPosContacts = lstPosContacts;
	}

	public List<Cell> getPosCells() {
		return posCells;
	}

	public void setPosCells(List<Cell> posCells) {
		this.posCells = posCells;
	}

	// Inner class
	class ContactDetails {

		private Cell cell;
		private Cell src;
		private int distance;
		private int wait;
		private Boolean possFlag;

		public ContactDetails() {
			this.cell = Cell.NULL_CELL;
			this.distance = -1;
			this.wait = -1;
			this.possFlag = false;
		}

		public ContactDetails(Cell cell, int distance, int wait) {
			super();
			this.cell = cell;
			this.distance = distance;
			this.wait = wait;
		}

		public ContactDetails(Cell cell, Cell src) {
			super();
			this.cell = cell;
			this.src = src;
			this.distance = Operations.getDistance(cell, src) - 1;
			this.wait = cell.getTimeStep() - (src.getTimeStep() + distance);
			possFlag = (wait < 0) ? false : true;
		}

		public Cell getCell() {
			return cell;
		}

		public void setCell(Cell cell) {
			this.cell = cell;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public int getWait() {
			return wait;
		}

		public void setWait(int wait) {
			this.wait = wait;
		}

		public Boolean getPossFlag() {
			return possFlag;
		}

		public void setPossFlag(Boolean possFlag) {
			this.possFlag = possFlag;
		}

		public Cell getSrc() {
			return src;
		}

		public void setSrc(Cell src) {
			this.src = src;
		}

		@Override
		public String toString() {
			return "ContactDetails [cell=" + cell + ", src=" + src + ", distance=" + distance + ", wait=" + wait + ", possFlag=" + possFlag + "]";
		}

	}

}
