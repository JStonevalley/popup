package problems;

import utilities.Kattio;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class GetShorty {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        while(io.hasMoreTokens()) {
            int numVertices = io.getInt();
            int numEdges = io.getInt();

            if (numVertices < 2 || numEdges < 1){
                break;
            }
            GetShorty sp = new GetShorty();
            double[] magnitudes = sp.dijkstra(readInput(io, numVertices, numEdges), 0);
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            otherSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("0.0000", otherSymbols);
            io.println(df.format(magnitudes[magnitudes.length-1]));
            io.flush();
        }
        io.close();
    }

    /**
     * Implementation of Dijkstra's algorithm.
     * @param vertices Array of the vertices in the graph.
     * @param rootId Id of the vertex to search from.
     * @return Array of distances from the root node to all the other vertices in the graph.
     */
    public double[] dijkstra(DoubleVertex[] vertices, int rootId){
        boolean[] visited = new boolean[vertices.length];
        PriorityQueue<DoubleVertex> unvisited = new PriorityQueue<>();
        int[] previousInPath = new int[vertices.length];
        boolean[] inPQ = new boolean[vertices.length];

        Arrays.fill(visited, false);
        Arrays.fill(inPQ, false);
        Arrays.fill(previousInPath, -1);

        vertices[rootId].distance = 1.0;
        unvisited.add(vertices[rootId]);
        previousInPath[rootId] = -1;
        inPQ[rootId] = true;

        while (!unvisited.isEmpty()) {
            DoubleVertex current = unvisited.poll();
            visited[current.id] = true;
            for (int i = 0; i < current.numNeighbours(); i++) {
                if (!visited[current.getNeighbour(i).id]) {
                    if (vertices[current.getNeighbour(i).id].distance < current.distance * current.getNeighbour(i).cost) { // Shorter path found
                        vertices[current.getNeighbour(i).id].distance = current.distance * current.getNeighbour(i).cost;
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

        return getDistances(vertices);
    }

    private double[] getDistances(DoubleVertex[] vertices) {
        double[] distances = new double[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            distances[i] = vertices[i].distance;
        }
        return distances;
    }

    private static DoubleVertex[] readInput(Kattio io, int numVertices, int numEdges) {
        DoubleVertex[] vertices = new DoubleVertex[numVertices];
        // instantiate all vertex objects
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new DoubleVertex(i);
        }

        int a, b;
        double c;
        for (int i = 0; i < numEdges; i++) {
            a = io.getInt();
            b = io.getInt();
            c = io.getDouble();

            vertices[a].addNeighbour(b, c);
            vertices[b].addNeighbour(a, c);
        }

        return vertices;
    }
    public static class DoubleVertex implements Comparable<DoubleVertex> {

        public final int id;
        private List<Neighbour> neighbours = new ArrayList<>();
        public double distance = 0.0;

        public DoubleVertex(int id) {
            this.id = id;
        }

        public void addNeighbour(int neighbourId, double cost) {
            neighbours.add(new Neighbour(neighbourId, cost));
        }

        public Neighbour removeNeighbour(int i) {
            return neighbours.remove(i);
        }

        public Neighbour getNeighbour(int i) {
            return neighbours.get(i);
        }

        public int numNeighbours() {
            return neighbours.size();
        }

        public List<Neighbour> getNeighbours() {
            return neighbours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DoubleVertex vertex = (DoubleVertex) o;

            return id == vertex.id;

        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public int compareTo(DoubleVertex ov) {
            if (ov.distance > distance) {
                return 1;
            }
            if (ov.distance < distance) {
                return -1;
            }
            return 0;
        }

        /**
         * Class representing a neighbor of a Vertex.
         */
        public static class Neighbour {
            public final int id;
            public final double cost;

            public Neighbour(int id, double cost) {
                this.id = id;
                this.cost = cost;
            }
        }
    }
}
