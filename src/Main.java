import java.io.FileNotFoundException;


public class Main {
	

	public static void main(String[] args) throws FileNotFoundException 
	{
		final int MAX_POPULATIONS = 5;
		String FILE = "anna.col";
		String FILE2 = "queen11_11.col";

		EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(MAX_POPULATIONS,FILE);
	}
	
	

}
