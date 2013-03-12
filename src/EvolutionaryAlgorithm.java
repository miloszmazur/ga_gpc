import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

public class EvolutionaryAlgorithm {
	public Graph[] pop;
	Random r;
	final int POP_SIZE = 10;
	final int COLORS = 11;
	public int max_populations;
	//mutacja
	private double FACTOR_X;
	//krzyzowanie
	private double FERTILITY; 

	public EvolutionaryAlgorithm(int max_populations,String file) {
		this.max_populations = max_populations;
		init(file, POP_SIZE, COLORS);
	}

	
	public int[]cost(Graph[]pop)
	{
		int[]costs = new int[pop.length];
		for (int i = 0; i < pop.length; i++) {
			costs[i] = pop[i].eval();
		}
		return costs;
	}
	
	public int findMin(int[]tab)
	{
		int min = tab[0];
		for (int i = 1; i < tab.length; i++) 
		{
			if(tab[i] > min)
				min = tab[i];
		}
		return min;
	}

	public void init(String file, int POP_SIZE, int colors) 
	{
		r = new Random();
		pop = new Graph[POP_SIZE];
		for (int i = 0; i < pop.length; i++) {
			try {
				pop[i] = new Graph(file);
				pop[i].colorize(colors);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void calculate()
	{
		int conflicts = -1;
		int population = 0;
		Graph perfect_match;
		int[]costF;
		while(conflicts > 0 && population <= max_populations)
		{
			population++;
			//selekcja TODO
			pop = selection(pop);
			//krzyzowanie
			pop = crossover(pop);
			//mutacja
			pop = mutation(pop);
			
			costF = cost(pop);
			conflicts = findMin(costF);
		}
	}


	

	private Graph[] selection(Graph[] pop2) {
		int[]cost = cost(pop);
		int[]pop = new int[pop2.length/2]; 
		
		return pop;
	}

	private Graph[] mutation(Graph[] pop2) {
		int chance;
		
		for (int i = 0; i < pop2.length; i++) 
		{
			for (int j = 0; j < pop2[i].vertices.length; j++) 
			{
				chance = r.nextInt(100);
				if(chance < FACTOR_X-1)
					pop2[i].vertices[j].color = r.nextInt(COLORS);
			}
		}
		return pop2;
	}


	private Graph[] crossover(Graph[] pop2) {
		int chance;
		for (int i = 0; i < pop2.length; i++) 
		{
			chance = r.nextInt(100);
			if(chance<FERTILITY-1)
			{
				Graph a = pop2[i];
				Graph b = pop2[r.nextInt(pop2.length)];
				
				int chance2 = r.nextInt(100);
				if(chance2<50)
				{
					Vertex[]v = new Vertex[a.vertices.length];
					for(int j = 0; j < v.length; j++) 
					{
						if(j < v.length/2)
							v[j] = a.vertices[j];
						else v[j] = b.vertices[j];
					}
					Graph c = new Graph(v);
					if(pop2.length<POP_SIZE-1)
					{
						Graph[] pop = Arrays.copyOf(pop2, pop2.length+1);
						pop[pop.length-1] = c;
					}
				}
				else
				{
					Vertex[] v1 = new Vertex[a.vertices.length];
					Vertex[] v2 = new Vertex[b.vertices.length];
					for(int j = 0; j < v1.length; j++) 
					{
						if(j < v1.length/2)
							v1[j] = a.vertices[j];
						else v1[j] = b.vertices[j];
					}
					
					for(int j = 0; j < v2.length; j++) 
					{
						if(j < v2.length/2)
							v2[j] = b.vertices[j];
						else v2[j] = a.vertices[j];
					}
					Graph c = new Graph(v1);
					Graph d = new Graph(v2);
					if(pop2.length<POP_SIZE-2)
					{
						Graph[] pop = Arrays.copyOf(pop2, pop2.length+2);
						pop[pop.length-2] = c;
						pop[pop.length-1] = d;
					}
				}
				
			}
		}
		return pop;
	}

}
