package BatchProcessor;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Command_Pipe_cmdCommand extends Command_Cmd_Parse {
	 private InputStream inputStream;
	    private OutputStream outputStream;
		
		@Override
		public String describe() {
			return "PipeCmd Command: " + id + ", " + path;
		}

		@Override
		public void execute(String workingDir, Map<String, Command> cmdMap) throws Process_Exception {
		  System.out.println("Executing: " + describe());
		  try {
			List<String> command = new ArrayList<String>();
			command.add(path);
			command.addAll(cmdArgs);
			
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.directory(new File(workingDir));
			File wd = builder.directory();

			//System.out.println("Executing: " + describe() + " 2");
			
			if (inID != null && !inID.trim().isEmpty()) {
				Command cmd = cmdMap.get(inID);
				builder.redirectInput(new File(wd, cmd.getPath()));
			}
			
			if (outID != null && !outID.trim().isEmpty()) {
				Command cmd = cmdMap.get(outID);
				builder.redirectOutput(new File(wd, cmd.getPath()));
			}
			
			Process process = builder.start();

			if (outID == null || outID.trim().isEmpty()) {
				inputStream = process.getInputStream();
			}
			
			if (inID == null || inID.trim().isEmpty()) {
				outputStream = process.getOutputStream();
			}
		  }
		  catch (Exception e) {
		    e.printStackTrace();
			throw new Process_Exception("Error executing cmd " + id, e);
		  }
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public OutputStream getOutputStream() {
			return outputStream;
		}
}
