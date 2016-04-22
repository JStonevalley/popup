package algorithms.intervalcover;

import java.util.ArrayList;

/**
 * Created by jonas on 2015-09-02.
 */
public class ICCase {
    private ICInterval itc;
    private ArrayList<ICInterval> intervals;

    public ICCase(ICInterval itc, ArrayList<ICInterval> intervals) {
        this.itc = itc;
        this.intervals = intervals;
    }

    public ICInterval getItc() {
        return itc;
    }

    public ArrayList<ICInterval> getIntervals() {
        return intervals;
    }
}
