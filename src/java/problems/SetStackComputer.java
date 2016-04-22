package problems;

import utilities.Kattio;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class SetStackComputer {

    Stack<HashSet<Integer>> stack = new Stack<>();
    HashMap<HashSet<Integer>, Integer> setHash = new HashMap<>();
    int index = 0;


    public static void main(String [] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);
        long testSets = io.getInt();
        SetStackComputer setStackComputer = new SetStackComputer();
        for (int i = 0; i < testSets; i++) {
            int numOperations = io.getInt();
            for (int j = 0; j < numOperations; j++) {
                String operation = io.getWord();
                io.println(setStackComputer.performOperation(operation));
            }
            setStackComputer.clearStack();
            io.println("***");
        }
        io.flush();
        io.close();
    }

    public SetStackComputer() {
        addSetIfNew(new HashSet<>());
    }

    public void clearStack(){
        stack.clear();
    }

    private void addSetIfNew(HashSet<Integer> set) {
        if (!setHash.containsKey(set)) {
            setHash.put(set, index++);
        }
    }

    public int performOperation(String operation){
        if (Operation.PUSH.equalsOp(operation)){
            stack.push(new HashSet<>());
        }
        else if(Operation.ADD.equalsOp(operation)){
            HashSet<Integer> first = stack.pop();
            HashSet<Integer> second = stack.pop();
            int firstId = setHash.get(first);
            if (second.contains(firstId)){
                stack.push(second);
            }
            else{
                HashSet<Integer> candidate = new HashSet<>(second);
                candidate.add(firstId);
                addSetIfNew(candidate);
                stack.push(candidate);
            }
        }
        else if(Operation.DUP.equalsOp(operation)) {
            HashSet<Integer> first = stack.pop();
            stack.push(first);
            stack.push(first);
        }
        else if(Operation.UNION.equalsOp(operation)){
            HashSet<Integer> first = stack.pop();
            HashSet<Integer> second = stack.pop();
            HashSet<Integer> candidate = new HashSet<>(first);
            candidate.addAll(second);
            addSetIfNew(candidate);
            stack.push(candidate);
        }
        else if(Operation.INTERSECT.equalsOp(operation)){
            HashSet<Integer> first = stack.pop();
            HashSet<Integer> second = stack.pop();
            HashSet<Integer> candidate = new HashSet<>(first);
            candidate.retainAll(second);
            addSetIfNew(candidate);
            stack.push(candidate);
        }
        return stack.peek().size();
    }

    private enum Operation{
        PUSH ("PUSH"),
        DUP ("DUP"),
        UNION ("UNION"),
        INTERSECT ("INTERSECT"),
        ADD ("ADD");

        private final String name;

        private Operation(String s) {
            name = s;
        }

        public boolean equalsOp(String s) {
            return (s != null) && name.equals(s);
        }
    }
}
