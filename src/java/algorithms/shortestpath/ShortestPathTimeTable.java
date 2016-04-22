package algorithms.shortestpath;

import utilities.Kattio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ShortestPathTimeTable {

    Kattio io = new Kattio(System.in);
    DijkstraVertex[] vertices;
    HashMap<SimpleEdge, CostInfo> edgeMap;

    public static void main(String[] args) {
        ShortestPathTimeTable sptt = new ShortestPathTimeTable();
        while (sptt.io.hasMoreTokens()) {
            sptt.edgeMap = new HashMap<>();
            int numVertices = sptt.io.getInt();
            int numEdges = sptt.io.getInt();
            int numQueries = sptt.io.getInt();
            int startVertex = sptt.io.getInt();
            if (numVertices == 0 && numEdges == 0 && numQueries == 0 && startVertex == 0) {
                return;
            }

            sptt.readVerticesAndEdges(numVertices, numEdges);
            PathInformation pathInformation = sptt.dijkstra(sptt.vertices, startVertex);

            for (int q = 0; q < numQueries; q++) {
                int query = sptt.io.getInt();
                long dist = pathInformation.getDistance()[query];
                if (dist == Integer.MAX_VALUE) {
                    System.out.println("Impossible");
                    continue;
                }
                System.out.println(dist);
            }
            System.out.println();
        }
    }

    /**
     * Implementation of dijkstra's algorithm that finds the shortest distance to all vertices in a graph.
     * @param vertices List of the vertices in the graph.
     * @param rootId Id of the vertex to search from.
     * @return Array of distances from the root vertex to all the other vertices in the graph.
     */
    public PathInformation dijkstra(DijkstraVertex[] vertices, int rootId){
        boolean[] visited = new boolean[vertices.length];
        PriorityQueue<DijkstraVertex> unvisited = new PriorityQueue<>();
        int[] previousInPath = new int[vertices.length];
        boolean[] inPQ = new boolean[vertices.length];

        Arrays.fill(visited, false);
        Arrays.fill(inPQ, false);

        vertices[rootId].distance = 0;
        unvisited.add(vertices[rootId]);
        inPQ[rootId] = true;

        while(!unvisited.isEmpty()){
            DijkstraVertex current = unvisited.poll();
            visited[current.id] = true;
            for (int i = 0; i < current.numNeighbours(); i++) {
                if (!visited[current.getNeighbour(i).id]) {
                    long cost = getCost(current, current.getNeighbour(i));
                    if (cost < vertices[current.getNeighbour(i).id].distance) {
                        vertices[current.getNeighbour(i).id].distance = cost;
                        previousInPath[current.getNeighbour(i).id] = current.id;
                    }
                    if (inPQ[current.getNeighbour(i).id]){
                        unvisited.remove(vertices[current.getNeighbour(i).id]);
                        unvisited.add(vertices[current.getNeighbour(i).id]);
                    }
                    else{
                        inPQ[current.getNeighbour(i).id] = true;
                        unvisited.add(vertices[current.getNeighbour(i).id]);
                    }
                }
            }
        }
        return new PathInformation(previousInPath, getDistances(vertices));
    }

    /**
     * Retrieves the cost for the edge between a vertex and one of its neighbors.
     * @param vertex A vertex.
     * @param neighbor One of the vertex's neighbors.
     * @return The cost.
     */
    private long getCost(DijkstraVertex vertex, DijkstraVertex.Neighbour neighbor) {
        long time = vertex.distance;
        long cost = vertex.distance + neighbor.cost;

        CostInfo costInfo = edgeMap.get(new SimpleEdge(vertex.id, neighbor.id, neighbor.cost));

        if (costInfo.interval == 0) {
            if (time > costInfo.startTime) {
                return Integer.MAX_VALUE;
            } else {
                return cost + costInfo.startTime - time;
            }
        }
        if (time >= costInfo.startTime) {
            long timeDiff = (time - costInfo.startTime) % costInfo.interval;
            if (timeDiff > 0) {
                cost += costInfo.interval - timeDiff;
            }
        } else {
            cost += costInfo.startTime - time;
        }


        return cost;
    }

    /**
     * Reads the input and populates the class' vertices array and edge map.
     */
    private void readVerticesAndEdges(int numVertices, int numEdges) {
        vertices = new DijkstraVertex[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new DijkstraVertex(i);
        }
        for (int i = 0; i < numEdges; i++) {
            int start = io.getInt();
            int end = io.getInt();
            int t0 = io.getInt();
            int interval = io.getInt();
            int cost = io.getInt();
            vertices[start].addNeighbour(end, cost);
            edgeMap.put(new SimpleEdge(start, end, cost), new CostInfo(t0, interval));
        }
    }

    /**
     * Creates and array with distances from an array with VertexWithDistance.
     */
    private long[] getDistances(DijkstraVertex[] vertices) {
        long[] distances = new long[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            distances[i] = vertices[i].distance;
        }
        return distances;
    }

    /**
     * Class representing an edge in a graph.
     */
    private class SimpleEdge {
        int start;
        int end;
        int cost;
        public SimpleEdge(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SimpleEdge that = (SimpleEdge) o;

            if (cost != that.cost) return false;
            if (end != that.end) return false;
            if (start != that.start) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = start;
            result = 31 * result + end;
            result = 31 * result + cost;
            return result;
        }
    }

    /**
     * Class holding information about the cost of traversing an edge.
     */
    private class CostInfo {
        int startTime;
        int interval;

        public CostInfo(int startTime, int interval) {
            this.startTime = startTime;
            this.interval = interval;
        }
    }
}
