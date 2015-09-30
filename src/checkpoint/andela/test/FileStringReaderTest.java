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

import checkpoint.andela.parser.FileStringReader;

public class FileStringReaderTest {
	
	private FileStringReader fileReader;
	private static Path pathToTarget = Paths.get("/Users/kamiye/Documents/workspace/fileParser.txt");
	private static String fileContent = "Test File Line 1\nTest File Line 2";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createTestFile();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		deleteTestFile();
	}

	@Before
	public void setUp() throws Exception {
		fileReader = new FileStringReader(pathToTarget);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testReadFile() {
	}
	
	private static void createTestFile() throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(pathToTarget);
		// WRITE LOG ONE LINE AT A TIME
		writer.write(fileContent);
		// CLOSE BUFFERED WRITER WHEN THROUGH
		writer.close();
	}
	
	private static void deleteTestFile() throws IOException {
		Files.delete(pathToTarget);
	}

}
