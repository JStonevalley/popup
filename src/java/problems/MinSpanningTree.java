package problems;

import utilities.Kattio;

import java.util.*;

/**
 * Created by Erik Ranby & Jonas Stendahl
 */
public class MinSpanningTree {

    public static void main(String[] args) {
        MinSpanningTree mst = new MinSpanningTree();
        Kattio io = new Kattio(System.in);
        int numVertices = io.getInt();
        int numEdges = io.getInt();
        while (!(numEdges == 0 && numVertices == 0)) {
            Vertex[] vertices = mst.createVertices(numVertices);
            Edge[] edges = mst.createEdges(numEdges, vertices, io);
            ArrayList<Edge> solution = mst.minimumSpanningTree(edges, vertices);
            mst.printOutput(solution, numVertices, numEdges);

            numVertices = io.getInt();
            numEdges = io.getInt();
        }
    }

    public ArrayList<Edge> minimumSpanningTree(Edge[] edges, Vertex[] vertices) {
        ArrayList<Edge> minTree = new ArrayList<>();
        Edge[] sortedEdges = new Edge[edges.length];
        System.arraycopy(edges, 0, sortedEdges, 0, edges.length);
        Arrays.sort(sortedEdges);

        for (int i = 0; i < edges.length; i++) {
            Edge edge = sortedEdges[i];
            Vertex a = edge.a;
            Vertex b = edge.b;
            Vertex aRoot = findRoot(vertices, a);
            Vertex bRoot = findRoot(vertices, b);
            if (!aRoot.equals(bRoot)) {
                minTree.add(edge);
                union(vertices, a, b);
            }
        }
        return minTree;
    }

    private Vertex findRoot(Vertex[] vertices, Vertex vertex) {
        if (vertex.parent == vertex.id) {
            return vertex;
        }
        vertex.parent = findRoot(vertices, vertices[vertex.parent]).id;
        return vertices[vertex.parent];
    }

    private void union(Vertex[] vertices, Vertex v1, Vertex v2) {
        Vertex root1 = findRoot(vertices, v1);
        Vertex root2 = findRoot(vertices, v2);
        if (root1.equals(root2)) {
            return;
        }

        if (root1.rank < root2.rank) {
            root1.parent = root2.id;
        } else if (root1.rank > root2.rank) {
            root2.parent = root1.id;
        } else {
            root2.parent = root1.id;
            root1.rank++;
        }
    }

    private void printOutput(ArrayList<Edge> solution, int numVertices, int numEdges) {
        if (solution.size() > numVertices-1) {
            throw new IllegalArgumentException("Bad solution :/");
        }
        if (solution.size() == 0 || solution.size() < numVertices-1) {
            System.out.println("Impossible");
            return;
        }

        int totcost = 0;
        for (Edge edge : solution) {
            totcost += edge.cost;
        }
        System.out.println(totcost);

        Collections.sort(solution, new LexiComparator());
        for (Edge edge : solution) {
            System.out.print(edge.a.id + " ");
            System.out.println(edge.b.id);
        }
    }

    private Edge[] createEdges(int numEdges, Vertex[] vertices, Kattio io) {
        Edge[] edges = new Edge[numEdges];
        for (int i = 0; i < numEdges; i++) {
            Vertex a = vertices[io.getInt()];
            Vertex b = vertices[io.getInt()];
            int cost = io.getInt();
            edges[i] = new Edge(a, b, cost);
        }
        return edges;
    }

    private Vertex[] createVertices(int numVertices) {
        Vertex[] vertices = new Vertex[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new Vertex(i, i);
        }
        return vertices;
    }

    public static class Edge implements Comparable<Edge> {
        Vertex a;
        Vertex b;
        double cost;
        public Edge(Vertex a, Vertex b, double cost) {
            if (a.id <= b.id) {
                this.a = a;
                this.b = b;
            } else {
                this.b = a;
                this.a = b;
            }
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.cost < o.cost)
                return -1;
            else if (this.cost > o.cost)
                return 1;
            else
                return 0;
        }

        @Override
        public boolean equals(Object other) {
            Edge otherEdge = (Edge) other;
            if (otherEdge.a.equals(this.a) && otherEdge.b.equals(this.b))
                return true;
            return false;
        }
    }

    public static class Vertex {
        int id;
        int parent;
        int rank;

        public Vertex(int id, int parent) {
            this.id = id;
            this.parent = parent;
            this.rank = 1;
        }

        @Override
        public boolean equals(Object other) {
            Vertex otherV = (Vertex)other;
            if (otherV.id == this.id)
                return true;
            return false;
        }
    }

    private class LexiComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            if (o1.a.id < o2.a.id) {
                return -1;
            }
            else if (o1.a.id > o2.a.id) {
                return 1;
            }
            else {
                if (o1.b.id < o2.b.id) {
                    return -1;
                }
                else if (o1.b.id > o2.b.id) {
                    return 1;
                }
            }
            return 0;
        }
    }
}
