package algorithms.shortestpath;

import utilities.Kattio;

import java.nio.file.Path;
import java.util.*;

public class ShortestPath {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        while(io.hasMoreTokens()) {
            int numVertices = io.getInt();
            int numEdges = io.getInt();
            int numQueries = io.getInt();
            int rootId = io.getInt();
            PathInformation pathInformation = new PathInformation(new int[numVertices], new long[numVertices]);
            if (numVertices == 0 && numEdges == 0 && numQueries == 0 && rootId == 0){
                break;
            }
            if (numEdges != 0) {
                ShortestPath sp = new ShortestPath();
                pathInformation = sp.dijkstra(readInput(io, numVertices, numEdges), rootId);
            }
            else{
                Arrays.fill(pathInformation.getDistance(), Integer.MAX_VALUE);
                pathInformation.getDistance()[rootId] = 0;
            }
            if (numQueries != 0) {
                for (int i = 0; i < numQueries; i++) {
                    int queryId = io.getInt();
                    if (pathInformation.getDistance()[queryId] == Integer.MAX_VALUE){
                        System.out.println("Impossible");
                    }
                    else {
                        System.out.println(pathInformation.getDistance()[queryId]);
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Implementation of Dijkstra's algorithm.
     * @param vertices Array of the vertices in the graph.
     * @param rootId Id of the vertex to search from.
     * @return Array of distances from the root node to all the other vertices in the graph.
     */
    public PathInformation dijkstra(DijkstraVertex[] vertices, int rootId){
        boolean[] visited = new boolean[vertices.length];
        PriorityQueue<DijkstraVertex> unvisited = new PriorityQueue<>();
        int[] previousInPath = new int[vertices.length];
        boolean[] inPQ = new boolean[vertices.length];

        Arrays.fill(visited, false);
        Arrays.fill(inPQ, false);
        Arrays.fill(previousInPath, -1);

        vertices[rootId].distance = 0;
        unvisited.add(vertices[rootId]);
        previousInPath[rootId] = -1;
        inPQ[rootId] = true;

        while (!unvisited.isEmpty()) {
            DijkstraVertex current = unvisited.poll();
            visited[current.id] = true;
            for (int i = 0; i < current.numNeighbours(); i++) {
                if (!visited[current.getNeighbour(i).id]) {
                    if (vertices[current.getNeighbour(i).id].distance > current.distance + current.getNeighbour(i).cost) { // Shorter path found
                        vertices[current.getNeighbour(i).id].distance = current.distance + current.getNeighbour(i).cost;
                        previousInPath[current.getNeighbour(i).id] = current.id;
                    }
                    if (inPQ[current.getNeighbour(i).id]) {
                        unvisited.remove(vertices[current.getNeighbour(i).id]);
                        unvisited.add(vertices[current.getNeighbour(i).id]);
                    } else {
                        inPQ[current.getNeighbour(i).id] = true;
                        unvisited.add(vertices[current.getNeighbour(i).id]);
                    }
                }
            }
        }

        return new PathInformation(previousInPath, getDistances(vertices));
    }

    private long[] getDistances(DijkstraVertex[] vertices) {
        long[] distances = new long[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            distances[i] = vertices[i].distance;
        }
        return distances;
    }

    private static DijkstraVertex[] readInput(Kattio io, int numVertices, int numEdges) {
        DijkstraVertex[] vertices = new DijkstraVertex[numVertices];
        // instantiate all vertex objects
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new DijkstraVertex(i);
        }

        int a, b, c;
        for (int i = 0; i < numEdges; i++) {
            a = io.getInt();
            b = io.getInt();
            c = io.getInt();

            vertices[a].addNeighbour(b, c);
        }

        return vertices;
    }
}
