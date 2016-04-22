package algorithms.knapsack;

public class KnapsackCase {
    double capacity;
    int[] values;
    int[] weights;

    public KnapsackCase(double capacity, int[] values, int[] weights) {
        this.capacity = capacity;
        this.values = values;
        this.weights = weights;
    }

    public double getCapacity() {
        return capacity;
    }

    public int[] getValues() {
        return values;
    }

    public int[] getWeights() {
        return weights;
    }
}
