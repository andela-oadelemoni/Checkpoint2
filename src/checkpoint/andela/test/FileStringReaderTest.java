package checkpoint.andela.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import checkpoint.andela.parser.FileStringReader;
import checkpoint.andela.parser.Parser;

public class FileStringReaderTest {
	
	private static FileStringReader fileReader;
	private static MockParser mockParser = new MockParser();
	private static Thread thread;
	private static Path pathToTarget = Paths.get("/Users/kamiye/Documents/workspace/fileParser.txt");
	private static String fileContent = "Test File Line 1\nTest File Line 2";
	
	// MOCK PARSER CLASS TO TEST FILE READER CLASS
	private static class MockParser implements Parser {

		public List<String> actual = new ArrayList<>();

		@Override
		public boolean isValidRecord(String string) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isCompleteRecord(String string) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String[] buildData(String string) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createTestFile();
		fileReader = new FileStringReader(pathToTarget, mockParser);
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
		List<String> actualList = mockParser.actual;
		String expected = "Test File Line 1";
		String actual = actualList.get(0);
		
		assertEquals("ReadFile assertion error", expected, actual);
		
		expected = "Test File Line 2";
		actual = actualList.get(1);
		
		assertEquals("ReadFile assertion error", expected, actual);
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
