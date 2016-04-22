package problems;

import utilities.Kattio;

import java.util.Arrays;

public class Sudoku {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        while(io.hasMoreTokens()) {
            int[] rowWise = new int[Game.WIDTH * Game.WIDTH];
            for (int i = 0; i < Game.WIDTH * Game.WIDTH; i++) {
                rowWise[i] = io.getInt() - 1;
            }
            String vask = io.getWord();
            Game g = null;
            try {
                g = new Game(rowWise);
            } catch (IllegalArgumentException e) {
                System.out.println("Find another job");
                System.out.println();
                continue;
            }
            boolean solved = g.solve();
            if (!solved && g.solution != null) {
                System.out.println("Non-unique");
                System.out.println();
            } else if (!solved) {
                System.out.println("Find another job");
                System.out.println();
            } else {
                System.out.println(g.toStringSolution());
                System.out.println();
            }
        }
    }

    public static class Game {
        static final int WIDTH = 9;
        boolean[] immutable = new boolean[WIDTH * WIDTH];
        boolean[][] cells = new boolean[WIDTH][];
        boolean[][] rows = new boolean[WIDTH][];
        boolean[][] columns = new boolean[WIDTH][];
        int[][] board = new int[WIDTH][];
        int[][] solution = null;

        public Game(int[] rowWise) {
            for (int i = 0; i < WIDTH; i++) {
                cells[i] = new boolean[WIDTH];
                rows[i] = new boolean[WIDTH];
                columns[i] = new boolean[WIDTH];
                board[i] = new int[WIDTH];
                Arrays.fill(board[i], -1);
            }
            int row, col;
            for (int i = 0; i < WIDTH * WIDTH; i++) {
                if (rowWise[i] != -1){
                    row = i / WIDTH;
                    col = i % WIDTH;
                    if (rows[row][rowWise[i]] || columns[col][rowWise[i]] || getCellValue(row, col, rowWise[i])){
                        throw new IllegalArgumentException();
                    }
                    rows[row][rowWise[i]] = true;
                    columns[col][rowWise[i]] = true;
                    setCellValue(row, col, rowWise[i], true);
                    board[row][col] = rowWise[i];
                    immutable[i] = true;
                }

            }
        }

        public void setCellValue(int row, int col, int num, boolean val){
            if (row < 3)
                cells[col/3][num] = val;
            else if (row < 6)
                cells[col/3 + 3][num] = val;
            else if (row < 9)
                cells[col/3 + 6][num] = val;
        }

        public boolean getCellValue(int row, int col, int num){
            if (row < 3)
                return cells[col/3][num];
            else if (row < 6)
                return cells[col/3 + 3][num];
            else
                return cells[col/3 + 6][num];
        }

        public boolean solve() {
            boolean first = true;
            int row, col;
            int i = 0;
            while (i >= 0 && i < WIDTH * WIDTH) {
                row = i / WIDTH;
                col = i % WIDTH;
                if (immutable[i] || put(row, col)){
                    i++;
                    if (i >= WIDTH * WIDTH && first){
                        first = false;
                        saveSolution();
                        while(--i >= 0 && immutable[i]){}
                    }
                    else if (i >= WIDTH * WIDTH){
                        return false;
                    }
                }
                else {
                    while(--i >= 0 && immutable[i]){}
                }
            }
            return !first;
        }
        public boolean put(int row, int col){
            int prevNumber = board[row][col];
            if (prevNumber >= 0)
                clearNumber(row, col);
            for (int i = prevNumber + 1; i < WIDTH; i++) {
                if (!rows[row][i] && !columns[col][i] && !getCellValue(row, col, i)) {
                    rows[row][i] = true;
                    columns[col][i] = true;
                    setCellValue(row, col, i, true);
                    board[row][col] = i;
                    return true;
                }
            }
            return false;
        }

        private void clearNumber(int row, int col){
            int prevNumber = board[row][col];
            board[row][col] = -1;
            rows[row][prevNumber] = false;
            columns[col][prevNumber] = false;
            setCellValue(row, col, prevNumber, false);
        }

        private void saveSolution(){
            solution = new int[WIDTH][];
            for (int i = 0; i < WIDTH; i++) {
                solution[i] = new int[WIDTH];
                for (int j = 0; j < WIDTH; j++) {
                    solution[i][j] = board[i][j];
                }
            }
        }

        public String toStringSolution(){
            String solutionString = "";
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    solutionString += (solution[i][j] + 1) + " ";
                }
                solutionString += "\n";
            }
            return solutionString.substring(0, solutionString.length()-2);
        }

        @Override
        public String toString(){
            String boardString = "";
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    boardString += (board[i][j] + 1) + " ";
                }
                boardString += "\n";
            }
            return boardString.substring(0, boardString.length()-2);
        }
    }
}
