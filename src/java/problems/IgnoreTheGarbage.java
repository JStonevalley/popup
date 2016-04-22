package problems;

import utilities.Kattio;

import java.lang.reflect.Array;
import java.util.Arrays;

public class IgnoreTheGarbage {

    public static void main(String [] args) {
        Kattio io = new Kattio(System.in, System.out);
        int[] base = {0, 1, 2, 5, 9, 8, 6};
        while(io.hasMoreTokens()) {
            int number10Base = io.getInt();
            int[] number7Base = new int[1];
            boolean first = true;
            while(number10Base != 0){
                if (number10Base < 7){
                    number7Base[0] = number10Base;
                    break;
                }
                int log = log7(number10Base);
                if (first){
                    number7Base = new int[log+1];
                    Arrays.fill(number7Base, 0);
                    first = false;
                }
                int remove = (int)Math.pow(7, (double)log);
                while(number10Base >= remove){
                    number10Base -= remove;
                    number7Base[log]++;
                }
            }
            for (int i = 0; i < number7Base.length; i++) {
                number7Base[i] = base[number7Base[i]];
                io.print(number7Base[i]);
            }
            io.println();
            io.flush();
        }
        io.close();
    }

    public static int log7(double number){
        return (int)(Math.log(number) / Math.log(7));
    }
}
