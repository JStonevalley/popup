package algorithms.shortestpath;

public class PathInformation {
    private int[] parent;
    private long[] distance;

    public PathInformation(int[] parent, long[] distance) {
        this.parent = parent;
        this.distance = distance;
    }

    public int[] getParent() {
        return parent;
    }

    public long[] getDistance() {
        return distance;
    }
}
