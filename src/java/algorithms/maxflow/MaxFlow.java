package algorithms.maxflow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for calculating the maximum flow for a graph.
 */
public class MaxFlow {
	int numVertices;
	int source;
	int sink;
	ArrayList<ArrayList<Edge>> neighbours;

	public MaxFlow(int numVertices, int source, int sink, ArrayList<ArrayList<Edge>> neighbours) {
		this.numVertices = numVertices;
		this.source = source;
		this.sink = sink;
		this.neighbours = neighbours;
	}

	/**
	 * Computes the maximum flow for the graph contained in this class.
	 */
	public void fordFulkerson() {
		ArrayList<Edge> edgePath = bfs();
		// As long as there is a path with positive capacity.
		while (edgePath != null) {
			int minRestCapacity = Integer.MAX_VALUE;
			// Find the bottleneck edge
			for (Edge edge : edgePath)
				minRestCapacity = Math.min(edge.restCapacity, minRestCapacity);
			// Send flow through the path and update the reverse edges
			for (Edge edge : edgePath) {
				ArrayList<Edge> ar = neighbours.get(edge.neighbour);
				Edge reverseEdge = ar.get(edge.reverseEdge);
				edge.flow = edge.flow + minRestCapacity;
				reverseEdge.flow = -edge.flow;
				edge.restCapacity = edge.capacity - edge.flow;
				reverseEdge.restCapacity = reverseEdge.capacity - reverseEdge.flow;
			}
			edgePath = bfs();
		}
	}

	/**
	 * @return Returns a path from the source to the sink if such a path exists. Null otherwise.
	 */
	private ArrayList<Edge> bfs() {
		Edge[] marker = new Edge[numVertices + 1];
		Queue<Edge> q = new LinkedList<>();
		Edge node = new Edge(-1, source, 0, 0);
		q.add(node);
		marker[node.neighbour] = node;
		while (!q.isEmpty()) {
			node = q.remove();
			if (node.neighbour == sink) {
				return getPath(node, marker);
			} else {
				for (Edge e : neighbours.get(node.neighbour)) {
					if (e.restCapacity > 0 && marker[e.neighbour] == null) {
						q.add(e);
						marker[e.neighbour] = e;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Backtrack to find the path.
	 * @param node start node for backtracking.
	 * @param marker marker which marked the path.
	 * @return Returns a path ending in node.
	 */
	private ArrayList<Edge> getPath(Edge node, Edge[] marker) {
		ArrayList<Edge> edgePath = new ArrayList<>();
		while (node.value != -1) {
			edgePath.add(node);
			node = marker[node.value];
		}
		return edgePath;
	}
}
