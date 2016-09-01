package BatchProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Batch class that sets commands in Hash map
public class Batch {
	String WD;
	List<Command> commandList;
	Map<String, Command> commandMap;
	
	public Batch() {
		commandList = new ArrayList<Command>();
		commandMap = new HashMap<String, Command>();
	}

	public void addCommand(Command command) {
		commandList.add(command);
		commandMap.put(command.id, command);
	}

	public String getWorkingDir() {
		return WD;
	}

	public void setWorkingDir(String workingDir) {
		this.WD = workingDir;
	}

	public List<Command> getCommandList() {
		return commandList;
	}

	public Map<String, Command> getCommandMap() {
		return commandMap;
	}

}
