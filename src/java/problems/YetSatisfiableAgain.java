package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class YetSatisfiableAgain {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        YetSatisfiableAgain ysa = new YetSatisfiableAgain();
        String[] line = reader.readLine().split(" ");
        int numCases = Integer.parseInt(line[0]);
        for (int c = 0; c < numCases; c++) {
            line = reader.readLine().split(" ");
            Variable[] v = new Variable[Integer.parseInt(line[0])];
            for (int i = 0; i < v.length; i++) {
                v[i] = new Variable();
            }
            int numClauses = Integer.parseInt(line[1]);
            ArrayList<Clause> clauses = new ArrayList<>(numClauses);
            for (int i = 0; i < numClauses; i++) {
                line = reader.readLine().split(" ");
                boolean[] neg = new boolean[line.length/2 + 1];
                Variable[] vs = new Variable[line.length/2 + 1];
                for (int j = 0; j < line.length; j+=2) {
                    if (line[j].charAt(0) != '~') {
                        neg[j / 2] = true;
                        vs[j / 2] = v[Integer.parseInt(line[j].substring(1, line[j].length()) + "") - 1];
                    }
                    else{
                        vs[j / 2] = v[Integer.parseInt(line[j].substring(2, line[j].length()) + "") - 1];
                    }
                }
                clauses.add(new Clause(neg, vs));
            }
            boolean satisfiable = ysa.isSatisfiable(clauses, v, 0);
            if (satisfiable)
                System.out.println("satisfiable");
            else
                System.out.println("unsatisfiable");
        }
    }

    public boolean isSatisfiable(ArrayList<Clause> clauses, Variable[] variables, int start){
        ArrayList<Clause> unsatisfied;
        if (clauses.size() == 0)
            return true;
        for (int i = start; i < variables.length; i++){
            variables[i].state = 0;
            unsatisfied = getUnsatisfiedClauses(clauses);
            if (unsatisfied != null && isSatisfiable(unsatisfied, variables, start + 1)){
                return true;
            }
            variables[i].state = 1;
            unsatisfied = getUnsatisfiedClauses(clauses);
            if (unsatisfied != null  && isSatisfiable(unsatisfied, variables, start + 1)){
                return true;
            }
            variables[i].state = -1;
            return false;
        }
        return false;
    }

    public ArrayList<Clause> getUnsatisfiedClauses(ArrayList<Clause> clauses){
        ArrayList<Clause> ucs = new ArrayList<>();
        for (Clause c : clauses){
            short satisfied = c.isSatisfied();
            if (satisfied == 0)
                ucs.add(c);
            else if (satisfied == -1)
                return null;
        }
        return ucs;
    }

    public static class Clause implements Comparable<Clause>{
        private boolean[] neg;
        public Variable[] vs;

        public Clause(boolean[] neg, Variable[] vs) {
            if (neg.length != vs.length)
                throw new IllegalArgumentException();
            this.neg = neg;
            this.vs = vs;
        }

        public short isSatisfied(){
            boolean allSet = true;
            for (int i = 0; i < vs.length; i++) {
                if ((neg[i] && vs[i].state == 1) || !neg[i] && vs[i].state == 0)
                    return 1;
                if (vs[i].state == -1)
                    allSet = false;
            }
            if (allSet)
                return -1;
            return 0;
        }

        @Override
        public int compareTo(Clause o) {
            if (vs.length < o.vs.length)
                return 1;
            if (vs.length > o.vs.length)
                return -1;
            return 0;
        }
    }

    public static class Variable {
        public short state = -1;
    }
}
