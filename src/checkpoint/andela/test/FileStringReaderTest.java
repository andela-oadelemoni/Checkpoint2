package checkpoint.andela.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import checkpoint.andela.log.LogBuffer;
import checkpoint.andela.log.LogWriter;
import checkpoint.andela.parser.FileStringReader;
import checkpoint.andela.parser.Reactant;
import checkpoint.andela.parser.ReactantBuffer;
import checkpoint.andela.parser.ReactantFileParser;
import checkpoint.andela.parser.ReactantProcessor;

public class FileStringReaderTest {
	
	private static FileStringReader fileReader;
	private static ReactantFileParser mockParser = new ReactantFileParser();
	private static ReactantBuffer buffer = new ReactantBuffer();
	private static ReactantProcessor processor = new ReactantProcessor(buffer);
	private static Thread thread;
	private static Path pathToTarget = Paths.get("/Users/kamiye/Documents/workspace/fileParser.txt");
	private static String fileContent = "#Test File Line 1\nKey - Value\n//";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createTestFile();
		fileReader = new FileStringReader(pathToTarget, mockParser, processor);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		deleteTestFile();
	}

	@Before
	public void setUp() throws Exception {
		thread = new Thread(fileReader);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRunnable_readFile() throws InterruptedException {
		thread.start();
		Thread.sleep(2000);
		Reactant reactant = processor.getReactant();
		
		assertNotNull("Reactant Creation assertion error", reactant);
		
	}
	
	@Test
	public void testLogWriterSetter() {
		LogWriter logWriter = new LogWriter(Paths.get("dummyfile"), new LogBuffer());
		fileReader.setLogWriter(logWriter);
		
		LogWriter actual = fileReader.getLogWriter();
		
		assertEquals("LogWriter Setter assertion error", logWriter, actual);
	}
	
	private static void createTestFile() throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(pathToTarget);
		// WRITE FILE CONTENT ONE LINE AT A TIME
		writer.write(fileContent);
		// CLOSE BUFFERED WRITER WHEN THROUGH
		writer.close();
	}
	
	private static void deleteTestFile() throws IOException {
		Files.delete(pathToTarget);
	}

}
