package algorithms;

import utilities.Kattio;

//Erik Ranby & Jonas Stendahl
public class RationalNumber implements Comparable<RationalNumber>{
    private long n;
    private long d;

    /**
     * Constructor that ensures a value with smallest possible denominator and
     * negative sign on nominator.
     * @param n nominator
     * @param d denominator
     */
    public RationalNumber(long n, long d) {
        this.n = d < 0 ? -n : n;
        this.d = Math.abs(d);
        simplify();
    }

    /**
     * Addition of rational numbers.
     * @param other number to be added.
     * @return result of addition.
     */
    public RationalNumber add(RationalNumber other){
        long nn = other.d * n + d * other.n;
        long nd = other.d * d;
        return new RationalNumber(nn, nd);
    }

    /**
     * Subtraction of rational numbers.
     * @param other number to be subtracted.
     * @return result of subtraction.
     */
    public RationalNumber sub(RationalNumber other){
        long nn = other.d * n - d * other.n;
        long nd = other.d * d;
        return new RationalNumber(nn, nd);
    }

    /**
     * Multiplication of rational numbers.
     * @param other number to be multiplied with.
     * @return result of multiplication.
     */
    public RationalNumber mul(RationalNumber other){
        long nn = n * other.n;
        long nd = other.d * d;
        return new RationalNumber(nn, nd);
    }

    /**
     * Division of rational numbers.
     * @param other number to be divided with.
     * @return result of division.
     */
    public RationalNumber div(RationalNumber other){
        long nn = n * other.d;
        long nd = d * other.n;
        return new RationalNumber(nn, nd);
    }

    /**
     * Make as simple as possible with smallest denominator possible.
     */
    private void simplify(){
        long gcd = gcd(n, d);
        n /= gcd;
        d /= gcd;
    }

    private long gcd(long a, long b){
        if (b == 0)
            return Math.abs(a);
        return gcd(b, a % b);
    }

    @Override
    public int compareTo(RationalNumber other) {
        if (other.n * d < n * other.d)
            return 1;
        else if (other.n * d > n * other.d)
            return -1;
        return 0;
    }

    @Override
    public String toString(){
        return n + " / " + d;
    }

    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int numOperations = io.getInt();
        for (int i = 0; i < numOperations; i++) {
            RationalNumber a = new RationalNumber(io.getInt(), io.getInt());
            String operand = io.getWord();
            RationalNumber b = new RationalNumber(io.getInt(), io.getInt());
            switch (operand){
                case "+":
                    io.println(a.add(b));
                    break;
                case "-":
                    io.println(a.sub(b));
                    break;
                case "*":
                    io.println(a.mul(b));
                    break;
                case "/":
                    io.println(a.div(b));
                    break;
            }
            io.flush();
        }
        io.close();
    }
}
