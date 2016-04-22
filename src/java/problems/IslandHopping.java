package problems;

import datastructures.Coordinate;
import utilities.Kattio;

public class IslandHopping {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int cases = io.getInt();
        for (int i = 0; i < cases; i++) {
            int numIslands = io.getInt();
            Coordinate<Double>[] islands = new Coordinate[numIslands];
            for (int j = 0; j < numIslands; j++) {
                islands[j] = new Coordinate<Double>(io.getDouble(), io.getDouble());
            }
            MinSpanningTree.Vertex[] vertices = new MinSpanningTree.Vertex[numIslands];
            for (int j = 0; j < numIslands; j++) {
                vertices[j] = new MinSpanningTree.Vertex(j, j);
            }

            MinSpanningTree.Edge[] edges = new MinSpanningTree.Edge[(numIslands * (numIslands-1))/2];
            int index = 0;
            for (int j = 0; j < numIslands; j++) {
                for (int k = j+1; k < numIslands; k++) {
                    double cost = Math.sqrt(Math.pow((islands[j].x - islands[k].x), 2) + Math.pow((islands[j].y - islands[k].y), 2));
                    edges[index++] = new MinSpanningTree.Edge(vertices[j], vertices[k], cost);
                }
            }
            double sum = 0;
            for (MinSpanningTree.Edge e : new MinSpanningTree().minimumSpanningTree(edges, vertices)){
                sum += e.cost;
            }
            System.out.println(sum);
        }
    }
}
