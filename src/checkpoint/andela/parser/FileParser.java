package checkpoint.andela.parser;

import java.util.TreeMap;

public class FileParser implements Parser {
	
	// PROPERTIES
	private ReactantBuffer buffer;
	private TreeMap<String, String> reactant = new TreeMap<>();
	
	// PARSING CONDITIONS
	public static final String LINE_BREAK = "//";
	public static final String COMMENT = "#";
	
	// CONSTRUCTOR
	public FileParser(ReactantBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void formatString(String string) {
		
		String[] keyValueArray = new String[2];
		
		// CHECK FOR RELEVANT STRING
		if (validString(string)) {
			keyValueArray = string.trim().split(" - ");
			// BUILD REACTION
			if (keyValueArray.length > 1)
				reactant.put(keyValueArray[0], keyValueArray[1]);
		}
		// CHECK FOR COMPLETE RECORD SET
		else if(isCompleteRecord(string)) {
			// SEND COMPLETED REACTION TO BUFFER
			buffer.putReactant(reactant);
			// RESET REACTANT OBJECT
			reactant = new TreeMap<>();
		}
		
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
