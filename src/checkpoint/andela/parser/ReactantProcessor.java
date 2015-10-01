package checkpoint.andela.parser;

import java.util.TreeMap;

import checkpoint.andela.log.LogWriter;

public class ReactantProcessor {
	
	// PROPERTIES
	private TreeMap<String, String> reactant = new TreeMap<>();
	private ReactantBuffer buffer;
	private LogWriter logWriter;
	// KEY VALUE CONSTANTS
	static final int key = 0;
	static final int value = 1;
	
	public ReactantProcessor (ReactantBuffer buffer, LogWriter logWriter) {
		this.buffer = buffer;
		this.logWriter = logWriter;
	}
	
	public void addReactantData(String[] reactantArray) {
		if (reactantArray.length > 1)
			reactant.put(reactantArray[key], reactantArray[value]);
	}
	
	public void processReactantData() {
		buffer.putReactant(reactant);
		logWriter.fileParserLog(reactant.get("UNIQUE-ID"));
		reactant = new TreeMap<>();
	}
	
	public void setDone(boolean done) {
		buffer.setDone(done);
	}

}
