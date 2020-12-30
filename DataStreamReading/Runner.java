/**
 * @author Christina Duan cd3136
 * The Runner class is where the user puts a short list of instructions for how to process a data stream
 * The user can specify a field name and a value
 * 
 */

package Stream;


public class Runner {
	public static void main (String[] args)
	{
		TSVFilter myTSVFilter = new TSVFilter
				.WhichFile("C:\\Users\\duanc\\Programs\\eclipse-workspace\\HW3\\src\\Stream\\myTSV.tsv")
				.select("Type","Fire")
				.terminate("DexNum", TerminalObservation.COUNT)
				.done();
		System.out.println(myTSVFilter);
		new TSVPipeline(myTSVFilter).doit();
	}

}
