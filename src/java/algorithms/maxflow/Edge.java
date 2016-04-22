package algorithms.maxflow;

public class Edge {
	public int value;
	public int neighbour;
	public int reverseEdge;
	public int capacity;
	public int flow;
	public int restCapacity;
	
	public Edge(int value, int neighbour, int reverseEdge, int capacity){
		this.value = value;
		this.neighbour = neighbour;
		this.reverseEdge = reverseEdge;
		this.capacity = capacity;
		this.restCapacity = capacity;
	}
}
