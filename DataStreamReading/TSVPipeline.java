/**
 * @author Christina Duan cd3136
 * TSVPipeline reads the tsv file, gets headers, and supports selection for what is piped to the output stream
 * If there is no selection or terminal - the pipeline will just do nothing elegantly, just forming each line properly
 * It assumes that the tsv file will only have longs and Strings
 * 
 */
package Stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class TSVPipeline {

	private TSVFilter filteredFile;

	private boolean validFile = true;

	private String[] headerFields = null;
	private String[] headerTypes = null;

	private BufferedWriter fileWriter = null;

	private static final String tab = "\\t";
	private static final String makeLine = "\t";
	private static final String outputTSVFile = "C:\\Users\\duanc\\Programs\\eclipse-workspace\\HW3\\src\\Stream\\myoutput.tsv";

	private int filteredField = 0;
	private int terminalField = 0;
	private int terminalCount;
	private long terminalMin;
	private long terminalMax;

	/**
	 * Constructor for the pipeline
	 * @param filter - TSVFilter object that is then set to private TSVFilter variable
	 */
	public TSVPipeline(TSVFilter filter) {
		filteredFile = filter;
	}

	/**
	 * The doit() method reads the tsv file one record at a time
	 * checks the record for proper form
	 * and outputs the record to a tsv file if it's properly formatted
	 * Displays what is filtered/selected along with terminals
	 * If there are exceptions to the tsv file, the reading will stop 
	 */
	public void doit() {
		BufferedReader TSVFile = null;
		String line = "";
		int lineNum = 0;

		try {
			TSVFile = new BufferedReader(new FileReader(filteredFile.getFile()));
			while ((line = TSVFile.readLine()) != null) {
				lineNum++;

				if (lineNum == 1) {
					processHeader(line);

				} else if (lineNum == 2) {
					processHeaderTypes(line);
				} else {
					processData(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (TSVFile != null) {
				try {
					TSVFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (filteredFile.getTerminalParam() != null) {
			terminalResults();
		}

	}

	private void processHeader(String line) {
		headerFields = line.split(tab);
		for (int i = 0; i < headerFields.length; i++) {
			if (filteredFile.getSelectedName() != null) {
				if (headerFields[i].trim().equalsIgnoreCase(filteredFile.getSelectedName())) {
					this.filteredField = i;
					break;
				}
			}
		}

		for (int i = 0; i < headerFields.length; i++) {
			if (filteredFile.getTerminalParam() != null) {
				if (headerFields[i].trim().equalsIgnoreCase(filteredFile.getTerminalParam())) {
					this.terminalField = i;
					break;
				}
			}

		}

		if (filteredFile.getSelectedName() == null) {
			convertToFile(headerFields);
		}

	}

	private void processHeaderTypes(String line) {
		headerTypes = line.split(tab);

		if (headerTypes.length != headerFields.length) {
			validFile = false;
		}

		validTypes();

		if (filteredFile.getSelectedValue() == null) {
			convertToFile(headerTypes);
		}

	}

	private void validTypes() {
		for (int i = 0; i < headerTypes.length; i++) {
			if (!headerTypes[i].trim().equalsIgnoreCase("String") && !headerTypes[i].trim().equalsIgnoreCase("long")) {
				validFile = false;
			}
		}
	}

	private void processData(String line) {

		String[] data = line.split(tab);

		if (validFile == false) {
			return;
		}

		if (filteredFile.getSelectedName() == null) {
			convertToFile(data);
		} else {
			if (validData(data) == true) {
				convertToFile(data);
				System.out.println("Filtered record: " + Arrays.toString(data));

				if (filteredFile.getTerminalParam() != null) {
					processTerminal(data);
				}
			}
		}

	}

	private void convertToFile(String[] lineArray) {
		try {
			if (fileWriter == null) {
				fileWriter = new BufferedWriter(new FileWriter(outputTSVFile));
			}
			for (int i = 0; i < lineArray.length; i++) {
				if (i != 0) {
					fileWriter.write(makeLine);
				}
				fileWriter.write(lineArray[i]);
			}
			fileWriter.newLine();
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean validData(String[] data) {
		boolean isValid = true;
		String dataField = data[filteredField].trim();
		String dataValue = filteredFile.getSelectedValue().trim();

		for (int i = 0; i < data.length; i++) {
			if (headerTypes[i].trim().equalsIgnoreCase("long")) {
				try {
					long longData = Long.parseLong(data[i]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid data in the record");
					isValid = false;
					return isValid;
				}
			}
		}

		if (!dataField.equalsIgnoreCase(dataValue)) {
			isValid = false;
		}
		return isValid;

	}

	private void processTerminal(String[] data) {

		if (filteredFile.getTerminalObs() == TerminalObservation.COUNT) {
			terminalCount++;
		}

		else if (filteredFile.getTerminalObs() == TerminalObservation.MIN) {

			if (headerTypes[terminalField].trim().equalsIgnoreCase("long")) {
				long longData = 0;
				{
					try {
						longData = Long.parseLong(data[terminalField]);
					} catch (NumberFormatException e) {
						System.out.println("Invalid data in file");
					}
					if (terminalMin == 0) {
						terminalMin = longData;
					} else {
						terminalMin = Math.min(terminalMin, longData);
					}
				}
			} else {
				terminalMin = Math.min(terminalMin, data[terminalField].length());
			}

		}

	else if(filteredFile.getTerminalObs()==TerminalObservation.MIN)	{
			if(headerTypes[terminalField].trim().equalsIgnoreCase("long")) {
				long longData = 0;
				
					try {
						longData = Long.parseLong(data[terminalField]);
					}
					catch(NumberFormatException e) {
						System.out.println("Invalid data in file");
					}
					if(terminalMax == 0) {
						terminalMax = longData;
					}
					else {
						terminalMax = Math.max(terminalMax, longData);
					}
				}
					else {
						terminalMax = Math.max(terminalMax, data[terminalField].length());
					}
				
				}
			}

	private void terminalResults() {
		String result = new String();
		
		if(filteredFile.getTerminalObs()== TerminalObservation.COUNT) {
			result = "From TSVPipeline: COUNT is: " + terminalCount +" \n";
			System.out.println(result); 	
			}
		else if(filteredFile.getTerminalObs() == TerminalObservation.MIN )
			{
				result = "From TSVPipeline: MIN is: " + terminalMin +" \n";
				
				System.out.println(result);
			}
		else if(filteredFile.getTerminalObs() == TerminalObservation.MAX )
		{
			result = "From TSVPipeline: MAX is: " + terminalMax +" \n";
			
			System.out.println(result);
		}
	}

}
