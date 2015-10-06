package checkpoint.andela.parser;

import checkpoint.andela.log.LogWriter;

public class ReactantFileParser implements Parser {
	
	// PROPERTIES
	private ReactantProcessor processor = new ReactantProcessor();
	private LogWriter logWriter;
	private ReactantBuffer buffer;
	
	// PARSING CONDITIONS
	public static final String LINE_BREAK = "//";
	public static final String COMMENT = "#";
	
	// CONSTRUCTOR
	public ReactantFileParser(ReactantBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void formatString(String string) {
		String[] keyValueArray;
		// CHECK FOR VALID STRING
		if (validFormat(string)) {
			keyValueArray = string.trim().split(" - ");
			processor.addReactantData(keyValueArray);
		}
		// CHECK FOR COMPLETE RECORD
		else if(isCompleteRecord(string)) {
			if (logWriter != null)
				logAction();
			processor.resetReactant();
		}
		
	}
	
	@Override
	public synchronized void setDone(boolean done) {
		buffer.setDone(done);
	}
	
	public void setLogWriter(LogWriter logWriter) {
		this.logWriter = logWriter;
	}
	
	public LogWriter getLogWriter() {
		return logWriter;
	}
	
	private boolean validFormat(String string) {
		return !string.startsWith(LINE_BREAK) 
				&& !string.startsWith(COMMENT) 
				&& !string.isEmpty();
	}
	
	private boolean isCompleteRecord(String string) {
		return string.startsWith(LINE_BREAK);
	}
	
	private void logAction() {
		String logString = processor.getReactantUniqueID();
		logWriter.fileParserLog(logString);
	}
	
	
}
