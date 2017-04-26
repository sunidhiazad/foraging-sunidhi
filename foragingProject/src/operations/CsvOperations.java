package operations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.SectorTest;

public class CsvOperations {

	static String DELIMITER = ",";

	public static String prepareRow(SectorTest st) {
		StringBuffer row = new StringBuffer();
		row.append(st.getId());
		row.append(DELIMITER);
		row.append(st.getGridSize());
		row.append(DELIMITER);
		row.append(st.getNumAgents());
		row.append(DELIMITER);
		row.append(st.getInPath());
		row.append(DELIMITER);
		row.append(st.getGridCovered());
		row.append(DELIMITER);
		row.append(st.getNumCollisions());
		row.append(DELIMITER);
		row.append(st.getValidPaths());
		row.append(DELIMITER);
		row.append(st.getPercentExtraCoverage());
		row.append(DELIMITER);
		row.append(st.getStdDeviation());
		row.append(DELIMITER);
		row.append(st.getAverage());
		row.append("\n");
		return row.toString();
	}
	
	public static String getHeader(){
		StringBuffer row = new StringBuffer();
		row.append("ID");
		row.append(DELIMITER);
		row.append("gridSize");
		row.append(DELIMITER);
		row.append("numAgents");
		row.append(DELIMITER);
		row.append("inSectorPath");
		row.append(DELIMITER);
		row.append("gridCovered");
		row.append(DELIMITER);
		row.append("numCollisions");
		row.append(DELIMITER);
		row.append("allPathsValid");
		row.append(DELIMITER);
		row.append("percentExtra");
		row.append(DELIMITER);
		row.append("stdDeviation");
		row.append(DELIMITER);
		row.append("average");
		row.append("\n");
		
		return row.toString();
	}

	public static void writeCSV(List<String> list) {
		try {
			File file = new File("files/Output.csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(getHeader());
			for (String s : list) {
				bw.write(s);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void writeOutput(List<SectorTest> list) {
		List<String> strlist=new ArrayList<String>();
		for(SectorTest st:list){
			strlist.add(prepareRow(st));
		}
		writeCSV(strlist);
		
	}
	
	public static List<SectorTest> readInput() {
		List<SectorTest> lstInput = new ArrayList<SectorTest>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader("files/Input.csv"));
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				lstInput.add(getAsObject(line));
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return lstInput;
	}

	private static SectorTest getAsObject(String line) {
		List<String> lst = GeneralOperations.tokenize(line, DELIMITER);
		SectorTest st = new SectorTest();
		st.setId(Integer.parseInt(lst.get(0)));
		st.setGridSize(Integer.parseInt(lst.get(1)));
		st.setNumAgents(Integer.parseInt(lst.get(2)));
		st.setInPath(Boolean.valueOf(lst.get(3)));
		return st;
	}

}
