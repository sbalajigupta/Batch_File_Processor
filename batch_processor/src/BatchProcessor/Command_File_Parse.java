package BatchProcessor;

import org.w3c.dom.Element;

import BatchProcessor.Command;
import BatchProcessor.Process_Exception;

//This File Command class implements command class and implements its methods
public class Command_File_Parse extends Command {
	String path;

	public String describe() {		
		return "A File command with path "+path+" is executed.";
	}
	//sets the ID and Path for File Command
	public void parseCmd(Element element) throws Process_Exception {
		System.out.println("Parsing File Command in input xml file");
		id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new Process_Exception("Missing ID in CMD Command");
		}
		path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new Process_Exception("Missing PATH in CMD Command");
		}	
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public String getPath() {
		return path;
	}
}
