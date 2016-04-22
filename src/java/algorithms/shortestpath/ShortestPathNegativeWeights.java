package algorithms.shortestpath;

import utilities.Kattio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Class for finding shortest distances by using bellman ford.
 */
public class ShortestPathNegativeWeights {

    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);
        ShortestPathNegativeWeights spnw = new ShortestPathNegativeWeights();
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
                pathInformation = spnw.bellmanFord(SimpleEdge.readEdges(io, numEdges), numVertices, rootId);
            }
            else{
                Arrays.fill(pathInformation.getDistance(), Integer.MAX_VALUE);
                pathInformation.getDistance()[rootId] = 0;
            }
            if (numQueries != 0) {
                for (int i = 0; i < numQueries; i++) {
                    int queryId = io.getInt();
                    if (pathInformation.getDistance()[queryId] == Integer.MAX_VALUE){
                        io.println("Impossible");
                    }
                    else if (pathInformation.getDistance()[queryId] == Integer.MIN_VALUE){
                        io.println("-Infinity");
                    }
                    else {
                        io.println(pathInformation.getDistance()[queryId]);
                    }
                }
                io.println();
            }
            io.flush();
        }
        io.close();
    }

    public PathInformation bellmanFord(ArrayList<SimpleEdge> edges, int numVertices, int rootId) {
        long[] d = new long[numVertices];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[rootId] = 0;

        int[] p = new int[numVertices];
        Arrays.fill(p, -1);

        for (int i = 0; i < numVertices; i++) {
            for (SimpleEdge e : edges) {
                if (d[e.from] != Integer.MAX_VALUE && d[e.from] + e.cost < d[e.to]) {
                    d[e.to] = d[e.from] + e.cost;
                    p[e.to] = e.from;
                }
            }
        }

        ArrayList<ArrayList<SimpleEdge>> N = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            N.add(new ArrayList<>());
        }
        for (SimpleEdge e : edges){
            N.get(e.from).add(e);
        }

        Stack<Integer> arbitraryPaths = new Stack<>();
        for (SimpleEdge e : edges){
            if (d[e.from] != Integer.MAX_VALUE && d[e.from] + e.cost < d[e.to]){
                arbitraryPaths.add(e.to);
            }
            while(!arbitraryPaths.isEmpty()){
                int id = arbitraryPaths.pop();
                if (d[id] != Integer.MIN_VALUE){
                    d[id] = Integer.MIN_VALUE;
                    for(SimpleEdge ne : N.get(id)){
                        arbitraryPaths.add(ne.to);
                    }
                }
            }
        }
        return new PathInformation(p, d);
    }

    public static class SimpleEdge {
        public final int from, to, cost;

        public SimpleEdge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        public static ArrayList<SimpleEdge> readEdges(Kattio io, int numEdges){
            ArrayList<SimpleEdge> edges = new ArrayList<>(numEdges);
            for (int i = 0; i < numEdges; i++) {
                edges.add(new SimpleEdge(io.getInt(), io.getInt(), io.getInt()));
            }
            return edges;
        }
    }
}
