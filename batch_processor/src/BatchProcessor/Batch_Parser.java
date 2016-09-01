package BatchProcessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//Batch Parser class used for parsing the XML file
public class Batch_Parser {
	
	//This method builds and returns Batch.
	public Batch buildBatch(String batchFileName) throws FileNotFoundException  {
		Batch batch = new Batch();
		try {
			File f = new File(batchFileName);
			FileInputStream fis = new FileInputStream(f);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);
			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();
			for (int id = 0; id < nodes.getLength(); id++) {
				Node node = nodes.item(id);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					Command command = buildCommand(elem);
					batch.addCommand(command);
					if (command instanceof Command_WD_Parse) {
					    batch.setWorkingDir(command.getPath());
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
  			e.printStackTrace();
  			System.exit(0);
		}

		return batch;
	}
	
	//This method returns command class after parsing.
	private Command buildCommand(Element elem) throws DOMException,Process_Exception
	{
		String commandName = elem.getNodeName();
		Command command = null;
		 if (commandName == null) {
			throw new Process_Exception("Unable to parse command from "+ elem.getTextContent());
		 } 
		 switch (commandName.toLowerCase()) {
         case "wd":
        	 command = new Command_WD_Parse();
 			 command.parseCmd(elem);
             break;
         case "file":
        	 command = new Command_File_Parse();
 			 command.parseCmd(elem);
 			 break;
         case "cmd":
        	 command = new Command_Cmd_Parse();
 			 command.parseCmd(elem);
 			 break;
         case "pipe":
        	 command = new Command_Pipe_Parse();
 			 command.parseCmd(elem);
 			 break;
         default: 
        	 throw new Process_Exception("Unknown command " + commandName + " from: "+ elem.getBaseURI());
     }
		return command;
	}
}

