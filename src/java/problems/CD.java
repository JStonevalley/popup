package problems;

import utilities.Kattio;

public class CD {
    public static void main(String [] args) {
        Kattio io = new Kattio(System.in);
        while(true) {
            int[] jackCollection = new int[io.getInt()];
            int[] jillCollection = new int[io.getInt()];
            if (jackCollection.length == 0 && jillCollection.length == 0){
                break;
            }
            for (int i = 0; i < jackCollection.length; i++) {
                jackCollection[i] = io.getInt();
            }
            for (int i = 0; i < jillCollection.length; i++) {
                jillCollection[i] = io.getInt();
            }
            int ja = 0, ji = 0, cdsToSell = 0;
            while (ja < jackCollection.length && ji < jillCollection.length) {
                if (jackCollection[ja] == jillCollection[ji]) {
                    cdsToSell++;
                    ji++;
                    ja++;
                } else if (jackCollection[ja] < jillCollection[ji]) {
                    ja++;
                } else {
                    ji++;
                }
            }
            System.out.println(cdsToSell);
        }
    }
}
