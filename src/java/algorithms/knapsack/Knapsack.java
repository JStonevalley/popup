package algorithms.knapsack;

import utilities.Kattio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Knapsack {

    /**
     * Main class. Reading the input and looping through the cases.
     */
    public static void main(String[] args) {
        Knapsack k = new Knapsack();
        ArrayList<KnapsackCase> cases = k.readInput();
        for (KnapsackCase kCase : cases) {
            int[] result = k.knapsack(kCase.getCapacity(), kCase.getValues(), kCase.getWeights());
            System.out.println(result.length);
            for (int i : result) {
                System.out.print(i + " ");
            }
        }
    }

    /**
     * Finds the items which maximizes the value in the knapsack.
     *
     * @param capacity The capacity of the Knapsack.
     * @param values Array with the values of the items.
     * @param weights Array with the weights of the items.
     * @return A int array containing the indices of the chosen items.
     */
    public int[] knapsack(double capacity, int[] values, int[]weights) {
        assert values.length == weights.length;

        int numItems = values.length;
        int intCapacity = (int)Math.floor(capacity);
        int[][] maxValue = new int[numItems+1][intCapacity+1]; //maxValue[i][j] contains the maximum value of the i first items with a max weights of j.
        boolean[][] take = new boolean[numItems][intCapacity+1]; //take[i][j] contains true of we have chosen i'th item with a max weight of j.

        // Base case
        for (int j = 0; j < intCapacity; j++) {
            maxValue[0][j] = 0;
        }

        for (int i = 1; i <= numItems; i++) {
            for (int j = 0; j <= intCapacity; j++) {
                if (weights[i-1] <= j && maxValue[i-1][j-weights[i-1]] + values[i-1] > maxValue[i-1][j]) {
                    maxValue[i][j] = maxValue[i-1][j-weights[i-1]] + values[i-1];
                    take[i-1][j] = true;
                }
                else {
                    maxValue[i][j] = maxValue[i-1][j];
                    take[i-1][j] = false;
                }
            }
        }
        System.err.println("Max value: " + maxValue[numItems][intCapacity]);

        // Checking which indices that were chosen and saving these.
        ArrayList<Integer> resultIndices = new ArrayList<>();
        int cap = intCapacity;
        for (int i = numItems-1; i >= 0; i--) {
            if (take[i][cap]) {
                resultIndices.add(i);
                cap = cap - weights[i];
            }
        }
        return resultIndices.stream().mapToInt(i->i).toArray();
    }

    /**
     * Reads the input to a list of KnapsackCase.
     * @return An ArrayList containing the indices of the chosen items.
     */
    private ArrayList<KnapsackCase> readInput() {
        Kattio io = null;
        try {
            boolean kattis = false;
            if (kattis) {
                io = new Kattio(System.in);
            } else {
                io = new Kattio(new BufferedInputStream(new FileInputStream("src\\resources\\knapsack.in")));
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<KnapsackCase> cases = new ArrayList<>();

        while(io.hasMoreTokens()) {
            double capacity = io.getDouble();
            int items = io.getInt();
            int[] values = new int[items];
            int[] weights = new int[items];
            for (int i  = 0; i < items; i++) {
                values[i] = io.getInt();
                weights[i] = io.getInt();
            }
            cases.add(new KnapsackCase(capacity, values, weights));
        }
        return cases;
    }
}
