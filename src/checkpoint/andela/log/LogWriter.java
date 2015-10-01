package checkpoint.andela.log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogWriter implements Runnable {

	// PROPERTIES
	private Path target;
	private LogBuffer logBuffer;
	private boolean done = false;
	
	// CONSTRUCTOR
	public LogWriter (Path targetPath, LogBuffer logBuffer) {
		this.target = targetPath;
		this.logBuffer = logBuffer;
	}

	// IMPLEMENT RUNNABLE INTERFACE METHOD
	@Override
	public void run() {
		logToFile();
	}
	
	// SET LOGGING STATE OF LOGWRITER TO DETERMINE WHEN FILE WRITING BEGINS
	public synchronized void setDone(boolean done) {
		this.done = done;
		notifyAll();
	}
	
	// LOG DATA COMING FROM FILE PARSER
	public void fileParserLog(String string) {
		String logString = "FileParser Thread ("+getCurrentTime()+")---- wrote "+string+" to buffer";
		logBuffer.addLog(logString);
	}
	
	// LOG DATA COMING FROM DBWRITER
	public void dbWriterLog(String string) {
		String logString = "DBWriter Thread ("+getCurrentTime()+")---- collected "+string+" from buffer";
		logBuffer.addLog(logString);
	}
	
	// BEGIN WRITING TO FILE WHEN LOG ADDING IS COMPLETE
	public synchronized void logToFile() {
		while (!done) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writeToFile();
		notifyAll();
	}
	
	private String getCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		return dateFormat.format(new Date());
	}
	
	private void writeToFile() {
		List<String> log_data = logBuffer.getLog();
		try {
			BufferedWriter writer = Files.newBufferedWriter(target);
			
			// WRITE LOG ONE LINE AT A TIME
			for (String line: log_data)
				writer.write(line+"\n");
			
			// CLOSE BUFFERED WRITER WHEN THROUGH
			writer.close();
			
			System.out.println("done!!!");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
