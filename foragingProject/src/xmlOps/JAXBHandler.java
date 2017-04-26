package xmlOps;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import entities.Grid;

public class JAXBHandler {
	public static void marshalGrid(Grid state, File selectedFile) throws IOException, JAXBException {
		JAXBContext context;
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(selectedFile));
		context = JAXBContext.newInstance(Grid.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(state, writer);
		writer.close();
	}

	public static Grid unmarshalGrid(File importFile) throws JAXBException {
		Grid state = new Grid();
		JAXBContext context = JAXBContext.newInstance(Grid.class);
		Unmarshaller um = context.createUnmarshaller();
		state = (Grid) um.unmarshal(importFile);
		return state;
	}
}
