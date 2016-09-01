package BatchProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Batch processor class contains main method and execution starts from here
public class Batch_Processor {
	static String inputFileName;

	public static void main(String[] args) throws Process_Exception, IOException, InterruptedException {
		
		//Check if Input file exists and path given correctly
		if(args.length > 0) {
			inputFileName = args[0].toLowerCase();
			if(inputFileName.contains("\\")){
				System.out.println("Back slash not allowed...Please Use forward slash in the input i.e work/batchname.xml");
				System.exit(0);
			}
			if(!inputFileName.contains(".xml")){
				System.out.println("Cannot Continue...Please give the input file with .xml extension");
				System.exit(0);
			}
			//creating object for batch parser	
			System.out.println("Input file is given in the arguments");
			Batch_Parser bp = new Batch_Parser();
			System.out.println("Building batch from " + args[0]);
			//calling build batch method
			Batch batch = bp.buildBatch(inputFileName);
			System.out.println("Completed Batch building");
			System.out.println("Batch built is executing...");
			//calling for batch execution
			executeBatch(batch);
			System.out.println("Batch execution is completed!");
		}
		else {
			System.out.println("No Input file given. Please give any XML file as an Input in the arguments");
		}
	}	
	
	public static void executeBatch(Batch batch) throws Process_Exception {
		List<Command> cmdlist = batch.getCommandList();
		Map<String, Command> cmdmap = batch.getCommandMap();
		
		for (Command cmd : cmdlist) {
			System.out.println("Executing: " + cmd.describe());
            cmd.execute(batch.getWorkingDir(), cmdmap);
		}
	}
}
	