package algorithms.intervalcover;

import utilities.Kattio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class IC {

    private final static boolean kattis = false;

    public static void main(String[] args) throws FileNotFoundException {
        Kattio io;
        ArrayList<ICCase> cases = new ArrayList<>();
        if (kattis) {
            io = new Kattio(System.in);
        } else {
            io = new Kattio(new BufferedInputStream(new FileInputStream("src\\resources\\inputIntervalCover")));
        }
        while (io.hasMoreTokens()) {
            ICInterval itc = new ICInterval(io.getDouble(), io.getDouble());
            int size = io.getInt();
            ArrayList<ICInterval> intervals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                intervals.add(new ICInterval(io.getDouble(), io.getDouble(), i));
            }
            cases.add(new ICCase(itc, intervals));
        }
        ArrayList<ICSolution> solutions = (new IC()).coverIntervals(cases);

        //Print the solutions or impossible if not solvable
        for (int i = 0; i < solutions.size(); i++) {
            if (!solutions.get(i).isValid()) {
                System.out.println("impossible");
            } else {
                System.out.println(solutions.get(i).getSi().size());
                for (ICInterval interval : solutions.get(i).getSi()) {
                    System.out.print(interval.getIndex() + " ");
                }
                System.out.println();
            }
        }
    }

    // Try to cover the intervals for each case
    public ArrayList<ICSolution> coverIntervals(ArrayList<ICCase> cases) {
        for (int i = 0; i < cases.size(); i++) {
            Collections.sort(cases.get(i).getIntervals());
        }
        ArrayList<ICSolution> solutions = new ArrayList<>(cases.size());
        for (ICCase cas : cases) {
            solutions.add(coverInterval(cas));
        }
        return solutions;
    }

    // Cover an interval
    public ICSolution coverInterval(ICCase cas) {
        ICSolution solution = new ICSolution(cas.getItc());
        ICInterval best = cas.getIntervals().get(cas.getIntervals().size()-1);
        ICInterval temp;
        int index = 0;
        // While there are still intervals not tested and the solution is not valid.
        while (!solution.isValid() && !cas.getIntervals().isEmpty()) {
            index = cas.getIntervals().size() - 1;
            boolean changed = false;
            // Find all intervals that have a lower limit below what is left to cover.
            while (index >= 0 && cas.getIntervals().get(index).getLow() <= solution.getInc().getLow()) {
                temp = cas.getIntervals().remove(index);
                //Take the interval with the highest upper limit
                if (temp.getHigh() >= best.getHigh()) {
                    best = new ICInterval(temp.getLow(), temp.getHigh(), temp.getIndex());
                    changed = true;
                }
                index--;
            }
            // If there were any interval that had a lower limit below what was needed to cover
            // the best of the available is added to the solution.
            if (changed) {
                solution.getSi().add(new ICInterval(best.getLow(), best.getHigh(), best.getIndex()));
                solution.setInc(new ICInterval(best.getHigh(), solution.getInc().getHigh()));
            } else {
                break;
            }
        }
        return solution;
    }
}
