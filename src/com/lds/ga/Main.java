package com.lds.ga;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException,
			CloneNotSupportedException {
		String FILE = "anna.col";
		String FILE2 = "david.col";
		String FILE3 = "huck.col";
		double best[];
		double worst[];
		double avg[];
		EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(FILE3);
		ea.calculate();
		best = new double[ea.minims.size()];
		worst = new double[ea.minims.size()];
		avg = new double[ea.minims.size()];

		for (int j = 0; j < best.length; j++) {
			best[j] = 0.0;
		}

		for (int a = 0; a < best.length; a++) {
			best[a] = ea.minims.get(a);
		}

		for (int b = 0; b < worst.length; b++) {
			worst[b] = ea.maxis.get(b) * 1.;
		}

		for (int c = 0; c < avg.length; c++) {
			avg[c] = ea.avgs.get(c) * 1.;
		}

		 // create your PlotPanel (you can use it as a JPanel)
		 Plot2DPanel plot = new Plot2DPanel();
		
		
		 // add a line plot to the PlotPanel
		 plot.addLinePlot("best", best);
		 plot.addLinePlot("worst", worst);
		 plot.addLinePlot("avg", avg);
		 plot.addLegend("SOUTH");
		 plot.setAxisLabel(0, "Generacja");
		 plot.setAxisLabel(1, "Konflikty");
		 // put the PlotPanel in a JFrame, as a JPanel
		 JFrame frame = new JFrame("");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setSize(1200, 600);
		 frame.setContentPane(plot);
		 frame.setVisible(true);
		//
		Graph g = new Graph(FILE3);
		int colors[] = new int[g.vertices.length];
		colors = Graph.largestFirst(g);
		for (int i = 1; i < colors.length; i++) {
			g.vertices[i].color = colors[i];
		}
		System.out.println(g.eval());
		System.out.println(findMax(colors)+1);

	}

	public static int findMax(int[] tab) {
		int max = tab[0];
		for (int i = 1; i < tab.length; i++) {
			if (tab[i] > max) {
				max = tab[i];
			}
		}
		return max;
	}

}
