package entities;

import enums.HilOrient;
import enums.StartPos;
import enums.ZigZagOrient;

public class HierDividedArea {
	private Cell start;
	private StartPos startPos;
	private int size;
	private HilOrient hilOrientation=HilOrient.NULL;
	private ZigZagOrient zigOrientation=ZigZagOrient.NULL;

	public HierDividedArea() {
		super();
	}

	public HierDividedArea(Cell start, StartPos startPos, int size, HilOrient hilOrientation) {
		super();
		this.start = start;
		this.startPos = startPos;
		this.size = size;
		this.hilOrientation = hilOrientation;
	}

	public HierDividedArea(Cell start, StartPos startPos, int size, ZigZagOrient zigOrientation) {
		super();
		this.start = start;
		this.startPos = startPos;
		this.size = size;
		this.setZigOrientation(zigOrientation);
	}

	public Cell getStart() {
		return start;
	}

	public StartPos getStartPos() {
		return startPos;
	}

	public int getSize() {
		return size;
	}

	public void setStart(Cell start) {
		this.start = start;
	}

	public void setStartPos(StartPos startPos) {
		this.startPos = startPos;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public HilOrient getHilOrientation() {
		return hilOrientation;
	}

	public void setHilOrientation(HilOrient hilOrientation) {
		this.hilOrientation = hilOrientation;
	}

	public ZigZagOrient getZigOrientation() {
		return zigOrientation;
	}

	public void setZigOrientation(ZigZagOrient zigOrientation) {
		this.zigOrientation = zigOrientation;
	}

	@Override
	public String toString() {
		return "HierDividedArea [start=" + start + ", startPos=" + startPos + "]";
	}

}
