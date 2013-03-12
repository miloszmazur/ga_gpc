import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Graph {
	private Vertex v;
	public Vertex[] vertices;
	private Scanner in;
	private Random r;

	public Graph(Vertex[] vertices) {
		this.vertices = vertices;

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
		vertices = new Vertex[a+1];

		while (in.hasNext()) {
			in.next();
			int i = in.nextInt();
			if (vertices[i] == null) {
				v = new Vertex(new LinkedList<Integer>(), 0);
				v.addNeighbour(in.nextInt());
				vertices[i] = v;
			} else {
				vertices[i].addNeighbour(in.nextInt());
			}
		}

		return new Graph(vertices);
	}

	public String toString() {

		return new String("Vertices: " + getVertices() + "\nEdges: " + getEdges());
	}

	public int getEdges() {
		int edges = 0;
		for (int i = 1; i < vertices.length; i++) {
			edges += vertices[i].neighbours.size();
		}
		return edges;
	}

	public int getVertices() {
		return vertices.length -1;
	}
	
	public void colorize(int tops)
	{
		for (int i = 1; i < vertices.length; i++) 
		{
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
		System.out.println(conflicts);
		return conflicts;
	}

}
