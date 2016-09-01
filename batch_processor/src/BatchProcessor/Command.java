package BatchProcessor;

import java.util.Map;

import org.w3c.dom.Element;
import BatchProcessor.Process_Exception;

//The Abstract Command class has abstract methods.
public abstract class Command {
	String id;
	String path;
	public abstract String describe();
	public abstract void parseCmd(Element element) throws Process_Exception;
	public void execute(String workingDir, Map<String, Command> cmdMap) throws Process_Exception {}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getId() {
		return id;
	}
	public String getPath() {
		return path;
	}	
}
