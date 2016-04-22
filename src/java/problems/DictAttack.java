package problems;

import utilities.Kattio;

import java.util.ArrayList;

public class DictAttack {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int dictSize = io.getInt();
        ArrayList<char[]> dict = new ArrayList<>(dictSize);
        for (int i = 0; i < dictSize; i++) {
            dict.add(io.getWord().toCharArray());
        }
        DictAttack da = new DictAttack();
        while(io.hasMoreTokens()) {
            String word = io.getWord();
            if(da.checkPassWord(dict, word.toCharArray())){
                io.println(word);
            }
            io.flush();
        }
    }

    public boolean checkPassWord(ArrayList<char[]> dict, char[] p){
        int swaps = 3;
        for (char c : p) {
            if (Character.isDigit(c))
                swaps--;
        }
        return checkPass(dict, p, swaps);
    }

    private boolean checkPass(ArrayList<char[]> dict, char[] p, int swaps) {
        for (char[] word : dict){
            if (word.length == p.length && trySwaps(word, p, swaps)){
                return false;
            }
        }
        return true;
    }

    private boolean trySwaps(char[] w, char[] p, int swaps) {
        boolean equal = true;
        for (int i = 0; i < p.length; i++) {
            if (!Character.isDigit(p[i]) && p[i] != w[i]) {
                equal = false;
                break;
            }
        }
        if (equal && swaps >= 0)
            return true;
        if (swaps < 1)
            return false;
        for (int i = 0; i < p.length - 1; i++) {
            char temp = p[i];
            p[i] = p[i + 1];
            p[i + 1] = temp;
            if(trySwaps(w, p, swaps - 1)){
                return true;
            }
            p[i + 1] = p[i];
            p[i] = temp;
        }
        return false;
    }
}
