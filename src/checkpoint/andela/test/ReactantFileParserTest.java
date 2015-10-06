package checkpoint.andela.test;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import checkpoint.andela.log.LogBuffer;
import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.ReactantBuffer;
import checkpoint.andela.parser.ReactantFileParser;

public class ReactantFileParserTest {

	private static String testValidFormat = "Key - Value";
	private static String testLineEnd = "//";
	private static ReactantFileParser fileParser;
	private static ReactantBuffer buffer = new ReactantBuffer();
	
	// SETUP FOR LOGWRITER TEST
	private Path pathToTarget = Paths.get("/Users/kamiye/Documents/workspace/logtest.txt");
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fileParser = new ReactantFileParser(buffer);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testFormatString_validFormat() throws InterruptedException {
		fileParser.formatString(testValidFormat);
		fileParser.formatString(testLineEnd);
		String expectedKey = "Key";
		String expectedValue = "Value";
		TreeMap<String, String> reactantTreeMap = buffer.getReactant();
		assertEquals("Reactant key format assertion error", expectedKey, reactantTreeMap.firstKey());
		
		assertEquals("Reactant key format assertion error", expectedValue, reactantTreeMap.get(reactantTreeMap.firstKey()));
	}
	
	@Test
	public void testSetLogWriter() {
		LogWriter logWriter = new LogWriter(pathToTarget, new LogBuffer());
		fileParser.setLogWriter(logWriter);
		LogWriter actual = fileParser.getLogWriter();
		
		assertEquals("LogWriter setter error", logWriter, actual);
	}

}
