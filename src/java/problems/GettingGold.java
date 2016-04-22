package problems;

import datastructures.Coordinate;
import utilities.Kattio;

import java.util.LinkedList;
import java.util.Queue;

public class GettingGold {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);
        int cols = io.getInt();
        int rows = io.getInt();
        Coordinate<Integer> start = new Coordinate(0, 0);
        char[][] map = new char[cols][];
        for (int i = 0; i < cols; i++) {
            map[i] = new char[rows];
        }
        for (int i = 0; i < rows; i++) {
            String rowString = io.getWord();
            for (int j = 0; j < cols; j++) {
                if (rowString.charAt(j) == 'P'){
                    start.x = j;
                    start.y = i;
                    map[j][i] = '.';
                }
                else{
                    map[j][i] = rowString.charAt(j);
                }
            }
        }
        GettingGold gg = new GettingGold();
        System.out.println(gg.gettingGold(start, map));
    }

    public int gettingGold(Coordinate<Integer> start, char[][] map) throws Exception {
        Queue<Coordinate<Integer>> queue = new LinkedList<>();
        queue.add(start);
        int sum = 0;
        while(!queue.isEmpty()){
            Coordinate<Integer> current = queue.poll();
            if (map[current.x][current.y+1] != 'T' && map[current.x][current.y-1] != 'T' && map[current.x+1][current.y] != 'T' && map[current.x-1][current.y] != 'T'){
                if (map[current.x][current.y+1] != '#' && map[current.x][current.y+1] != 'V'){
                    if (map[current.x][current.y+1] == 'G'){
                        sum++;
                    }
                    map[current.x][current.y+1] = 'V';
                    queue.add(new Coordinate<Integer>(current.x, current.y+1));
                }
                if (map[current.x][current.y-1] != '#' && map[current.x][current.y-1] != 'V'){
                    if (map[current.x][current.y-1] == 'G'){
                        sum++;
                    }
                    map[current.x][current.y-1] = 'V';
                    queue.add(new Coordinate<Integer>(current.x, current.y-1));
                }
                if (map[current.x+1][current.y] != '#' && map[current.x+1][current.y] != 'V'){
                    if (map[current.x+1][current.y] == 'G'){
                        sum++;
                    }
                    map[current.x+1][current.y] = 'V';
                    queue.add(new Coordinate<Integer>(current.x+1, current.y));
                }
                if (map[current.x-1][current.y] != '#' && map[current.x-1][current.y] != 'V'){
                    if (map[current.x-1][current.y] == 'G'){
                        sum++;
                    }
                    map[current.x-1][current.y] = 'V';
                    queue.add(new Coordinate<Integer>(current.x-1, current.y));
                }
            }
        }
        return sum;
    }

    public String printMap(char[][] map){
        StringBuilder sb = new StringBuilder();
        int ys = map[0].length;
        int xs = map.length;
        for (int i = 0; i < ys; i++) {
            for (int j = 0; j < xs; j++) {
                sb.append(map[j][i]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
