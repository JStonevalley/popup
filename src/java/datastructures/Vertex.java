package datastructures;

import java.util.*;

public class Vertex {
    public final int id;

    private HashMap<Integer, Neighbour> neighbours = new HashMap<>();

    public Vertex(int id) {
        this.id = id;
    }

    public Vertex(int id, List<Neighbour> neighbours) {
        this.id = id;
        for (Neighbour n : neighbours){
            this.neighbours.put(n.id, n);
        }
    }

    public void addNeighbour(int neighbourId, int cost){
        neighbours.put(neighbourId, new Neighbour(neighbourId, cost));
    }

    public Neighbour getNeighbour(int i){
        return neighbours.get(i);
    }

    public int numNeighbours(){
        return neighbours.size();
    }

    public List<Neighbour> getNeighbours() {
        return new ArrayList<>(neighbours.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public class Neighbour{
        public final int id;
        public final int cost;

        public Neighbour(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }
    }
}
