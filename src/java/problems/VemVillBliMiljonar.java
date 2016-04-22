package problems;

import utilities.Kattio;

public class VemVillBliMiljonar {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        int questions = io.getInt();
        double skill = io.getDouble();
        while(questions !=0 && skill != 0){
            System.out.printf("%.3f\n", millionaire(questions, skill));
            questions = io.getInt();
            skill = io.getDouble();
        }
        io.close();
    }

    public static double millionaire(int questions, double skill){
        double prize = Math.round(Math.pow(2, questions));
        for (int i = 1; i <= questions; i++) {
            int qLeft = (questions - i);
            double thresh = Math.pow(2, qLeft)/prize;
            if (skill >= thresh){
                prize = (1 + skill)/2 * prize;
            }
            else{
                double belowThresh = thresh - skill;
                double aboveThresh = 1 - thresh;
                double probRange = 1 - skill;
                double lowerPart = belowThresh/probRange;
                double upperPart = aboveThresh/probRange;
                prize = lowerPart * Math.pow(2, qLeft) + upperPart * (1 + thresh)/2 * prize;
            }
        }
        return prize;
    }
}
