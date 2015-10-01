package checkpoint.andela.parser;

import checkpoint.andela.log.LogWriter;

public class ReactantFileParser implements Parser {
	
	// PROPERTIES
	private ReactantProcessor processor;
	
	// PARSING CONDITIONS
	public static final String LINE_BREAK = "//";
	public static final String COMMENT = "#";
	
	// CONSTRUCTOR
	public ReactantFileParser(ReactantBuffer buffer, LogWriter logWriter) {
		processor = new ReactantProcessor(buffer, logWriter);
	}

	@Override
	public void formatString(String string) {
		String[] keyValueArray;
		// CHECK FOR VALID STRING
		if (validString(string)) {
			keyValueArray = string.trim().split(" - ");
			processor.addReactantData(keyValueArray);
		}
		// CHECK FOR COMPLETE RECORD
		else if(isCompleteRecord(string)) {
			processor.processReactantData();
		}
		
	}
	
	@Override
	public synchronized void setDone(boolean done) {
		processor.setDone(done);
	}
	
	private boolean validString(String string) {
		return !string.startsWith(LINE_BREAK) 
				&& !string.startsWith(COMMENT) 
				&& !string.isEmpty();
	}
	
	private boolean isCompleteRecord(String string) {
		return string.startsWith(LINE_BREAK);
	}
	
}
