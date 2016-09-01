package BatchProcessor;
/**
 * An exception that should be used to signal problems with the
 * execution of the batch file. 
 */

//This class extends parent exception class
@SuppressWarnings("serial")
public class Process_Exception extends Exception
{
	public Process_Exception(String message) {
		super(message);
	}

	public Process_Exception(String message, Throwable throwable) {
		super(message, throwable);
	}
}

