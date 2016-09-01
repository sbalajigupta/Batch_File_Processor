package BatchProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

//CMD Command Class implements Command Class and implements its methods.
public class Command_Cmd_Parse extends Command {
	String path;
	List<String> cmdArgs;
	String inID;
	String outID;

	public String describe() {
		return "A CMD command {" + path + "," + cmdArgs.toString() + "} is executed.";
	}

	// Sets the ID,Path,inID,OutID for cmd Command
	public void parseCmd(Element element) throws Process_Exception {
		System.out.println("Parsing CMD Command in input xml file");
		id = element.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new Process_Exception("Missing ID in CMD Command");
		}
		path = element.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new Process_Exception("Missing PATH in CMD Command");
		}
		// Arguments must be passed to ProcessBuilder as a list of individual
		// strings.
		cmdArgs = new ArrayList<String>();
		String arg = element.getAttribute("args");
		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}

		inID = element.getAttribute("in");
		outID = element.getAttribute("out");
	}

	public void execute(String workingDir, Map<String, Command> cmdMap) throws Process_Exception {
		// System.out.println("Executing: " + id);
		try {
			if (inID == null || outID == null) {
				// This must be a Pipe cmd, return since it will be executed
				// somewhere else.
				return;
			}

			List<String> command = new ArrayList<String>();
			command.add(path);
			command.addAll(cmdArgs);

			ProcessBuilder builder = new ProcessBuilder(command);
			builder.directory(new File(workingDir));
			File wd = builder.directory();

			if (inID != null && !inID.trim().isEmpty()) {
				Command cmd = cmdMap.get(inID);
				if (cmd != null) {
					builder.redirectInput(new File(wd, cmd.getPath()));
				} else {
					throw new Process_Exception("Unable to locate IN file command for id: " + inID);
				}
			}

			if (outID != null && !outID.trim().isEmpty()) {
				Command cmd = cmdMap.get(outID);
				if (cmd != null) {
					builder.redirectOutput(new File(wd, cmd.getPath()));
				} else {
					throw new Process_Exception("Unable to locate OUT file command: " + outID);
				}
			}

			Process process = builder.start();
			process.waitFor();

			System.out.println("Execution Completed: " + describe());
		} catch (Process_Exception pe) {
			throw pe;
		} catch (Exception e) {
			throw new Process_Exception("Error executing cmd " + id, e);
		}
	}
}
