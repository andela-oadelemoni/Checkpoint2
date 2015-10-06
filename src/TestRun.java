import java.nio.file.Path;
import java.nio.file.Paths;

import checkpoint.andela.db.DBWriter;
import checkpoint.andela.log.LogBuffer;
import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.ReactantFileParser;
import checkpoint.andela.parser.FileStringReader;
import checkpoint.andela.parser.ReactantBuffer;

public class TestRun {
	
	private static final String dirPath = "/Users/kamiye/Documents/workspace/";
	private static final Path sourcePath = Paths.get(dirPath+"reactions.dat");
	private static final Path targetPath = Paths.get(dirPath+"log.txt");
	private static final String dbConfigPath = dirPath+"db.properties";
	
	// BUFFERS
	ReactantBuffer buffer = new ReactantBuffer();
	LogBuffer logBuffer = new LogBuffer();
	// OBJECTS
	LogWriter logWriter = new LogWriter(targetPath, logBuffer);
	ReactantFileParser fileParser = new ReactantFileParser(buffer);
	FileStringReader fileReader = new FileStringReader(sourcePath, fileParser);
	DBWriter dbWriter = new DBWriter(dbConfigPath, buffer);
	
	// SET LOGWRITER FOR LOGGING ACTIONS
	{
		fileParser.setLogWriter(logWriter);
		dbWriter.setLogWriter(logWriter);
	}
	
	// THREADS
	Thread fileReaderThread = new Thread(fileReader);
	Thread dbWriterThread = new Thread(dbWriter);
	Thread logWriterThread = new Thread(logWriter);
	
	
	// main method to start the threads above
	public static void main (String[] args) {
		TestRun testRun = new TestRun();
		testRun.runTest();
	}
	
	public void runTest() {
		fileReaderThread.start();
		dbWriterThread.start();
		logWriterThread.start();
	}
}
