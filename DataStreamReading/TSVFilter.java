/**
 * @author Christina Duan cd3136
 * TSVFilter uses the Builder Pattern for its constructor 
 * It records the user's need in a data structure that is used by pipeline
 * 
 */


package Stream;


public class TSVFilter {

	private final String readFile;
	private final String selectName;
	private final String selectValue;

	private final String terminalParam;
	private final TerminalObservation terminalObs;
	
	/**
	 * Constructor for TSVFilter and also part of the Builder pattern
	 * @param file is an object of the innerclass WhichFile
	 */
	public TSVFilter(WhichFile file) {
		this.readFile = file.readFile;
		this.selectName = file.selectName;
		this.selectValue = file.selectValue;
		this.terminalParam = file.terminalParam;
		this.terminalObs = file.terminalObs;	
	}

	public String getFile() {
		return readFile;
	}
	
	/**
	 * 
	 * @return the name of the selected field
	 */
	public String getSelectedName() {
		return selectName;
	}
	
	
	public String getSelectedValue() {
		return selectValue;
	}
	
	/**
	 * @return the name of the selected terminal's field
	 */
	public String getTerminalParam() {
		return terminalParam;
	}
	
	/**
	 * @return the results from the terminal
	 */
	public TerminalObservation getTerminalObs() {
		return terminalObs;
	}
	
	
	@Override
	public String toString() {
		String terminalOption= new String();
		if(terminalObs == TerminalObservation.COUNT)
			terminalOption = "TerminalObservation.COUNT";
		else if (terminalObs == TerminalObservation.MIN)
			terminalOption = "TerminalObservation.MIN";
		else if (terminalObs == TerminalObservation.MAX)
			terminalOption = "TerminalObservation.MAX";
		else if (terminalObs == TerminalObservation.ISSAME)
			terminalOption = "TerminalObservation.ISSAME";
		else if (terminalObs == TerminalObservation.ISSORTED)
			terminalOption = "TerminalObservation.ISSORTED";		
		
		return "TSVFilter "
				+ "\n{ fileName = " + readFile +   "\n"
				+ ", selectName = "  + selectName
				+ ", selectValue = " + selectValue +"\n"			
				+ ", terminalParam = " + terminalParam + "\n"
				+ ", terminalObs = " + terminalOption +"\n"
				+ "}\n";	
	}
	
	
	/**
	 * @author Christina Duan cd3136
	 * WhichFile is the inner class  
	 */
	public static class WhichFile{
	
		private final String readFile;
		private String selectName;
		private String selectValue;

		private String terminalParam;
		private TerminalObservation terminalObs;
	
		/**
		 * 
		 * @param file
		 */
		public WhichFile (String file) {
			readFile = file;
		}
		
		/**
		 * Selects which field and value to filter from the file
		 * @param name1 field that is selected by user
		 * @param name2 value that is selected by user
		 * @return
		 */
		public WhichFile select(String name1, String name2) {
			selectName = name1;
			selectValue = name2;
			return this;			
		}
		
		/**
		 * Selects which field and what enum operation to use for a terminal stream operation
		 * @param termName the field/value
		 * @param termEnum the enum operation
		 * @return
		 */
		public WhichFile terminate (String termName, TerminalObservation termEnum) {
			terminalParam = termName;
			terminalObs = termEnum;
			return this;
		}
		
		/**
		 * done executes the method
		 * creates an instance of TSVFilter and constructs it
		 * @return 
		 */
		public TSVFilter done(){
			return new TSVFilter(this);
		}
	}
	
}
