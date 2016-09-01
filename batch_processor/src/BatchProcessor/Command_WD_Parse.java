package BatchProcessor;

import java.util.List;
import org.w3c.dom.Element;
import BatchProcessor.Command;
import BatchProcessor.Process_Exception;

//This WD Command class implements command class and implements its methods.
public class Command_WD_Parse  extends Command{
	String path;
	String id;
	List<String> cmdArgs;
	String inID;
	String outID;
	
	public String describe() {
		return "A WD command with path "+path+" is executed.";
	}
	//Set ID and Path for WD command
	public void parseCmd(Element element) throws Process_Exception {
		System.out.println("Parsing WD Command in input xml file");
		id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new Process_Exception("Missing ID in WD Command");
		}	
		path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new Process_Exception("Missing PATH in WD Command");
		}
		setId(id);
		setPath(path);
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
