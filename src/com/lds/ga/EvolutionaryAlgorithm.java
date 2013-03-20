package com.lds.ga;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EvolutionaryAlgorithm {

    public Graph[] pop;
    Random r;
    final static int POP_SIZE = 1000;
    final static int COLORS = 11;
    final static int MAX_POPULATIONS = 100;
    // mutacja
    private double FACTOR_X = 3;
    // krzyzowanie
    private double FERTILITY = 60;
    public ArrayList<Double> minims;
    public ArrayList<Double> avgs;
    public ArrayList<Double> maxis;

    public EvolutionaryAlgorithm(String file) {
        minims = new ArrayList<Double>();
        avgs = new ArrayList<Double>();
        maxis = new ArrayList<Double>();
        init(file, POP_SIZE, COLORS);
    }

    public Graph[] cost(Graph[] pop) {
        for (int i = 0; i < pop.length; i++) {
            pop[i].cost = pop[i].eval();
        }
        return pop;
    }

    public int findMin(Graph[] tab) {
        int min = tab[0].cost;
        for (int i = 1; i < tab.length; i++) {
            if (min > tab[i].cost) {
                min = tab[i].cost;
            }
        }
        return min;
    }

    public int findMax(Graph[] tab) {
        int max = tab[0].cost;
        for (int i = 1; i < tab.length; i++) {
            if (tab[i].cost > max) {
                max = tab[i].cost;
            }
        }
        return max;
    }

    public double findAvg(Graph[] tab) {
        int avg = 0;
        for (int i = 0; i < tab.length; i++) {
            avg += tab[i].cost;
        }
        return avg / tab.length;
    }

    public void init(String file, int pop_size, int colors) {
        r = new Random();
        pop = new Graph[pop_size];
        for (int i = 0; i < pop.length; i++) {
            try {
                pop[i] = new Graph(file);
                pop[i].colorize(colors);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void calculate() throws CloneNotSupportedException {
        int best = -1;
        double avg = -1;
        int worst = -1;
        int population = 0;

        while (best != 0 && population <= MAX_POPULATIONS) {
//            System.out.println("====");
//            System.out.println("populacja: " + population);


            // selekcja
            pop = selection(pop);
            // krzyzowanie
            pop = crossover(pop);
            // mutacja
            pop = mutation(pop);
            //ewaluacja
            pop = cost(pop);

            //stats
            best = findMin(pop);
            minims.add((double) best);
            worst = findMax(pop);
            maxis.add((double) worst);
            avg = findAvg(pop);
            avgs.add(avg);

            population++;
        }
        System.out.println(population);
    }

    private Graph[] selection(Graph[] pop2) {
        pop2 = cost(pop2);
        Graph[] pop3 = new Graph[pop2.length / 5];
        Arrays.sort(pop2);
        for (int i = 0; i < pop3.length; i++) {
            pop3[i] = pop2[i];
//            System.out.println(i + ". " + pop3[i].cost);
        }
        return pop3;
    }

    private Graph[] mutation(Graph[] pop2) {
        int chance;

        for (int i = 0; i < pop2.length; i++) {
            for (int j = 1; j < pop2[i].vertices.length; j++) {
                chance = r.nextInt(100);
                if (chance < FACTOR_X - 1) {
                    pop2[i].vertices[j].color = r.nextInt(COLORS);
                }
            }
        }
        return pop2;
    }

    private Graph[] crossover(Graph[] pop2) throws CloneNotSupportedException {
        // TODO krzy�wanie powinno by� dobrze, teraz kwestia tego, �eby rozmiar
        // nowej populacji by� r�wny POP_SIZE
        Graph[] population = new Graph[POP_SIZE];
        int chance;
        int index = 0;
        r = new Random();
        for (int i = 0; index < POP_SIZE - 1; i = (i + 1) % pop2.length) {

            chance = r.nextInt(100);
            if (chance < FERTILITY - 1) {
                Graph a = pop[i];
                Graph b = pop[r.nextInt(pop.length)];
                Vertex[] v1 = new Vertex[a.vertices.length];
                Vertex[] v2 = new Vertex[a.vertices.length];
                for (int j = 1; j < a.vertices.length; j++) {
                    if (j < a.vertices.length / 2) {
                        v1[j] = (Vertex) a.vertices[j].clone();
                        v2[j] = (Vertex) b.vertices[j].clone();
                    } else {
                        v1[j] = (Vertex) b.vertices[j].clone();
                        v2[j] = (Vertex) a.vertices[j].clone();
                    }
                }
                Graph c = new Graph(v1, -1);
                Graph d = new Graph(v1, -1);
                if (index < POP_SIZE - 1) {
                    population[index] = c;
                    population[index + 1] = d;
                    index += 2;
                } else {
                    population[index] = c;
                    index++;
                }

            }
        }
        return population;
    }
}
