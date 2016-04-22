package problems;

import algorithms.EratosthenesSieve;
import utilities.Kattio;

import java.util.HashSet;

public class HappyPrimes {
    public static HashSet<Integer> loop;
    EratosthenesSieve isPrime;

    public static void main(String[] args) {
        HappyPrimes hp = new HappyPrimes();
        Kattio io = new Kattio(System.in, System.out);
        int numbers = io.getInt();
        for (int i = 1; i <= numbers; i++) {
            io.getInt();
            int number = io.getInt();
            if (hp.isPrime.isPrime(number) && hp.isHappy(number)){
                io.println(i + " " + number + " YES");
            }
            else{
                io.println(i + " " + number + " NO");
            }
        }
        io.close();
    }

    public HappyPrimes(){
        isPrime = new EratosthenesSieve(10000);
        loop = new HashSet<>();
        loop.add(4);
        loop.add(16);
        loop.add(37);
        loop.add(58);
        loop.add(89);
        loop.add(145);
        loop.add(42);
        loop.add(20);
    }

    public boolean isHappy(int n){
        if (loop.contains(n)){
            return false;
        }
        else if (n == 1){
            return true;
        }
        else{
            int sum = 0;
            int mod;
            while(n > 0){
                mod = n % 10;
                n/=10;
                if (mod != 0){
                    sum += mod * mod;
                }
            }
            return isHappy(sum);
        }
    }

}
