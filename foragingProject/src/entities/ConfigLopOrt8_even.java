package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enums.LoOrient;
import enums.MoveCode;

public class ConfigLopOrt8_even {
	private int agent;
	private MoveCode start;
	private LoOrient startOrient;
	private List<LoOrient> lstOrients;
	private List<Integer> numList;

	public ConfigLopOrt8_even(int agent) {
		this.agent = agent;
		this.numList = getNumberList(75);
		initialize();
	}

	private void initialize() {
		switch (agent) {
		case 2:
			this.start = MoveCode.PLUS_X;
			this.startOrient = LoOrient.get(1);
			this.lstOrients = getOrientListBy(Arrays.asList(2, 6, 7, 3, 4, 8, 5, 1));
			break;
		case 4:
			this.start = MoveCode.MINUS_Y;
			this.startOrient = LoOrient.get(6);
			this.lstOrients = getOrientListBy(Arrays.asList(7, 3, 4, 8, 5, 1, 2, 6));
			break;
		case 6:
			this.start = MoveCode.MINUS_X;
			this.startOrient = LoOrient.get(3);
			this.lstOrients = getOrientListBy(Arrays.asList(4, 8, 5, 1, 2, 6, 7, 3));
			break;
		case 8:
			this.start = MoveCode.PLUS_Y;
			this.startOrient = LoOrient.get(8);
			this.lstOrients = getOrientListBy(Arrays.asList(5, 1, 2, 6, 7, 3, 4, 8));
			break;

		}
	}

	private List<Integer> getNumberList(int num) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		while (list.size() < num) {
			list.add(list.get(list.size() - 1) + 3);
			list.add(list.get(list.size() - 1) - 1);
		}
		return list;
	}

	private List<LoOrient> getOrientListBy(List<Integer> intList) {
		List<LoOrient> list = new ArrayList<LoOrient>();
		for (Integer i : intList) {
			list.add(LoOrient.get(i));
		}
		return list;
	}

	public List<MoveCode> getMCodes() {
		List<MoveCode> list = new ArrayList<MoveCode>();
		List<LoOrient> expanded = new ArrayList<LoOrient>();
		list.addAll(Arrays.asList(startOrient.getFirst(), startOrient.getSecond()));
		list.add(start);
		list.addAll(Arrays.asList(startOrient.getFirst(), startOrient.getSecond()));
		for (int k = 0; k < numList.size(); k++) {
			switch (k % 8) {
			case 0:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(0));
				break;
			case 1:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(1));
				break;
			case 2:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(2));
				break;
			case 3:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(3));
				break;
			case 4:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(4));
				break;
			case 5:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(5));
				break;
			case 6:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(6));
				break;
			case 7:
				for (int i = 0; i < numList.get(k); i++)
					expanded.add(lstOrients.get(7));
				break;
			}
		}
		for (LoOrient lor : expanded) {
			list.addAll(Arrays.asList(lor.getFirst(), lor.getSecond()));
		}
		return list;
	}
}
