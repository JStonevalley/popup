package algorithms;//Erik Ranby & Jonas Stendahl
import utilities.Kattio;

import java.util.BitSet;

public class EratosthenesSieve {
    private int N;
    private int numberOfPrimes = 0;
    private int sqrtN;
    private BitSet isPrime;

    /**
     * Creates an object which indicates numbers below n are prime.
     */
    public EratosthenesSieve(int n){
        N = n;
        sqrtN = (int)Math.round(Math.sqrt(n)) + 1;
        isPrime = new BitSet(sqrtN * sqrtN);
        isPrime.flip(0, isPrime.size());
        buildIsPrime(0);

        if (N >= 1){
            isPrime.clear(0);
            isPrime.clear(1);
        }

        for (int i = 0; i <= N; i++) {
            numberOfPrimes = isPrime.get(i) ? numberOfPrimes + 1 : numberOfPrimes;
        }
    }

    /**
     * Recursively builds the bitset which indicates which numbers are prime.
     * @param segmentNr Current segment number.
     */
    private void buildIsPrime(int segmentNr){
        if (segmentNr == sqrtN)
            return;
        for (int i = 2; i < sqrtN; i++) {
            if (isPrime.get(i) && i*i < sqrtN * (segmentNr + 1)){
                int base;
                if (i * i > sqrtN * segmentNr) {
                    base = i * i;
                }
                else{
                    int mod = (sqrtN * segmentNr)%i;

                    base = mod == 0 ? 0 : i - mod;
                    base += sqrtN * segmentNr;
                }

                do {
                    isPrime.clear(base);
                } while((base += i) < sqrtN * (segmentNr + 1));
            }
        }
        buildIsPrime(segmentNr + 1);
    }

    public boolean isPrime(int n){
        return isPrime.get(n);
    }

    public int getNumberOfPrimes() {
        return numberOfPrimes;
    }

    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int n = io.getInt(); int numQueries = io.getInt();
        EratosthenesSieve sieve = new EratosthenesSieve(n);
        io.println(sieve.getNumberOfPrimes());
        boolean prime;
        for (int i = 0; i < numQueries; i++) {
            prime = sieve.isPrime(io.getInt());
            if (prime)
                io.println(1);
            else
                io.println(0);
        }
        io.close();
    }
}
