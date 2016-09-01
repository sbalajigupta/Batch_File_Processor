package BatchProcessor;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import BatchProcessor.Command;

//This Pipe Command class implements command class and implements its methods
public class Command_Pipe_Parse extends Command{
	
	Command_Pipe_cmdCommand pipecmd1;
	Command_Pipe_cmdCommand pipecmd2;
	public String describe() {
		return "Pipe Command";
	}
	
	public void parseCmd(Element element) throws Process_Exception{
		System.out.println("Parsing pipe Command in input xml file");
		NodeList nodes = element.getChildNodes();
		for (int idx = 0; idx < nodes.getLength(); idx++) {
			Node node = nodes.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elm = (Element) node;
	        	if (pipecmd1 == null) {
	        		pipecmd1 = new Command_Pipe_cmdCommand();
	        		pipecmd1.parseCmd(elm);
	        	}
	        	else if (pipecmd2 == null) {
	        		pipecmd2 = new Command_Pipe_cmdCommand();
	        		pipecmd2.parseCmd(elm);
	        	}
	        	parseCmd(elm);
			}
		}
		
	}
	
	
	@Override
	public void execute(String workingDir, Map<String, Command> cmdLookUp)  throws Process_Exception {
	  //System.out.println("Executing: " + id);
	  try {
		pipecmd1.execute(workingDir, cmdLookUp);
		pipecmd2.execute(workingDir, cmdLookUp);
		
		InputStream inputStream = pipecmd1.getInputStream();
		OutputStream outputStream = pipecmd2.getOutputStream();
		
		int achar;
		while ((achar = inputStream.read()) != -1) {
			outputStream.write(achar);
		}
		outputStream.flush();
		outputStream.close();
		
		System.out.println("Completed: " + pipecmd1.describe());
		System.out.println("Completed: " + pipecmd2.describe());
		System.out.println("Completed: " + describe());
	  }
	  catch (Exception e) {
		throw new Process_Exception("Error executing cmd " + id, e);
	  }
	}
	
}
