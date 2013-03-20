package com.lds.ga;
import java.util.LinkedList;

public class Vertex implements Cloneable{
	LinkedList<Integer> neighbours;
	int color;

	public Vertex(LinkedList<Integer> neighbours, int color) {
		this.neighbours = neighbours;
		this.color = color;
	}

	public void addNeighbour(int neighbour) {
		neighbours.add(neighbour);
	}

	public String toString()
	{
		String n="";
		for(int i = 0; i< neighbours.size();i++)
		{
			n += neighbours.get(i)+ " ";
		}
		return new String("Neighbours: " + n + "\nColor: " + color);
	}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Vertex((LinkedList<Integer>)neighbours.clone(), color);
    }
        
        
}
