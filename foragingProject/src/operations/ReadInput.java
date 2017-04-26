package operations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Parameters;

public class ReadInput {
	String DELIMITER = ",";

	public ReadInput() {}

	public List<Parameters> readInput() {
		List<Parameters> lstInput = new ArrayList<Parameters>();
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader("files/input/Input.csv"));
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

	private Parameters getAsObject(String line) {
		List<String> lst = GeneralOperations.tokenize(line, DELIMITER);
		Parameters params = new Parameters();
		params.setId(Integer.parseInt(lst.get(0)));
		params.setGridSize(Integer.parseInt(lst.get(1)));
		params.setNumAgents(Integer.parseInt(lst.get(2)));
		params.setPattern(Integer.parseInt(lst.get(3)));
		params.setFoodX(Integer.parseInt(lst.get(4)));
		params.setFoodY(Integer.parseInt(lst.get(5)));
		params.setSeparation(Integer.parseInt(lst.get(6)));
		return params;
	}

}
