package problems;

import utilities.Kattio;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class PebbleSolitair {

    private HashMap<String, Integer> states;
    private static final int GAME_SIZE = 23;
    private int best;

    public static void main(String [] args) {
        Kattio io = new Kattio(System.in, System.out);
        PebbleSolitair solitair = new PebbleSolitair();
        int numGames = io.getInt();
        for (int i = 0; i < numGames; i++) {
            io.println(solitair.getOptimalMoves(io.getWord()));
            io.flush();
        }
        io.close();
    }

    public PebbleSolitair(){
        states = new HashMap<>();
    }

    public boolean[] getStateFromString(String stateString){
        boolean[] state = new boolean[stateString.length()];
        for (int i = 0; i < stateString.length(); i++) {
            if (stateString.charAt(i) == '-')
                state[i] = false;
            if (stateString.charAt(i) == 'o')
                state[i] = true;
        }
        return state;
    }

    public int getOptimalMoves(String state){
        best = 23;
        return getOptimalMovesRecursion(state);
    }

    private int getOptimalMovesRecursion(String state){
        if (states.containsKey(state)){
            return states.get(state);
        }
        int min = Integer.MAX_VALUE;
        String next;
        for (int i = 0; i < GAME_SIZE - 1; i++) {
            if (state.charAt(i) == 'o' && state.charAt(i+1) == 'o'){
                if (i + 2 < GAME_SIZE && state.charAt(i + 2) == '-'){
                    next = state.substring(0, i) + "--o" + state.substring(i+3, state.length());
                    if (!states.containsKey(next)){
                        states.put(next, getOptimalMovesRecursion(next));
                    }
                    min = Math.min(min, states.get(next));
                }
                if (i - 1 > 0 && state.charAt(i - 1) == '-'){
                    next = state.substring(0, i-1) + "o--" + state.substring(i+2, state.length());
                    if (!states.containsKey(next)){
                        states.put(next, getOptimalMovesRecursion(next));
                    }
                    min = Math.min(min, states.get(next));
                }
            }
        }
        if (min != Integer.MAX_VALUE){
            states.put(state, min);
            return min;
        }
        else {
            int count = 0;
            for (int i = 0; i < GAME_SIZE; i++) {
                if (state.charAt(i) == 'o') {
                    count++;
                }
            }
            states.put(state, count);
            return count;
        }
    }
}
