package entities;

import java.util.ArrayList;
import java.util.List;

public class SubOrients {
	private List<Integer> subOrientList;

	public SubOrients() {}

	public SubOrients(int i1, int i2, int i3, int i4) {
		subOrientList=new ArrayList<Integer>();
		subOrientList.add(i1);
		subOrientList.add(i2);
		subOrientList.add(i3);
		subOrientList.add(i4);
	}

	public SubOrients(List<Integer> subOrientList) {
		super();
		this.subOrientList = subOrientList;
	}

	public List<Integer> getSubOrientList() {
		return subOrientList;
	}

	public void setSubOrientList(List<Integer> subOrientList) {
		this.subOrientList = subOrientList;
	}

}
