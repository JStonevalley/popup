package problems;

import utilities.Kattio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Palindrome {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int  numStrings = io.getInt();
        char[] p;
        Palindrome pal = new Palindrome();
        for (int i = 0; i < numStrings; i++) {
            p = io.getWord().toCharArray();
            int middleIndex = pal.canBePalindrome(p);
            if (middleIndex >= 0) {
                ArrayList<Character> pa = new ArrayList<>();
                for (int j = 0; j < p.length; j++) {
                    //if (j != middleIndex){
                        pa.add(p[j]);
                    //}
                }
                //if (middleIndex == Integer.MAX_VALUE)
                    io.println(pal.swapsRequired(pa));
                //else
                //    io.println(pal.swapsRequired(pa) + Math.abs(p.length/2 - middleIndex));
            }
            else
                io.println("Impossible");
        }
        io.close();
    }

    public int canBePalindrome(char[] p){
        HashMap<Character, ArrayList<Integer>> check = new HashMap<>();
        int i = 0;
        for (char c : p){
            if (check.containsKey(c)) {
                check.get(c).add(i);
            }
            else{
                check.put(c, new ArrayList<>());
                check.get(c).add(i);
            }
            i++;
        }
        int index = Integer.MAX_VALUE;
        for(Character c : check.keySet()){
            ArrayList<Integer> current = check.get(c);
            if (current.size() % 2 != 0 && index == Integer.MAX_VALUE){
                index = current.get(current.size()/2);
            }
            else if (current.size() % 2 != 0){
                return -1;
            }
        }
        return index;
    }

    public int swapsRequired(ArrayList<Character> p){
        int swaps = 0;
        char temp;
        int left = 0;
        int right = 0;
        int f = 0;
        int b = p.size() - 1;
        while (f < b) {
            for (int i = b; i >= f; i--) {
                if (p.get(i) == p.get(f)){
                    left = i;
                    break;
                }
            }
            for (int i = f; i <= b; i++) {
                if (p.get(i) == p.get(b)){
                    right = i;
                    break;
                }
            }
            if (b - left < right - f){
                while(left < b) {
                    temp = p.get(left);
                    p.set(left, p.get(left + 1));
                    p.set(left + 1, temp);
                    swaps++;
                    left++;
                }
            }
            else {
                while(right > f) {
                    temp = p.get(right);
                    p.set(right, p.get(right - 1));
                    p.set(right - 1, temp);
                    swaps++;
                    right--;
                }
            }
            f++;
            b--;
        }
        return swaps;
    }
}
