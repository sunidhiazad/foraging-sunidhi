package entities;

import java.util.List;

import enums.LoOrient;
import enums.MoveCode;

public class ConfigurationLopOrt {

	private List<MoveCode> lstCodes;
	private List<LoOrient> lstOrients;

	public ConfigurationLopOrt(List<MoveCode> lstCodes, List<LoOrient> lstOrients) {
		super();
		this.lstCodes = lstCodes;
		this.lstOrients = lstOrients;
	}

	public List<MoveCode> getLstCodes() {
		return lstCodes;
	}

	public void setLstCodes(List<MoveCode> lstCodes) {
		this.lstCodes = lstCodes;
	}

	public List<LoOrient> getLstOrients() {
		return lstOrients;
	}

	public void setLstOrients(List<LoOrient> lstOrients) {
		this.lstOrients = lstOrients;
	}

	@Override
	public String toString() {
		return "Configuration [lstCodes=" + lstCodes + ", lstOrients=" + lstOrients + "]";
	}

}
