package checkpoint.andela.db;

import java.util.TreeMap;

import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.ReactantBuffer;

public class DBWriter implements Runnable {
	
	// PROPERTIES
	private ReactantBuffer buffer;
	private LogWriter logWriter;
	private DBManager dbObject;
	
	// CONSTRUCTOR
	public DBWriter(String dbConfigPath, ReactantBuffer buffer, LogWriter logWriter) {
		this.buffer = buffer;
		dbObject = new DBManager(dbConfigPath);
		this.logWriter = logWriter;
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
		while (!buffer.isDone()) {
			reactant = buffer.getReactant();
			String queryString = dbObject.generateInsertQuery(reactant);
			dbObject.executeQuery(queryString);
			// LOG ACTION
			logWriter.dbWriterLog(reactant.get("UNIQUE-ID"));
		}
		logWriter.setDone(true);
	}
	
}
