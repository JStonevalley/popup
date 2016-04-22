package algorithms;

import utilities.Kattio;

import java.util.ArrayList;
import java.util.Collections;

public class LongestIncreasingSubSequence<T extends Comparable<T>> {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        while(io.hasMoreTokens()) {
            int seqLength = io.getInt();
            if (seqLength == 0){
                System.out.println(0);
                continue;
            }
            int high = 1;
            Integer[] elements = new Integer[seqLength];
            int l;
            for (int i = 0; i < seqLength; i++) {
                elements[i] = io.getInt();
            }
            LongestIncreasingSubSequence<Integer> liss = new LongestIncreasingSubSequence<Integer>();
            ArrayList<Integer> indices = liss.getLongestIncreasingSubSequence(elements);
            StringBuilder sb = new StringBuilder();
            for (int i = indices.size() - 1; i >= 0; i--) {
                sb.append(indices.get(i));
                sb.append(" ");
            }
            System.out.println(indices.size());
            System.out.println(sb.toString());
        }
    }

    /**
     * @param elems the elements to find sub sequences in
     * @return th indices of the longest increasing sub sequence backwards
     */
    public ArrayList<Integer> getLongestIncreasingSubSequence(T[] elems) {

        Element<T>[] elements = new Element[elems.length];
        for (int i = 0; i < elems.length; i++) {
            elements[i] = new Element<T>(i, elems[i]);
        }

        ArrayList<Element<T>> last = new ArrayList<Element<T>>(elements.length + 2);
        last.add(new Element<T>(-1, null));

        for (int i = 0; i < elements.length; i++) {
            //Find maximum l such that last[l] < element.value
            int l;
            l = Collections.binarySearch(last, elements[i]);
            if (l < 0){
                l = Math.abs(l + 1);
                //The element is larger than anything we have seen
                if (l == last.size()){
                    last.add(new Element<T>(elements[i].index, elements[i].value));
                    elements[i].previous = last.get(l-1).index;
                }
                //The element is larger than [l-1] and smaller than last[l] replace last[l]
                else if (l < last.size())
                    last.get(l).value = elements[i].value;
                    last.get(l).index = elements[i].index;
                    elements[i].previous = last.get(l-1).index;
            }
        }
        // Backtrack solution to find the indices of the longest sequence.
        int maxIndex = last.get(last.size()-1).index;
        ArrayList<Integer> indices = new ArrayList<>();
        Element temp = elements[maxIndex];
        indices.add(temp.index);
        while (temp.previous > -1) {
            temp = elements[temp.previous];
            indices.add(temp.index);
        }
        return indices;
    }

    /**
     * A container for values for which we would like to find a subsequence.
     * Keeps index and previous element-index in order to be able to backtrack.
     * @param <T> Camparable type.
     */
    private class Element<T extends Comparable<T>> implements Comparable<Element<T>>{
        public int index;
        public T value;
        public int previous = -1;

        public Element(int index, T value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Element<T> o) {
            if (o.value == null && this.value == null)
                return 0;
            if (o.value == null)
                return 1;
            if (this.value == null)
                return -1;
            return this.value.compareTo(o.value);
        }
    }
}
