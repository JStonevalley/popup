package problems;

import utilities.Kattio;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class PerfectPthPower {
    public static void main(String [] args) throws Exception{
        Kattio io = new Kattio(System.in, System.out);
        long number;
        PerfectPthPower pPower = new PerfectPthPower();
        long pthPower = 0;
        while((number = io.getLong()) != 0){
            //pthPower = pPower.getPPower(number);
            pthPower = pPower.getPPowerBruteForce(number);
            if (pthPower != 1){
//                double root = Math.pow(number, (1.0 / pthPower));
//                long a = Math.round(Math.pow(root, (double)pthPower));
//                if (a != number) {
//                    System.out.println("Fail");
//                }
            }
            io.println(pthPower);
        }
        io.close();
    }

    public long getPPowerBruteForce(long number){
        long limit = Math.round(Math.sqrt(Math.abs(number))) + 1;
        long start = -limit;
        double root;
        if (number > 0) {
            for (long i = 2; i < limit + 1; i++){
                long tempNr = number;
                long pow = 0;
                while(tempNr % i == 0){
                    pow++;
                    tempNr /= i;
                }
                if(tempNr == 1){
                    return pow;
                }
            }
        }
        if (number < 0) {
            for (long i = -2; i > -limit - 1; i--){
                long tempNr = number;
                long pow = 0;
                while(tempNr % i == 0){
                    pow++;
                    tempNr /= i;
                }
                if(tempNr == 1){
                    return pow;
                }
            }
        }
        return 1;
    }

    private boolean isValidInteger(double root){
        double top = Math.floor(root + 1);
        double bottom = Math.floor(root);
        if (top - root < 0.0000001 || root - bottom < 0.0000001)
            return true;
        return false;

    }

    public long getPPower(long number) throws Exception{
        HashMap<Long, Integer> factors = new HashMap<>();
        while (!BigInteger.valueOf(number).isProbablePrime(Integer.MAX_VALUE)){
            long factor = pollardRho(number, 0);
            if(factors.containsKey(factor)){
                factors.replace(factor, factors.get(factor) + 1);
            }
            else{
                factors.put(factor, 1);
            }
            number /= factor;
        }
        if (number != 0 && number != 1){
            if(factors.containsKey(number)){
                factors.replace(number, factors.get(number) + 1);
            }
            else{
                factors.put(number, 1);
            }
        }
        long[] factorPowers = new long[factors.size()];
        int i = 0;
        for (Long factor : factors.keySet()){
            factorPowers[i++] = factors.get(factor);
        }
        if (factorPowers.length == 1){
            return factorPowers[0];
        }
        return gcd(factorPowers);
    }

    private long pollardRho(long number, int numTries) throws Exception {
        long start = System.currentTimeMillis();
        long x = 2 + numTries;
        long y = 2;
        long c = new Random().nextInt(Integer.MAX_VALUE);
        long d = trial(number);
        while (d == 1){
            x = (x*x+c) % number;
            y = (y*y+c) % number;
            y = (y*y+c) % number;
            d = gcd(Math.abs((int)(x-y)), number);
            if (System.currentTimeMillis() - start > 100){
                d = number;
            }
        }
        if (d == number || !BigInteger.valueOf(d).isProbablePrime(Integer.MAX_VALUE)){
            numTries++;
            return pollardRho(number, numTries);
        }
        return d;
    }

    private int trial(long number){
        int[] earlyPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229};
        for (int prime : earlyPrimes){
            if (number % prime == 0){
                return prime;
            }
        }
        return 1;
    }

    private long gcd(long[] numbers){
        long gcd = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (gcd == 1){
                return 1;
            }
            gcd = gcd(gcd, numbers[i]);
        }
        return gcd;
    }

    public long gcd(long a, long b){
        return (BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))).longValue();
    }

    public double logBase(long b, long n) {
        return Math.log(n) / Math.log(b);
    }

}
