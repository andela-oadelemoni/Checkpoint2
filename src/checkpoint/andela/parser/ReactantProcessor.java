package checkpoint.andela.parser;


public class ReactantProcessor {
	
	// PROPERTIES
	private Reactant reactant = new Reactant();
	// KEY VALUE CONSTANTS
	static final int key = 0;
	static final int value = 1;
	
	public void addReactantData(String[] reactantArray) {
		if (reactantArray.length > 1)
			reactant.put(reactantArray[key], reactantArray[value]);
	}
	
	public void resetReactant() {
		reactant = new Reactant();
	}
	
	public String getReactantUniqueID() {
		String reactantUniqueID = reactant.get("UNIQUE-ID");
		return reactantUniqueID;
	}
	
}
