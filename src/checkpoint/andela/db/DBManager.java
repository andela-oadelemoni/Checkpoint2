package checkpoint.andela.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import checkpoint.andela.parser.Reactant;

public class DBManager {
	
	private String dbConfigPath;
	private MysqlDataSource mysqlDS = new MysqlDataSource();
	private Properties props = new Properties();
	private DataSource dataSource;
	private FileReader fileReader;
	private String tableName;
	

	public DBManager(String dbConfigPath) {
		this.dbConfigPath = dbConfigPath;
		config();
		dataSource = getDataSource();
	}
	
	private void config() {		
		File file;
		try {
			file = new File(dbConfigPath);
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataSource getDataSource() {
        try {
            props.load(fileReader);
            tableName = props.getProperty("MYSQL_DB_TABLENAME");
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
	
	public void executeQuery(String queryString) {
		
		try (Connection con = dataSource.getConnection()) {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(queryString);
		}
		catch (SQLException e) {
			
		}
	}
	
	public String generateInsertQuery(Reactant dataBaseData) {
		String columnNames = "";
		String columnValues = "";
		
		// BUILD QUERY STRING
	    for (Entry<String, String> entry: dataBaseData.entrySet()) {
	    	
	    	if (entry.equals(dataBaseData.lastEntry())) {	    		
	    		columnNames += "`"+entry.getKey()+"`";
	    		columnValues += "\""+entry.getValue()+"\"";
	    	}
	    	else {
	    		columnNames += "`"+entry.getKey()+"`,";
	    		columnValues += "\""+entry.getValue()+"\",";
	    	}
		}
	    
	    String queryString = "INSERT INTO "
	    		+ tableName + " "
	    		+ "("+columnNames+") VALUES"
	    		+ "("+columnValues+")";
	    
	    return queryString;
	}
	
}
