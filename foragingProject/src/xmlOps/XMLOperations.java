package xmlOps;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import entities.Grid;

public class XMLOperations {
	public static void WriteGridToXML(Grid state) {
		try {
			JAXBHandler.marshalGrid(state, new File("XML/gridstate.xml"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}

	public static Grid ReadGridFromXML() {
		try {
			return JAXBHandler.unmarshalGrid(new File("XML/gridstate.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
