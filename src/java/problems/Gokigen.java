package problems;

import utilities.Kattio;

import java.util.HashSet;

public class Gokigen {

    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int size = io.getInt() + 1;
        Corner[] corners = new Corner[size * size];
        String line = "";
        for (int i = 0; i < size * size; i++) {
            if (i % size == 0)
                line = io.getWord();
            int reqLinks = -1;
            if (line.charAt(i%size) != '.')
                reqLinks = Integer.parseInt(line.charAt(i%size) + "");
            int opportunities = 4;
            if (i < size || i >= size * size - size || i % size == 0 || i % size == size - 1)
                opportunities = 2;
            if (i == 0 || i == size-1 || i == size * size - size || i == size * size - 1)
                opportunities = 1;
            corners[i] = new Corner(i, reqLinks, opportunities);
        }
        Cell[] cells = new Cell[(size - 1) * (size - 1)];
        for (int i = 0; i < (size - 1) * (size - 1); i++) {
            int startIndex = i / (size - 1);
            cells[i] = new Cell(corners[i + startIndex], corners[i + startIndex + 1], corners[i + startIndex + size], corners[i + startIndex + 1 + size]);
        }
        Gokigen gokigen = new Gokigen();
        cells = gokigen.solve(cells, 0);
        for (int i = 0; i < (size-1) * (size-1); i++) {
            if (cells[i].isFront()){
                io.print("/");
            }
            else if (cells[i].isBack()){
                io.print("\\");
            }
            if ((i+1) % (size-1) == 0){
                io.println();
            }
        }
        io.close();
    }

    public Cell[] solve(Cell[] cells, int index){
        if (index >= cells.length){
            return cells;
        }
        if (cells[index].tryAddFront()){
            if (solve(cells, index + 1) != null)
                return cells;
            cells[index].removeFront();
        }
        if (cells[index].tryAddBack()){
            if (solve(cells, index + 1) != null)
                return cells;
            cells[index].removeBack();
        }
        return null;
    }

    public static class Cell{
        private boolean front, back;
        private Corner[] corners;
        public Cell(Corner a, Corner b, Corner c, Corner d){
            corners = new Corner[] {a, b, c, d};
        }

        public boolean isFront() {
            return front;
        }

        public boolean isBack() {
            return back;
        }

        public boolean tryAddBack(){
            if (tryAddLink(0, 3)){
                back = true;
                return true;
            }
            return false;
        }

        public boolean tryAddFront(){
            if (tryAddLink(1, 2)){
                front = true;
                return true;
            }
            return false;
        }

        public void removeBack(){
            back = false;
            removeLink(0, 3);
        }

        public void removeFront(){
            front = false;
            removeLink(1, 2);
        }

        private void removeLink(int a, int b){
            corners[a].removeLink(corners[b]);
            corners[b].removeLink(corners[a]);
            for (Corner c : corners){
                c.addOpportunity();
            }
        }

        private boolean tryAddLink(int a, int b){
            if (corners[a].sameComponent(corners[b])){
                return false;
            }
            if (!corners[a].addLink(corners[b])){
                return false;
            }
            if (!corners[b].addLink(corners[a])){
                corners[a].removeLink(corners[b]);
                return false;
            }
            for (Corner c : corners){
                if (!c.canRemoveOpportunity()){
                    corners[a].removeLink(corners[b]);
                    corners[b].removeLink(corners[a]);
                    return false;
                }
            }
            for (Corner c : corners){
                c.removeOpportunity();
            }
            return true;
        }
    }

    public static class Corner{
        private final int id;
        private final int reqLinks;
        private int opportunities;
        private HashSet<Corner> neighbours;

        public Corner(int id, int reqLinks, int opportunities) {
            this.id = id;
            this.reqLinks = reqLinks;
            this.opportunities = opportunities;
            neighbours = new HashSet<>();
        }

        public boolean addLink(Corner c){
            if (neighbours.size() == reqLinks){
                return false;
            }
            neighbours.add(c);
            return true;
        }
        public void removeLink(Corner c){
            neighbours.remove(c);
        }

        public boolean canRemoveOpportunity(){
            if (reqLinks == -1)
                return true;
            return (reqLinks - neighbours.size() <= opportunities - 1) && (opportunities >= 0);
        }

        public void removeOpportunity(){
            opportunities--;
        }

        public void addOpportunity(){
            opportunities++;
        }

        public boolean sameComponent(Corner key){
            for (Corner n : neighbours){
                if (n == key || n.sameComponent(key, this)){
                    return true;
                }
            }
            return false;
        }

        private boolean sameComponent(Corner key, Corner from){
            for (Corner n : neighbours){
                if (n != from && (n == key || n.sameComponent(key, this))){
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Corner corner = (Corner) o;

            return id == corner.id;

        }

        @Override
        public int hashCode() {
            return id;
        }
    }

}
