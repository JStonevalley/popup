package algorithms.intervalcover;

import java.util.ArrayList;

/**
 * Created by jonas on 2015-09-02.
 */
public class ICSolution {
    ICInterval itc;
    private ArrayList<ICInterval> si;
    private ICInterval inc;

    public ICSolution(ICInterval itc) {
        si = new ArrayList<>();
        inc = new ICInterval(itc.getLow(), itc.getHigh());
        this.itc = itc;
    }

    public ArrayList<ICInterval> getSi() {
        return si;
    }

    public ICInterval getInc() {
        return inc;
    }

    public void setInc(ICInterval inc) {
        this.inc = inc;
    }

    public boolean isValid(){
        if (inc.getLow() >= inc.getHigh() && !getSi().isEmpty()){
            return true;
        }
        return false;
    }
}
