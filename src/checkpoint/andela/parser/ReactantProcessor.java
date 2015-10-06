package checkpoint.andela.parser;


public class ReactantProcessor {
	
	// PROPERTIES
	private Reactant reactant = new Reactant();
	private ReactantBuffer buffer;
	// KEY VALUE CONSTANTS
	static final int key = 0;
	static final int value = 1;
	
	public ReactantProcessor(ReactantBuffer buffer) {
		this.buffer = buffer;
	}
	
	public void addReactantData(String[] reactantArray) {
		if (reactantArray.length > 1)
			reactant.put(reactantArray[key], reactantArray[value]);
	}
	
	public void resetReactant() {
		buffer.putReactant(reactant);
		reactant = new Reactant();
	}
	
	public String getReactantUniqueID() {
		String reactantUniqueID = reactant.get("UNIQUE-ID");
		return reactantUniqueID;
	}
	
	public Reactant getReactant() {
		return reactant;
	}
	
	public void setDone(boolean done) {
		buffer.setDone(done);
	}
	
}
