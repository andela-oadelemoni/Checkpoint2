package checkpoint.andela.parser;


public class ReactantFileParser implements Parser {
	
	// PARSING CONDITIONS
	public static final String LINE_BREAK = "//";
	public static final String COMMENT = "#";
	
	
	@Override
	public boolean isValidRecord(String string) {
		return !string.startsWith(LINE_BREAK) 
				&& !string.startsWith(COMMENT) 
				&& !string.isEmpty();
	}
	
	@Override
	public boolean isCompleteRecord(String string) {
		return string.startsWith(LINE_BREAK);
	}
	
	@Override
	public String[] buildData(String string) {
		String[] keyValueArray = string.trim().split(" - ");
		return keyValueArray;
	}
	
}
