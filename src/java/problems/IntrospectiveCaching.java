package problems;

import utilities.Kattio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class IntrospectiveCaching {

    private int cacheSize;
    private DataObject[] dataObjects;
    private int[] accesses;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int cacheSize = io.getInt();
        int numObjects = io.getInt();
        int accesses = io.getInt();
        IntrospectiveCaching ic = new IntrospectiveCaching(io, cacheSize, numObjects, accesses);
        System.out.println(ic.getMinNumberNetworkAccess());
    }

    public IntrospectiveCaching(Kattio io, int cacheSize, int numObjects, int accesses){
        this.cacheSize = cacheSize;
        dataObjects = new DataObject[numObjects];
        this.accesses = new int[accesses];
        int objectNumber;
        for (int i = 0; i < accesses; i++) {
            objectNumber = io.getInt();
            if (dataObjects[objectNumber] == null){
                dataObjects[objectNumber] = new DataObject(i, objectNumber);
            }
            else{
                dataObjects[objectNumber].addAccess(i);
            }
            this.accesses[i] = objectNumber;
        }
    }

    private int getMinNumberNetworkAccess(){
        int netWorkAccesses = 0;
        HashSet<Integer> cache = new HashSet<>(cacheSize);
        PriorityQueue<NextAccess> priority = new PriorityQueue<>();
        for (int i = 0; i < accesses.length; i++) {
            dataObjects[accesses[i]].access();
            if (!cache.contains(accesses[i])){
                if (cache.size() < cacheSize){
                    cache.add(accesses[i]);
                    priority.add(dataObjects[accesses[i]].getNextAccess());
                }
                else{
                    cache.remove(priority.poll().getIndex());
                    priority.add(dataObjects[accesses[i]].getNextAccess());
                    cache.add(accesses[i]);
                }
                netWorkAccesses++;
            }
            else {
                priority.add(dataObjects[accesses[i]].getNextAccess());
            }
        }
        return netWorkAccesses;
    }

    private class DataObject implements Comparable<DataObject>{
        private int index;
        private ArrayList<Integer> access;
        private int lastAccess;
        private int nextAccessValue;
        public DataObject(int access, int index){
            this.access = new ArrayList<>();
            this.access.add(access);
            this.lastAccess = -1;
            this.nextAccessValue = this.access.get(lastAccess + 1);
            this.index = index;
        }
        public NextAccess getNextAccess(){
            return new NextAccess(index, nextAccessValue);
        }
        public void access(){
            lastAccess++;
            if (lastAccess == access.size()-1){
                nextAccessValue = Integer.MAX_VALUE;
            }
            else{
                nextAccessValue = access.get(lastAccess + 1);
            }
        }
        public void addAccess(int access){
            this.access.add(access);
        }

        public int getIndex() {
            return index;
        }

        @Override
        public int compareTo(DataObject o) {
            if(o.nextAccessValue > nextAccessValue){
                return 1;
            }
            else if(o.nextAccessValue < nextAccessValue){
                return -1;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataObject that = (DataObject) o;

            if (index != that.index) return false;
            if (lastAccess != that.lastAccess) return false;
            if (nextAccessValue != that.nextAccessValue) return false;
            return access.equals(that.access);

        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + access.hashCode();
            result = 31 * result + lastAccess;
            result = 31 * result + nextAccessValue;
            return result;
        }
    }

    private class NextAccess implements Comparable<NextAccess>{
        private int index;
        private int nextAccess;

        public NextAccess(int index, int nextAccess) {
            this.index = index;
            this.nextAccess = nextAccess;
        }

        public int getIndex() {
            return index;
        }

        public int getNextAccess() {
            return nextAccess;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NextAccess that = (NextAccess) o;

            return index == that.index;

        }

        @Override
        public int hashCode() {
            return index;
        }

        @Override
        public int compareTo(NextAccess o) {
            if(o.nextAccess > nextAccess){
                return 1;
            }
            else if(o.nextAccess < nextAccess){
                return -1;
            }
            return 0;
        }
    }
}
