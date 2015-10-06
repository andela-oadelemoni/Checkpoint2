package checkpoint.andela.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import checkpoint.andela.log.LogWriter;

public class FileStringReader implements Runnable {
	
	// PROPERTIES
	private Path filePath;
	private BufferedReader bufferedReader;
	private Parser parser;
	private LogWriter logWriter;
	private ReactantProcessor processor;
	
	// CONSTRUCTOR
	public FileStringReader(Path filePath, Parser parser, ReactantProcessor processor) {
		this.filePath = filePath;
		this.parser = parser;
		this.processor = processor;
	}
	
	private void readFile() {		
		String line;
		try {
			bufferedReader = Files.newBufferedReader(filePath);
			while ((line = bufferedReader.readLine()) != null) {
				processLine(line);
			}
		    // SEND SIGNAL TO PARSER ON COMPLETION
			processor.setDone(true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readFile();
	}
	
	public void setLogWriter(LogWriter logWriter) {
		this.logWriter = logWriter;
	}
	
	public LogWriter getLogWriter() {
		return logWriter;
	}
	
	private void processLine(String string) {
		if (parser.isValidRecord(string)) {
			processor.addReactantData(parser.buildData(string));
		}
		else if (parser.isCompleteRecord(string)) {
			if (logWriter != null)
				logAction(processor.getReactantUniqueID());
			processor.resetReactant();
		}
	}
	
	private void logAction(String string) {
		logWriter.fileParserLog(string);
	}
	
}
