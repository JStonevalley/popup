package algorithms.intervalcover;

/**
 * Created by jonas on 2015-09-02.
 */
public class ICInterval implements Comparable<ICInterval> {
    private double low;
    private double high;
    private int index;

    public ICInterval(double low, double high) {
        this.low = low;
        this.high = high;
        this.index = -1;
    }

    public ICInterval(double low, double high, int index) {
        this.low = low;
        this.high = high;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public double getLow() {
        return low;
    }

    public double getHigh() {
        return high;
    }

    @Override
    public int compareTo(ICInterval o) {
        if(this.getLow() < o.getLow()){
            return 1;
        }
        else if(this.getLow() > o.getLow()){
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ICInterval interval = (ICInterval) o;

        if (Double.compare(interval.low, low) != 0) return false;
        return Double.compare(interval.high, high) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(low);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(high);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
