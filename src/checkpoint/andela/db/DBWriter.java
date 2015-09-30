package checkpoint.andela.db;

import java.util.TreeMap;

import checkpoint.andela.parser.ReactantBuffer;

public class DBWriter implements Runnable {
	
	// PROPERTIES
	private ReactantBuffer buffer;
	private DBManager dbObject;
	
	// CONSTRUCTOR
	public DBWriter(String dbConfigPath, ReactantBuffer buffer) {
		this.buffer = buffer;	
		dbObject = new DBManager(dbConfigPath);
	}

	// IMPLEMENT RUNNABLE INTERFACE METHOD
	@Override
	public void run() {
		writeToDB();
	}
	
	// METHOD TO WRITE FILE CONTENT TO THE DB FROM THE BUFFER
	private synchronized void writeToDB() {
	
		// GETS COMPLETE REACTANT DATA AND WRITES TO DATABASE
		TreeMap<String, String> reactant;
		while ((reactant = buffer.getReactant()) != null) {
			String queryString = dbObject.generateInsertQuery(reactant);
			dbObject.executeQuery(queryString);
		}
	}
	
}
