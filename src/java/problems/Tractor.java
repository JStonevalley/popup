package problems;

import datastructures.Coordinate;
import utilities.Kattio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Tractor {

    HashMap<Coordinate<Integer>, Integer> solutions = new HashMap<>();

    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int numCases = io.getInt();
        Tractor t = new Tractor();
        for (int i = 0; i < numCases; i++) {
            int xMax = io.getInt();
            int yMax = io.getInt();
            io.println(xMax + " " + yMax);
            io.println(t.math(xMax, yMax));
            io.println(t.getNumPos(xMax, yMax));
            io.println("------------");
        }
        io.close();
    }

    public int math(int xMax, int yMax){
        int xLog = Log2(xMax + 1) + 1;
        int yLog = Log2(yMax + 1) + 1;
        int base;
        int incr;
        int index = 0;
        int pow = 0;
        if (xMax > yMax){
            base = xLog;
            incr = xLog - 1;
            while (index < yMax){
                base += incr * Math.min(Math.round(Math.pow(2, pow)), yMax - index);
                index += Math.round(Math.pow(2, pow));
                pow++;
                incr--;
            }
            base += xMax - Math.round(Math.pow(2, xLog-1) - 1);
        }
        else{
            base = yLog;
            incr = yLog - 1;
            while (index < xMax) {
                base += incr * Math.min(Math.round(Math.pow(2, pow)), xMax - index);
                index += Math.round(Math.pow(2, pow));
                pow++;
                incr--;
            }
            base += yMax - Math.round(Math.pow(2, yLog-1) - 1);
        }
        return base;
    }

    public int getNumPos(int xMax, int yMax){
        if (solutions.containsKey(new Coordinate<>(xMax, yMax)))
            return solutions.get(new Coordinate<>(xMax, yMax));
        Queue<TimeStep> queue = new LinkedList<>();
        boolean[][] field = new boolean[xMax + 1][];
        for (int i = 0; i < field.length; i++) {
            field[i] = new boolean[yMax + 1];
        }
        int max = 1;
        field[0][0] = true;
        queue.add(new TimeStep(new Coordinate<>(0, 0), 0));
        while (!queue.isEmpty()){
            TimeStep prev = queue.poll();
            int incr = (int)Math.round(Math.pow(2, (prev.t)));
            if (prev.c.x + incr <= xMax && !field[prev.c.x + incr][prev.c.y]){
                field[prev.c.x + incr][prev.c.y] = true;
                max++;
                queue.add(new TimeStep(new Coordinate<>(prev.c.x + incr, prev.c.y), prev.t + 1));
            }
            if (prev.c.y + incr <= yMax && !field[prev.c.x][prev.c.y + incr]){
                field[prev.c.x][prev.c.y + incr] = true;
                max++;
                queue.add(new TimeStep(new Coordinate<>(prev.c.x, prev.c.y  + incr), prev.t + 1));
            }
        }
        solutions.put(new Coordinate<>(xMax, yMax), max);
        solutions.put(new Coordinate<>(yMax, xMax), max);
        return max;
    }

    public int Log2(int n){
        if (n < 2)
            return 0;
        int log = 1;
        int upper = 2;
        while (upper <= n){
            upper *= 2;
            log++;
        }
        return log - 1;
    }


    public static class TimeStep{
        public final Coordinate<Integer> c;
        public final int t;

        public TimeStep(Coordinate<Integer> c, int t) {
            this.c = c;
            this.t = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimeStep timeStep = (TimeStep) o;

            if (t != timeStep.t) return false;
            return c.equals(timeStep.c);

        }

        @Override
        public int hashCode() {
            int result = c.hashCode();
            result = 31 * result + t;
            return result;
        }
    }
}
