package problems;

import utilities.Kattio;

import java.math.BigInteger;
import java.util.Arrays;

public class Ljutnja {

    private long candiesGiven = 0;
    private long candies;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        long candies = io.getInt();
        int numChildren = io.getInt();
        Ljutnja ljutnja = new Ljutnja(io, candies, numChildren);
    }

    public Ljutnja(Kattio io, long candies, int numChildren) {
        int[] children = new int[numChildren];
        this.candies = candies;
        for (int i = 0; i < numChildren; i++) {
            children[i] = io.getInt();
        }
        Arrays.sort(children);
        BigInteger anger = new BigInteger("0");
        long candiesGiven = 0;
        int index = children.length-1;
        int high = children.length-1;
        for (int candiesRequested = children[children.length-1]; candiesRequested > 0; candiesRequested--) {
            while(index >= 0 && children[index] >= candiesRequested){
                index--;
            }
            if (candiesGiven + (high - index) <= candies){
                candiesGiven += (children.length - 1 - index);
            }
            else{
                anger = anger.add(BigInteger.valueOf(candiesRequested).pow(2).multiply(BigInteger.valueOf((high - index) - (candies - candiesGiven))));
                anger = anger.add(BigInteger.valueOf(candiesRequested-1).pow(2).multiply(BigInteger.valueOf((candies - candiesGiven))));
                //anger += Math.pow(candiesRequested, 2) * ((high - index) - (candies - candiesGiven));
                //anger += Math.pow(candiesRequested-1, 2) * (candies - candiesGiven);
                candiesGiven = candies;
                break;
            }
        }
        for (int i = 0; i <= index; i++) {
            anger = anger.add(BigInteger.valueOf(children[i]).pow(2));
            //anger += Math.pow(children[i], 2);
        }
        System.out.println(anger);
    }
}
