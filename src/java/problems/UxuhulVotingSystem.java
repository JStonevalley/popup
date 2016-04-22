package problems;

import utilities.Kattio;

import java.util.ArrayList;
import java.util.HashSet;

public class UxuhulVotingSystem {
    ArrayList<HashSet<Integer>> conversions;
    public static void main(String [] args) {
        Kattio io = new Kattio(System.in, System.out);
        UxuhulVotingSystem uvs = new UxuhulVotingSystem();
        String[] states = {"NNN", "NNY", "NYN", "NYY", "YNN", "YNY", "YYN", "YYY"};
        int cases = io.getInt();
        for (int i = 0; i < cases; i++) {
            int priests = io.getInt();
            int[][] preferences = new int[priests][];
            for (int j = 0; j < priests; j++) {
                preferences[j] = new int[8];
                for (int k = 0; k < 8; k++) {
                    preferences[j][io.getInt() -1] = k;
                }
            }
            io.println(states[uvs.getOutcome(preferences)]);
            io.flush();
        }
        io.close();
    }

    public UxuhulVotingSystem(){
        initiateConversions();
    }

    public int getOutcome(int[][] preferences){
        int[][] outcomes = new int[preferences.length][];
        int[][] finalOutcomes = new int[preferences.length][];
        outcomes[preferences.length-1] = new int[8];
        finalOutcomes[preferences.length-1] = new int[8];
        boolean[] setfinal = new boolean[8];
        for (int j = 0; j < 8; j++) {
            for (int preference : preferences[preferences.length-1]){
                if (!setfinal[j] && conversions.get(j).contains(preference)){
                    outcomes[preferences.length-1][j] = preference;
                    setfinal[j] = true;
                    finalOutcomes[preferences.length-1][j] = preference;
                }
            }
        }
        for (int i = preferences.length-2; i >= 0; i--) {
            setfinal = new boolean[8];
            outcomes[i] = new int[8];
            finalOutcomes[i] = new int[8];
            int set = 0;
            for (int preference : preferences[i]){
                if (set < 8) {
                    for (int j = 0; j < 8; j++) {
                        if (finalOutcomes[i+1][j] == preference) {
                            for (int conversion : conversions.get(j)){
                                if (!setfinal[conversion]){
                                    outcomes[i][conversion] = j;
                                    finalOutcomes[i][conversion] = preference;
                                    setfinal[conversion] = true;
                                    set++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return finalOutcomes[0][0];
    }

    private void initiateConversions(){
        conversions = new ArrayList<>();
        conversions.add(new HashSet<>(3));
        conversions.get(0).add(1);
        conversions.get(0).add(2);
        conversions.get(0).add(4);
        conversions.add(new HashSet<>(3));
        conversions.get(1).add(0);
        conversions.get(1).add(3);
        conversions.get(1).add(5);
        conversions.add(new HashSet<>(3));
        conversions.get(2).add(0);
        conversions.get(2).add(3);
        conversions.get(2).add(6);
        conversions.add(new HashSet<>(3));
        conversions.get(3).add(1);
        conversions.get(3).add(2);
        conversions.get(3).add(7);
        conversions.add(new HashSet<>(3));
        conversions.get(4).add(0);
        conversions.get(4).add(5);
        conversions.get(4).add(6);
        conversions.add(new HashSet<>(3));
        conversions.get(5).add(1);
        conversions.get(5).add(4);
        conversions.get(5).add(7);
        conversions.add(new HashSet<>(3));
        conversions.get(6).add(2);
        conversions.get(6).add(4);
        conversions.get(6).add(7);
        conversions.add(new HashSet<>(3));
        conversions.get(7).add(3);
        conversions.get(7).add(5);
        conversions.get(7).add(6);
    }
}
