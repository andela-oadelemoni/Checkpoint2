package checkpoint.andela.test;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import checkpoint.andela.db.DBManager;
import checkpoint.andela.db.DBWriter;
import checkpoint.andela.log.LogBuffer;
import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.Reactant;
import checkpoint.andela.parser.ReactantBuffer;

public class DBWriterTest {
	
	private String config = "/Users/kamiye/Documents/workspace/dbWriterTest.properties";
	private ReactantBuffer stringBuffer = new ReactantBuffer();
	private DBWriter dbWriter;
	private DBManager dbManager = new DBManager(config);
	private Thread dbWriterThread;

	@Before
	public void setUp() throws Exception {
		createTestDBTable();
		Reactant testReactant = new Reactant();
		testReactant.put("NAME", "DBWriterTest");
		stringBuffer.putReactant(testReactant);
		dbWriter = new DBWriter(config, stringBuffer);
		dbWriterThread = new Thread(dbWriter);
	}

	@After
	public void tearDown() throws Exception {
		deleteTestDBTable();
	}

	@Test
	public void testRunnable_writeToDB() throws SQLException {
		String dbData = "DBWriterTest";
		dbWriterThread.start();
		String actualData = getTestDBData();
		
		assertEquals("DBWriter assertion error", dbData, actualData);
	}

	@Test
	public void testLogWriterSetter() {
		LogWriter logWriter = new LogWriter(Paths.get("dummyfile"), new LogBuffer());
		dbWriter.setLogWriter(logWriter);
		
		LogWriter actual = dbWriter.getLogWriter();
		
		assertEquals("DBWriter assertion error", logWriter, actual);
	}
	
	private void createTestDBTable() throws SQLException {
		DataSource dataSource = dbManager.getDataSource();
		
		String queryString = "CREATE TABLE DBWRITERTEST (NAME VARCHAR(255))";
		
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(queryString);
		
	}
	
	private void deleteTestDBTable() throws SQLException {
		DataSource dataSource = dbManager.getDataSource();
		
		String queryString = "DROP TABLE DBWRITERTEST";
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		stmt.executeUpdate(queryString);
	}
	
	private String getTestDBData() throws SQLException {
		DataSource dataSource = dbManager.getDataSource();
		
		String name = "";
		
		String queryString = "SELECT * FROM DBWRITERTEST";

		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(queryString);
		if (result.next()) {
			name = result.getString("name");
		}
		return name;
	}

}
