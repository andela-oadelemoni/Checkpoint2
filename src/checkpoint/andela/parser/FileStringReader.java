package checkpoint.andela.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStringReader implements Runnable {
	
	// PROPERTIES
	private Path filePath;
	private BufferedReader bufferedReader;
	private Parser parser;
	
	// CONSTRUCTOR
	public FileStringReader(Path filePath, Parser parser) {
		this.filePath = filePath;
		this.parser = parser;
	}
	
	private void readFile() {		
		String line;
		try {
			bufferedReader = Files.newBufferedReader(filePath);
		    while ((line = bufferedReader.readLine()) != null) {
		        parser.formatString(line);
		    }
		    // SEND SIGNAL TO PARSER ON COMPLETION
		    parser.setDone(true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readFile();
	}
	
}
