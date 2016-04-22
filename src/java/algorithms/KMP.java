package algorithms;
import utilities.Kattio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The calculation of T is not optimal. Takes more time than needed.
 */
public class KMP {
    /**
     * Get indices of matches of pattern in text.
     * @param text to find pattern in.
     * @param pattern to find matches of.
     * @return
     */
    public ArrayList<Integer> getMatches(char[] text, char[] pattern){
        int[] t = buildT(pattern);
        // Start of possible pattern
        int m = 0;
        // Index in pattern
        int i = 0;
        ArrayList<Integer> positions = new ArrayList<>();
        while (m + i < text.length) {
            if (i == pattern.length) {
                positions.add(m);
                m = m + i - t[i];
                i = t[i];
            }
            if (pattern[i] == text[m + i]){
                i++;
            }
            else{
                m = Math.max(m + i - t[i], m + 1);
                i = t[i];
            }
        }
        if (i == pattern.length){
            positions.add(m);
        }
        return positions;
    }

    /**
     * Find possible started patterns in the given pattern.
     * @param pattern to find indexes in.
     * @return
     */
    private int[] buildT(char[] pattern){
        int[] t = new int[pattern.length + 1];
        t[0] = 0;
        for (int i = 1; i < t.length - 1; i++) {
            if (pattern[t[i]] == pattern[i]){
                t[i+1] = t[i] + 1;
            }
        }
        return t;
    }

    public static void main(String[] args) throws IOException {

        Kattio io = new Kattio(System.in, System.out);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        KMP kmp = new KMP();
        String line;
        while((line = reader.readLine()) != null){
            char[] p = line.toCharArray();
            char[] s = reader.readLine().toCharArray();
            for (Integer position : kmp.getMatches(s, p)){
                io.print(position + " ");
            }
            io.println();
            io.flush();
        }
        io.close();
    }
}
