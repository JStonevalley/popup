package algorithms.shortestpath;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a vertex in a graph. Each vertex holds a list of its neighbors and the distance to the root nod.
 */
public class DijkstraVertex implements Comparable<DijkstraVertex> {

    public final int id;
    private List<Neighbour> neighbours = new ArrayList<>();
    public long distance = Integer.MAX_VALUE;

    public DijkstraVertex(int id) {
        this.id = id;
    }

    public void addNeighbour(int neighbourId, int cost){
        neighbours.add(new Neighbour(neighbourId, cost));
    }

    public Neighbour removeNeighbour(int i){
        return neighbours.remove(i);
    }

    public Neighbour getNeighbour(int i){
        return neighbours.get(i);
    }

    public int numNeighbours(){
        return neighbours.size();
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DijkstraVertex vertex = (DijkstraVertex) o;

        return id == vertex.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(DijkstraVertex ov) {
        if (ov.distance < distance) {
            return 1;
        }
        if (ov.distance > distance) {
            return -1;
        }
        return 0;
    }

    /**
     * Class representing a neighbor of a Vertex.
     */
    public class Neighbour{
        public final int id;
        public final int cost;

        public Neighbour(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }
    }
}
