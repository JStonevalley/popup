package problems;

import datastructures.Vertex;
import utilities.Kattio;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class George {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int numIntersections = io.getInt();
        int numStreets = io.getInt();
        int lukaStart = io.getInt()-1;
        int lucaGoal = io.getInt()-1;
        int timeDiff = io.getInt();
        int[] georgePath = new int[io.getInt()];
        for (int i = 0; i < georgePath.length; i++) {
            georgePath[i] = io.getInt()-1;
        }
        Vertex[] vertices = new Vertex[numIntersections];
        for (int i = 0; i < numIntersections; i++) {
            vertices[i] = new Vertex(i);
        }
        int a, b, l;
        for (int i = 0; i < numStreets; i++) {
            a = io.getInt()-1; b = io.getInt()-1; l = io.getInt();
            vertices[a].addNeighbour(b,l);
            vertices[b].addNeighbour(a,l);
        }
        George g = new George();
        Time[][] georgeTime = g.getTimeGeorgeOnRoad(vertices, georgePath, timeDiff);
        int[] distances = g.minimizeTime(vertices, lukaStart, georgeTime);
        System.out.println(distances[lucaGoal]);
    }

    public int[] minimizeTime(Vertex[] vertices, int startIntersection, Time[][] georgeTime){
        boolean[] visited = new boolean[vertices.length];
        VertexWithDistance[] verticesWD = getVerticesWithDistance(vertices);
        Arrays.fill(visited, false);
        PriorityQueue<VertexWithDistance> unvisited = new PriorityQueue<>();
        boolean[] inPQ = new boolean[verticesWD.length];
        Arrays.fill(inPQ, false);
        verticesWD[startIntersection].distance = 0;
        unvisited.add(verticesWD[startIntersection]);
        inPQ[startIntersection] = true;
        while(!unvisited.isEmpty()){
            VertexWithDistance current = unvisited.poll();
            visited[current.id] = true;
            List<Vertex.Neighbour> neighbours = current.getNeighbours();
            for (int i = 0; i < current.numNeighbours(); i++) {
                Vertex.Neighbour neighbour = neighbours.get(i);
                VertexWithDistance neighbourV =  verticesWD[neighbour.id];
                if (!visited[neighbourV.id]) {
                    int penalty = 0;
                    if (georgeTime[current.id][neighbourV.id] != null){
                        if(georgeTime[current.id][neighbourV.id].start < current.distance && georgeTime[current.id][neighbourV.id].end > current.distance){
                            penalty = georgeTime[current.id][neighbourV.id].end - current.distance;
                        }
                    }
                    neighbourV.distance = Math.min(neighbourV.distance, current.distance + neighbour.cost + penalty);
                    if (inPQ[neighbourV.id]){
                        unvisited.remove(neighbourV);
                        unvisited.add(neighbourV);
                    }
                    else{
                        inPQ[neighbourV.id] = true;
                        unvisited.add(neighbourV);
                    }
                }
            }
        }
        return getDistances(verticesWD);
    }

    private Time[][] getTimeGeorgeOnRoad(Vertex[] vertices, int[] path, int timeDiff){
        Time[][] timeMatrix = new Time[vertices.length][];
        for (int i = 0; i < timeMatrix.length; i++) {
            timeMatrix[i] = new Time[vertices.length];
        }
        int end = -timeDiff;
        int start = -timeDiff;
        for (int i = 1; i < path.length; i++) {
            end += vertices[path[i]].getNeighbour(path[i-1]).cost;
            timeMatrix[path[i]][path[i-1]] = new Time(start, end);
            timeMatrix[path[i-1]][path[i]] = new Time(start, end);
            start = end;
        }
        return timeMatrix;
    }

    private int[] getDistances(VertexWithDistance[] verticesWD) {
        int[] distances = new int[verticesWD.length];
        for (int i = 0; i < verticesWD.length; i++) {
            distances[i] = verticesWD[i].distance;
        }
        return distances;
    }

    public VertexWithDistance[] getVerticesWithDistance(Vertex[] vertices){
        VertexWithDistance[] verticesWD = new VertexWithDistance[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            verticesWD[i] = new VertexWithDistance(vertices[i]);
        }
        return verticesWD;
    }

    public class VertexWithDistance extends Vertex implements Comparable<VertexWithDistance>{

        public int distance = Integer.MAX_VALUE;

        public VertexWithDistance(int id) {
            super(id);
        }

        public VertexWithDistance(Vertex v){
            super(v.id, v.getNeighbours());
        }

        @Override
        public int compareTo(VertexWithDistance ov) {
            if (ov.distance < distance){
                return 1;
            }
            if (ov.distance > distance){
                return -1;
            }
            return 0;
        }
    }

    private class Time{
        int start;
        int end;

        public Time(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
