package checkpoint.andela.parser;


public interface Parser {
	
	// METHOD TO WORK ON STRING
	public boolean isValidRecord(String string);
	
	public boolean isCompleteRecord(String string);
	
	public String[] buildData(String string);
	
}
