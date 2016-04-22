package problems;

import utilities.Kattio;

import java.util.*;

public class ClassPicture {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);
        while(io.hasMoreTokens()) {
            int numPeople = io.getInt();
            ArrayList<Person> people = new ArrayList<>();
            for (int i = 0; i < numPeople; i++) {
                people.add(new Person(io.getWord()));
            }
            Collections.sort(people);
            HashMap<String, Integer> ids = new HashMap<>(numPeople);
            for (int i = 0; i < numPeople; i++) {
                people.get(i).id = i;
                ids.put(people.get(i).name, i);
            }

            int numEnemies = io.getInt();
            for (int i = 0; i < numEnemies; i++) {
                int a = ids.get(io.getWord());
                int b = ids.get(io.getWord());
                people.get(a).enemies.add(b);
                people.get(b).enemies.add(a);
            }
            ClassPicture picture = new ClassPicture();
            LinkedList<Person> order = picture.suggestOrder(new LinkedList<Person>(), people);
            if (order == null) {
                System.out.println("You all need therapy.");
            } else {
                for (Person p : order) {
                    System.out.print(p.name + " ");
                }
                System.out.println();
            }
        }
    }

    public LinkedList<Person> suggestOrder(LinkedList<Person> order, ArrayList<Person> people) {
        if (people.isEmpty()){
            return order;
        }
        LinkedList<Person> returnedOrder = null;
        for (int i = 0; i < people.size(); i++){
            if (order.isEmpty() || !order.getLast().enemies.contains(people.get(i).id)){
                order.addLast(people.get(i));
                people.remove(i);
                if ((returnedOrder = suggestOrder(order, people)) != null){
                    return returnedOrder;
                }
                else {
                    people.add(i, order.removeLast());
                }
            }
        }
        return null;
    }

    private static class Person implements Comparable<Person>{
        public int id;
        public String name;
        public HashSet<Integer> enemies;

        public Person(String name) {
            this.name = name;
            this.id = -1;
            this.enemies = new HashSet<>();
        }


        @Override
        public int compareTo(Person o) {
            if (this.id == -1 || o.id == -1) {
                return this.name.compareTo(o.name);
            }
            else{
                if (this.id > o.id){
                    return 1;
                }
                if (this.id < o.id){
                    return -1;
                }
                return 0;
            }
        }
    }
}
