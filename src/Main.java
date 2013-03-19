import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;


public class Main {
	

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException 
	{
		String FILE = "anna.col";
		String FILE2 = "queen11_11.col";

		EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(FILE);
		ea.calculate();
		
		
		//plot stuff
		double[]x = new double[EvolutionaryAlgorithm.MAX_POPULATIONS];
		
		for (int i = 0; i < x.length; i++) {
			x[i] =  i;
		}
	
		double b[] = new double[ea.minims.size()];
		
		for (int i = 0; i < b.length; i++) {
			b[i] = 0.0;
		}
		
		for (int i = 0; i < b.length; i++) {
			b[i] = ea.minims.get(i) * 1.;
		}
		
		double w[] = new double[ea.minims.size()];
		
		for (int i = 0; i < w.length; i++) {
			w[i] = ea.maxis.get(i) * 1.;
		}
		
		double a[] = new double[ea.minims.size()];
		
		for (int i = 0; i < a.length; i++) {
			a[i] = ea.avgs.get(i) * 1.;
		}
		
			 // create your PlotPanel (you can use it as a JPanel)
			  Plot2DPanel plot = new Plot2DPanel();
			 
			  // add a line plot to the PlotPanel
			  plot.addLinePlot("best",x,b);
			  plot.addLinePlot("worst", x,w);
			  plot.addLinePlot("avg", x,a);
			  plot.addLegend("SOUTH");
			  // put the PlotPanel in a JFrame, as a JPanel
			  JFrame frame = new JFrame("a plot panel");
			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			  frame.setSize(1200, 600);
			  frame.setContentPane(plot);
			  frame.setVisible(true);
			  
		
	}
	
	

}
