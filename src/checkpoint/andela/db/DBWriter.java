package checkpoint.andela.db;

import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.Reactant;
import checkpoint.andela.parser.ReactantBuffer;

public class DBWriter implements Runnable {
	
	// PROPERTIES
	private ReactantBuffer buffer;
	private LogWriter logWriter;
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
		Reactant reactant;
		while (!buffer.isDone()) {
			reactant = buffer.getReactant();
			String queryString = dbObject.generateInsertQuery(reactant);
			dbObject.executeQuery(queryString);
			// LOG ACTION
			if (logWriter != null)
				logWriter.dbWriterLog(reactant.get("UNIQUE-ID"));
		}
		logWriter.setDone(true);
	}
	
	public void setLogWriter(LogWriter logWriter) {
		this.logWriter = logWriter;
	}
	
	public LogWriter getLogWriter() {
		return logWriter;
	}
	
}
