package problems;

import utilities.Kattio;

import java.util.HashSet;

public class MatchSticks {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int cases = io.getInt();
        for (int i = 0; i < cases; i++) {
            int sticks = io.getInt();
            String[] minimum = new String[9];
            minimum[0] = "";
            minimum[1] = "10";
            minimum[2] = "1";
            minimum[3] = "7";
            minimum[4] = "4";
            minimum[5] = "2";
            minimum[6] = "6";
            minimum[7] = "8";
            minimum[8] = "10";
            String min = "";
            int tempSticks = sticks;
            while(tempSticks >= minimum.length){
                tempSticks -= 7;
                min += minimum[7];
            }
            if (tempSticks == 3){
                if (min.length() > 1){
                    min = min.replaceFirst("88", "00");
                    tempSticks += 2;
                }
                else if (min.length() == 1){
                    min = min.replaceFirst("8", "2");
                    tempSticks += 2;
                }
            }
            else if (tempSticks == 4){
                if (min.length() > 0){
                    min = min.replaceFirst("8", "0");
                    tempSticks += 1;
                }
            }
            min = minimum[tempSticks] + min;


            String max;
            if (sticks % 2 != 0)
                max = "7";
            else
                max = "1";
            for (int j = 0; j < sticks / 2 - 1; j++) {
                max += "1";
            }
            io.println(min + " " + max);

        }
        io.close();
    }
}
