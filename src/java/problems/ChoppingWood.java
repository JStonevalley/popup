package problems;

import utilities.Kattio;

import java.io.IOException;
import java.util.*;

public class ChoppingWood {

    public static void main(String [] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);
        int numCuts = io.getInt();
        ArrayList<Integer> v = new ArrayList<>(numCuts);
        for (int i = 0; i < numCuts; i++) {
            v.add(io.getInt());
        }
        new ChoppingWood().determineTree(v, io);
    }

    public void determineTree(ArrayList<Integer> v, Kattio io) throws IOException {
        int[] nodes = new int[v.size()+2];
        Arrays.fill(nodes, 0);
        for(int vertice : v){
            nodes[vertice]++;
        }
        int firstV = v.get(v.size()-1);
        int firstFq = nodes[v.get(v.size()-1)];

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i < nodes.length; i++) {
            if(nodes[i] == 0){
                pq.add(i);
            }
        }
        StringBuilder answer = new StringBuilder();
        for(int vertice : v){
            if (pq.isEmpty() || (nodes[v.get(v.size()-1)] == 1 && pq.peek() > firstV)){
                io.println("Error");
                io.close();
                return;
            }
            answer.append(pq.poll()).append("\n");
            if (--nodes[vertice] == 0){
                pq.add(vertice);
            }
        }
        io.print(answer.toString());
        io.close();
    }
}
