package problems;

import datastructures.Coordinate;
import utilities.Kattio;

import java.util.LinkedList;
import java.util.Queue;

public class HidingPlaces {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int numCases = io.getInt();
        HidingPlaces hp = new HidingPlaces();
        for (int i = 0; i < numCases; i++) {
            io.println(hp.getHidingPlaces(io.getWord()));
            io.flush();
        }
        io.close();
    }
    public String getHidingPlaces(String position){
        Coordinate<Integer> start = getCoordinate(position);
        int[][] board = new int[8][];
        for (int i = 0; i < 8; i++) {
            board[i] = new int[8];
        }
        Queue<Coordinate<Integer>> queue = new LinkedList<>();
        queue.add(start);
        int max = 0;
        while(!queue.isEmpty()){
            Coordinate<Integer> current = queue.poll();
            int newY;
            int newX = current.x + 2;
            if (newX < 8){
                newY = current.y + 1;
                if (newY < 8 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
                newY = current.y - 1;
                if (newY >= 0 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
            }
            newX = current.x + 1;
            if (newX < 8){
                newY = current.y + 2;
                if (newY < 8 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
                newY = current.y - 2;
                if (newY >= 0 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
            }
            newX = current.x - 2;
            if (newX >= 0){
                newY = current.y + 1;
                if (newY < 8 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
                newY = current.y - 1;
                if (newY >= 0 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
            }
            newX = current.x - 1;
            if (newX >= 0){
                newY = current.y + 2;
                if (newY < 8 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
                newY = current.y - 2;
                if (newY >= 0 && board[newX][newY] == 0){
                    board[newX][newY] = board[current.x][current.y] + 1;
                    queue.add(new Coordinate<>(newX, newY));
                    max = board[current.x][current.y] + 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(max).append(" ");
        for (int j = board.length -1; j >= 0; j--) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == max){
                    sb.append(getPosition(new Coordinate<>(i, j))).append(" ");
                }
            }
        }
        return sb.toString();

    }
    private Coordinate<Integer> getCoordinate(String position){
        char a = position.charAt(0);
        char b = position.charAt(1);
        int x = 0;
        switch (a){
            case 'a':
                x = 0;
                break;
            case 'b':
                x = 1;
                break;
            case 'c':
                x = 2;
                break;
            case 'd':
                x = 3;
                break;
            case 'e':
                x = 4;
                break;
            case 'f':
                x = 5;
                break;
            case 'g':
                x = 6;
                break;
            case 'h':
                x = 7;
                break;
        }
        int y = Integer.parseInt(b + "") - 1;
        return new Coordinate<>(x, y);
    }

    private String getPosition(Coordinate<Integer> position){
        String a = "a";
        String b = (position.y + 1) + "";
        int x = position.x;
        switch (x){
            case 0:
                a = "a";
                break;
            case 1:
                a = "b";
                break;
            case 2:
                a = "c";
                break;
            case 3:
                a = "d";
                break;
            case 4:
                a = "e";
                break;
            case 5:
                a = "f";
                break;
            case 6:
                a = "g";
                break;
            case 7:
                a = "h";
                break;
        }
        return a + b;
    }

}
