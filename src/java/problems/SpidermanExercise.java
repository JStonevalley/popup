package problems;

import utilities.Kattio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class SpidermanExercise {

    public static void main(String [] args) throws IOException {
        Kattio io = new Kattio(System.in);
        SpidermanExercise se = new SpidermanExercise();
        int numCases = io.getInt();
        for (int i = 0; i < numCases; i++) {
            int[] distances = new int[io.getInt()];
            for (int j = 0; j < distances.length; j++){
                distances[j] = io.getInt();
            }
            String result = se.exercise(distances);
            if (result.equals("")){
                result = "IMPOSSIBLE";
            }
            System.out.println(result);
        }
    }

    public String exercise(int[] distances){
        int wallHeight = height(distances);
        if (wallHeight == -1){
            return "";
        }
        wallHeight += 1;
        Break[][] wall = new Break[distances.length][];
        HashSet<Integer> heights = new HashSet<>();
        HashSet<Integer> prevHeights = new HashSet<>();
        wall[0] = new Break[wallHeight];
        wall[0][distances[0]] = new Break(Direction.U, distances[0]);
        heights.add(distances[0]);
        for (int i = 1; i < distances.length; i++) {
            prevHeights = new HashSet<>(heights);
            heights.clear();
            wall[i] = new Break[wallHeight];
            for(int height : prevHeights){
                if (height + distances[i] < wallHeight){
                    if (wall[i][height + distances[i]] == null) {
                        wall[i][height + distances[i]] = new Break(Direction.U, Math.max(wall[i-1][height].getMaxHeight(), height + distances[i]));
                        heights.add(height + distances[i]);
                    }
                    else if ((wall[i][height + distances[i]].getMaxHeight() > Math.max(wall[i-1][height].getMaxHeight(), height + distances[i]))){
                        wall[i][height + distances[i]] = new Break(Direction.U, Math.max(wall[i-1][height].getMaxHeight(), height + distances[i]));
                    }
                }
                if (height - distances[i] >= 0){
                    if (wall[i][height - distances[i]] == null) {
                        wall[i][height - distances[i]] = new Break(Direction.D, wall[i-1][height].getMaxHeight());
                        heights.add(height - distances[i]);
                    }
                    else if ((wall[i][height - distances[i]].getMaxHeight() > wall[i-1][height].getMaxHeight())){
                        wall[i][height - distances[i]] = new Break(Direction.D, wall[i-1][height].getMaxHeight());
                    }
                }
            }
        }
        if (wall[distances.length - 1][0] == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int height = 0;
        Direction prevDirection = wall[distances.length - 1][height].getDirection();
        sb.append(prevDirection);
        for (int i = distances.length - 2; i >= 0 ; i--) {
            if(prevDirection == Direction.U){
                height -= distances[i+1];
            }
            else{
                height += distances[i+1];
            }
            prevDirection = wall[i][height].getDirection();
            sb.append(prevDirection);
        }
        sb.reverse();
        return sb.toString();
    }

    public int height(int[] distances){
        int sum = 0;
        if (distances.length == 1){
            return -1;
        }
        for (int distance : distances){
            sum += distance;
        }
        if (sum % 2 != 0){
            return  -1;
        }
        return sum/2;
    }

    private enum Direction{
        U, D
    }

    private class Break{
        private Direction direction;
        private int maxHeight;

        public Break(Direction direction, int maxHeight) {
            this.direction = direction;
            this.maxHeight = maxHeight;
        }

        public Direction getDirection() {
            return direction;
        }

        public int getMaxHeight() {
            return maxHeight;
        }
    }
}
