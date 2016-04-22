package algorithms;

import utilities.Kattio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EulerianPath {
    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);
        EulerianPath ep = new EulerianPath();
        int numVertices = io.getInt();
        int numEdges = io.getInt();

        while (numVertices > 1 && numEdges > 0){
            Vertex[] vertices = new Vertex[numVertices];
            for (int i = 0; i < numVertices; i++) {
                vertices[i] = new Vertex(i);
            }
            for (int i = 0; i < numEdges; i++) {
                int from = io.getInt();
                int to = io.getInt();
                vertices[from].addChild(to);
                vertices[to].incrementNumParents();
            }
            int[] path = ep.getEulerianPath(vertices);
            // Null betyder att det inte finns en Eulerstig
            if (path != null) {
                for (int p : path) {
                    io.print(p + " ");
                }
                io.println();
            }
            else {
                io.println("Impossible");
            }
            io.flush();
            numVertices = io.getInt();
            numEdges = io.getInt();
        }
        io.close();
    }

    /**
     * Computes and returns an eulerian path in the graph consisting of the vertices
     * @param vertices Array of vertices.
     * @return An eulerian path represented as the ids of vertices in the path.
     */
    public int[] getEulerianPath(Vertex[] vertices) {
        int start = -1;
        int numEdges = 0;
        boolean oddStart = false;
        boolean oddEnd = false;

        // Val av startnod, det maste finnas endast ett horn med udda antal inkommande kanter
        // och endast ett horn med udda antal utgaende kanter eller samma antal inkommande och
        // utgaende kanter pa samtliga horn.
        for (Vertex v : vertices){
            numEdges += v.getNumChildren();
            if (!oddStart && v.getNumChildren() != 0 && v.getNumParents() == v.getNumChildren()){
                start = v.id;
            }
            else if (!oddStart && v.getNumParents() + 1 == v.getNumChildren()){
                start = v.id;
                oddStart = true;
            }
            else if (!oddEnd && v.getNumParents() == v.getNumChildren() + 1){
                oddEnd = true;
            }
            else if (v.getNumParents() != v.getNumChildren()){
                return null;
            }
        }

        int[] path = new int[numEdges + 1];
        int index = path.length - 1;
        Stack<Integer> stack = new Stack<>();
        stack.add(start);

        // Build path
        while(!stack.isEmpty()){
            Vertex current = vertices[stack.pop()];
            if (current.getNumChildren() == 0){
                path[index--] = current.id;
            }
            else {
                stack.add(current.id);
                stack.add(current.removeFirstChild());
            }
        }
        // Det finns tva eller fler komponenter med rank > 1
        if (index != -1){
            return null;
        }
        return path;
    }

    /**
     * Class representing a vertex in a graph.
     */
    public static class Vertex{
        public final int id;
        private int numParents = 0;
        public LinkedList<Integer> children = new LinkedList<>();

        public Vertex(int id) {
            this.id = id;
        }

        public int removeFirstChild(){
            return children.removeFirst();
        }

        public void incrementNumParents(){
            numParents++;
        }

        public void addChild(int i){
            children.add(i);
        }

        public int getFirstChild(){
            return children.getFirst();
        }

        public int getNumChildren(){
            return children.size();
        }

        public int getNumParents(){
            return numParents;
        }
    }

    public void generateInputData(int numExamples) throws IOException {
        FileWriter fw = new FileWriter(new File("C:\\Users\\Jonas\\Development\\popuplab2\\res\\ep.in"));
        for (int j = 0; j < numExamples; j++) {
            Random r = new Random();
            int vertices = 0;
            if (j < 10) {
                vertices = 2;
            }
            else{
                vertices = r.nextInt(1000) + 2;
            }
            int numEdges = r.nextInt(5000) + 1;
            ArrayList<String> edges = new ArrayList<>();
            int current = r.nextInt(vertices);
            int next;
            for (int i = 0; i < numEdges; i++) {
                next = r.nextInt(vertices);
                edges.add(current + " " + next + "\n");
                current = next;
            }
            Collections.shuffle(edges);
            fw.write(vertices + " " + (numEdges) + "\n");
            for (String e : edges) {
                fw.write(e);
            }
        }
        fw.write("0 0\n");
//        for (int i = 0; i < path.length; i++) {
//            fw.write(path[i] + " ");
//        }
        fw.close();
    }
}
