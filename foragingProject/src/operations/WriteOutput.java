package operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Parameters;

public class WriteOutput {
	String DELIMITER = ",";

	public WriteOutput(List<Parameters> params) {
		List<String> strlist = new ArrayList<String>();
		for (Parameters param : params) {
			strlist.add(prepareRow(param));
		}
		writeCSV(strlist);
	}

	private void writeCSV(List<String> strlist) {
		try {
			File file = new File("files/output/Output.csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(getHeader());
			for (String s : strlist) {
				bw.write(s);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String prepareRow(Parameters param) {
		StringBuffer row = new StringBuffer();
		row.append(param.getId());
		row.append(DELIMITER);
		row.append(getSep(param.getSeparation()));
		row.append(DELIMITER);
		row.append(param.getGridSize());
		row.append(DELIMITER);
		row.append(param.getNumAgents());
		row.append(DELIMITER);
		row.append(param.getPattern());
		row.append(DELIMITER);
		row.append(param.getFoodX());
		row.append(DELIMITER);
		row.append(param.getFoodY());
		row.append(DELIMITER);
		row.append(param.getInitDepTS());
		row.append(DELIMITER);
		row.append(param.getFoodDiscoveryTS());
		row.append(DELIMITER);
		row.append(param.getFirstCommTS());
		row.append(DELIMITER);
		row.append(param.getFirstTransTS());
		row.append(DELIMITER);
		row.append(param.getFoodTransportTS());
		row.append("\n");
		return row.toString();
	}

	private String getSep(int separation) {
		if (separation == 1)
			return "0-20";
		if (separation == 2)
			return "20-40";
		if (separation == 3)
			return "40-60";
		if (separation == 4)
			return "60-80";
		if (separation == 5)
			return "80-100";
		if (separation == 6)
			return "0-100";
		return "";
	}

	public String getHeader() {
		StringBuffer row = new StringBuffer();
		row.append("ID");
		row.append(DELIMITER);
		row.append("separation");
		row.append(DELIMITER);
		row.append("gridSize");
		row.append(DELIMITER);
		row.append("numAgents");
		row.append(DELIMITER);
		row.append("pattern");
		row.append(DELIMITER);
		row.append("foodX");
		row.append(DELIMITER);
		row.append("foodY");
		row.append(DELIMITER);
		row.append("initDepTS");
		row.append(DELIMITER);
		row.append("foodDiscoveryTS");
		row.append(DELIMITER);
		row.append("firstCommTS");
		row.append(DELIMITER);
		row.append("firstTransTS");
		row.append(DELIMITER);
		row.append("FoodTransportedTS");
		row.append("\n");
		return row.toString();
	}

	public static void main(String[] args) {
		new WriteOutput(new ReadInput().readInput());
	}

}
