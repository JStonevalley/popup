package algorithms;

import utilities.Kattio;

/**
 * Created by Erik Ranby & Jonas Stendahl
 */
public class ModularArithmetic {

    /**
     * Main method that reads the input and calls the correct operation.
     */
    public static void main(String[] args){
        ModularArithmetic ma = new ModularArithmetic();
        Kattio io = new Kattio(System.in, System.out);

        long mod = io.getLong();
        int numOperations = io.getInt();
        while (mod > 0 && numOperations > 0) {
            for (int i = 0; i < numOperations; i++) {
                long a = io.getLong();
                String op = io.getWord();
                long b = io.getLong();

                switch (op) {
                    case "+":
                        System.out.println(ma.add(a, b, mod));
                        break;
                    case "-":
                        System.out.println(ma.sub(a, b, mod));
                        break;
                    case "*":
                        System.out.println(ma.mul(a, b, mod));
                        break;
                    case "/":
                        System.out.println(ma.div(a, b, mod));
                        break;
                }
            }

            mod = io.getLong();
            numOperations = io.getInt();
        }
    }

    /**
     * Adds the numbers a and b modulo mod.
     */
    public long add(long a, long b, long mod) {
        long res = (a + b) % mod;
        return res;
    }

    /**
     * Subtracts the number b from a modulo mod.
     */
    public long sub(long a, long b, long mod) {
        long res;
        if (b > a) {
//             -x (mod n) = n - (x (mod n))
            long pos = Math.abs(a - b);
            res = mod - (pos % mod);
        }
        else {
            res = (a - b) % mod;
        }
        return res;
    }

    /**
     * Multiplies the numbers a and b modulo mod.
     */
    public long mul(long a, long b, long mod) {
        long x = a % mod;
        long y = b % mod;
        return (x * y) % mod;
    }

    /**
     * Divides the number a by b modulo mod.
     */
    public long div(long a, long b, long mod) {
        long inverse = inverse(b, mod);
        if (inverse == -1) {
            return -1;
        }
        long res = mul(a, inverse, mod);
        return res;
    }

    /**
     * Calculates the modular inverse of a
     */
    private long inverse(long a, long mod) {
        long t = 0, newt = 1;
        long r = mod, newr = a;

        long quot, tmpt, tmpr;
        while (newr != 0) {
            quot = r / newr;

            tmpt = newt;
            newt = t - (quot * newt);
            t = tmpt;

            tmpr = newr;
            newr = r - (quot * newr);
            r = tmpr;
        }
        if (r > 1) return -1;
        if (t < 0) {
            t = t + mod;
        }
        return t;
    }
}
