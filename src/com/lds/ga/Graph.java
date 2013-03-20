package com.lds.ga;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("rawtypes")
public class Graph implements Comparable {
	private Vertex v;
	public Vertex[] vertices;
	private Scanner in;
	private Random r;
	public int cost;

	public Graph(Vertex[] vertices, int cost) {
		this.vertices = vertices;
		this.cost = cost;
	}

	public Graph(String file) throws FileNotFoundException {
		load(file);
	}

	private Graph load(String file) throws FileNotFoundException {
		File f = new File(file);
		in = new Scanner(f);
		while (in.next().equals("c")) {
			in.nextLine();
		}
		in.next();
		int a = in.nextInt();
		in.next();
		vertices = new Vertex[a + 1];

		while (in.hasNext()) {
			String s = in.next();
			int i = in.nextInt();
			if (vertices[i] == null) {
				v = new Vertex(new LinkedList<Integer>(), 0);
				v.addNeighbour(in.nextInt());
				vertices[i] = v;
			} else {
				vertices[i].addNeighbour(in.nextInt());
			}
		}

		return new Graph(vertices, -1);
	}

	public String toString() {
		return "Vertices: " + getVertices() + ", Edges: " + getEdges()
				+ ", conflicts: " + cost;
	}

	public int getEdges() {
		int edges = 0;
		for (int i = 1; i < vertices.length; i++) {
			edges += vertices[i].neighbours.size();
		}
		return edges;
	}

	public int getVertices() {
		return vertices.length - 1;
	}

	public void colorize(int tops) {
		for (int i = 1; i < vertices.length; i++) {
			r = new Random();
			vertices[i].color = r.nextInt(tops);
		}
	}

	public int eval() {
		int conflicts = 0;
		for (int i = 1; i < vertices.length; i++) {
			for (int j = 0; j < vertices[i].neighbours.size(); j++) {
				if (vertices[i].color == vertices[vertices[i].neighbours.get(j)].color)
					conflicts++;
			}
		}
		return conflicts;
	}

	@SuppressWarnings("unchecked")
	public static int[] largestFirst(Graph pop) {
		LinkedList temp = new LinkedList();
		int[] res = new int[pop.vertices.length];
		int color = 0;
		int largestIndex, largestNeighbour;
		int index;
		for (int i = 0; i < res.length; i++) {
			res[i] = -1;
		}
		while (temp.size() < pop.vertices.length) {
			largestIndex = 1;
			largestNeighbour = 0;
			for (int i = 1; i < pop.vertices.length; i++) {
				if (!temp.contains(i)) {
					if (largestNeighbour == 0) {
						largestIndex = i;
						largestNeighbour = pop.vertices[i].neighbours.size();
					} else if (pop.vertices[i].neighbours.size() > largestNeighbour) {
						largestIndex = i;
						largestNeighbour = pop.vertices[i].neighbours.size();
					}
				}
			}
			color = 0;
			temp.add(largestIndex);
			index = 0;
			while (index < pop.vertices[largestIndex].neighbours.size()) {
				if (res[pop.vertices[largestIndex].neighbours.get(index)] == color) {
					index = 0;
					color++;
				} else
					index++;
			}
			res[largestIndex] = color;

		}

		return res;
	}

	public int compareTo(Object other) {
		// final int BEFORE = -1;
		// final int EQUAL = 0;
		// final int AFTER = 1;
		if (cost < ((Graph) other).cost)
			return -1;
		else if (cost > ((Graph) other).cost)
			return 1;
		else
			return 0;
	}

}
