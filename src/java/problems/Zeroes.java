package problems;

import utilities.Kattio;

public class Zeroes {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        long low = io.getLong();
        long high = io.getLong();
        while(low >= 0){
            if (low == 0)
                io.println(count(high));
            else
                io.println(count(high) - count(low-1));
            io.flush();
            low = io.getLong();
            high = io.getLong();
        }
        io.close();
    }

    public static long count(long n){
        if (n == 0){
            return 1;
        }
        String number = "" + n;
        long prefix, postfix;
        long zeroes = 1;
        long deg = Math.round(Math.pow(10, (int)Math.log10(n)-1));
        int count = 1;
        while (count < number.length()){
            if (number.charAt(count) == '0' && count < number.length()-1) {
                prefix = Integer.parseInt(number.substring(0, count)) - 1;
                postfix = Integer.parseInt(number.substring(count)) + 1;
                zeroes += prefix * deg + postfix;
            }
            else{
                prefix = Integer.parseInt(number.substring(0, count));
                zeroes += prefix * deg;
            }
            count++;
            deg /= 10;
        }
        return zeroes;
    }
}
