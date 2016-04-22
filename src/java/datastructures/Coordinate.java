package datastructures;

public class Coordinate<T extends Comparable<T>> {
    public T x, y;

    public Coordinate(T x, T y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate<?> that = (Coordinate<?>) o;

        if (!x.equals(that.x)) return false;
        return y.equals(that.y);

    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
