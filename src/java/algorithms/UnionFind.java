package algorithms;

import utilities.Kattio;

import java.io.*;

public class UnionFind {

    private static boolean kattis = false;

    private int[] parent;
    private int[] rank;

    public UnionFind(Kattio io) throws IOException {
        int numElements = io.getInt();
        int numOperations = io.getInt();
        long time = System.currentTimeMillis();
        parent = new int[numElements];
        rank = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < numOperations; i++) {
            String operation = io.getWord();
            int elementId1 = io.getInt();
            int elementId2 = io.getInt();
            if (operation.equals("?")) {
                if (sameRoot(elementId1, elementId2)) {
                    log.write("yes\n");
                } else {
                    log.write("no\n");
                }
            }
            else if (operation.equals("=")) {
                unite(elementId1, elementId2);
            }
        }
        if (!kattis) {
            log.write((System.currentTimeMillis() - time) + "");
        }
        log.flush();
    }

    /**
     * Find the root of the node
     * @param n node for which to find the root node.
     */
    private int rootOf(int n) {
        while (n != parent[n]) {
            parent[n] = parent[parent[n]];
            n = parent[n];
        }
        return n;
    }


    /**
     * Check if two nodes belong to the same tree.
     * @param n1 node in tree1
     * @param n2 node in tree2
     */
    public boolean sameRoot(int n1, int n2){
        return rootOf(n1) == rootOf(n2);
    }


    /**
     * Join two trees together by joining the roots of the trees.
     * @param n1 node in tree1
     * @param n2 node in tree2
     */
    public void unite(int n1, int n2){
        int r1 = rootOf(n1);
        int r2 = rootOf(n2);
        if (rank[r1] < rank[r2]){
            parent[r1] = r2;
            rank[r2] += rank[r1];
        }
        parent[r1] = r2;
    }

    public static void main(String[] args) throws IOException {
        Kattio io = null;
        try {
            if (kattis) {
                io = new Kattio(System.in);
            } else {
                io = new Kattio(new BufferedInputStream(new FileInputStream("src\\resources\\UFmillion")));
            }
        }catch(FileNotFoundException e) {

        }

        UnionFind uf = new UnionFind(io);
    }
}
