package problems;

import algorithms.EratosthenesSieve;
import utilities.Kattio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Factovisors {

    EratosthenesSieve isPrime;
    public static void main(String[] args){

        Factovisors fv = new Factovisors((int) Math.sqrt(Integer.MAX_VALUE));
        Kattio io = new Kattio(System.in, System.out);

        while(io.hasMoreTokens()) {
            int factorial = io.getInt();
            int divisor = io.getInt();

            if (fv.isFactovisor(factorial, divisor)) {
                io.println(divisor + " divides " + factorial + "!");
            } else {
                io.println(divisor + " does not divide " + factorial + "!");
            }
            io.flush();
        }
        io.close();
    }

    public Factovisors(int n){
        isPrime = new EratosthenesSieve(n);
    }


    public int baseSum(int n, int b){
        int sum = 0;
        while(n >= b){
            sum += (n % b);
            n /= b;
        }
        sum += n;

        return sum;
    }

    public boolean isFactovisor(int factorial, int divisor){

        if (divisor == 0)
            return false;
        if (factorial >= divisor)
            return true;

        HashMap<Integer, Integer> factors = trialDivisionPowers(divisor);
        for (int factor : factors.keySet()){
            int sum = baseSum(factorial, factor);
            if ((factorial - sum)/(factor - 1) < factors.get(factor))
                return false;
        }
        return true;
    }

    public HashMap<Integer, Integer> trialDivisionPowers(int n){
        HashMap<Integer, Integer> factors = new HashMap<>();
        int sqrtN = ((int)Math.sqrt(n)) + 1;
        for (int i = 2; i < sqrtN && n > 1; i++) {
            if (isPrime.isPrime(i)){
                int count = 0;
                while(n % i == 0){
                    count++;
                    n /= i;
                }
                factors.put(i, count);
            }
        }
        if (n != 1) {
            factors.put(n, 1);
        }
        return factors;
    }
}
