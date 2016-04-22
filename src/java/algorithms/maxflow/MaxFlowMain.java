package algorithms.maxflow;

import utilities.Kattio;

import java.util.ArrayList;

public class MaxFlowMain {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int numVertices = io.getInt();
		int numEdges = io.getInt();
		int source = io.getInt();
		int sink = io.getInt();

		MaxFlowMain kattisReadWrite = new MaxFlowMain();

		ArrayList<ArrayList<Edge>> graph  = kattisReadWrite.readGraph(io, numVertices, numEdges);

		MaxFlow maxFlow = new MaxFlow(numVertices, source, sink, graph);
		maxFlow.fordFulkerson();
		kattisReadWrite.writeFlowGraph(io, graph, source, numVertices);
	}

	public ArrayList<ArrayList<Edge>> readGraph(Kattio io, int numVertices, int numEdges) {
		ArrayList<ArrayList<Edge>> neighbours = new ArrayList<>();
		for (int i = 0; i <= numVertices; i++){
			neighbours.add(new ArrayList<>());
		}

		for (int i = 0; i < numEdges; i++){
			int from = io.getInt();
			int to = io.getInt();
			int cap = io.getInt();

			boolean found = false;
			for (Edge edge : neighbours.get(from)) {
				if (to == edge.neighbour) {
					edge.capacity = cap;
					edge.restCapacity = cap;
					found = true;
					break;
				}
			}
			if (!found) {
				neighbours.get(from).add(new Edge(from, to, neighbours.get(to).size(), cap));
				neighbours.get(to).add(new Edge(to, from, neighbours.get(from).size()-1, 0));
			}
		}
		return neighbours;
	}

	public void writeFlowGraph(Kattio io, ArrayList<ArrayList<Edge>> graph, int source, int numVertices){
		int maxFlow = 0;
		for (Edge edge : graph.get(source)) {
			maxFlow += edge.flow;
		}
		ArrayList<Edge> flowingEdges = new ArrayList<Edge>();
		for (ArrayList<Edge> ar : graph) {
			for (Edge edge : ar) {
				if (edge.flow > 0)
					flowingEdges.add(edge);
			}
		}
		io.println(numVertices + " " + maxFlow + " " + flowingEdges.size());
		for (Edge edge : flowingEdges) {
			io.println(edge.value + " " + edge.neighbour + " " + edge.flow);
		}
		io.flush();
	}
}