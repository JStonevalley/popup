package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class CodeTheft {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numFiles = Integer.parseInt(reader.readLine());
        ArrayList<CodeFile> files = new ArrayList<>();
        for (int i= 0; i < numFiles; i++) {
            CodeFile file = new CodeFile(reader.readLine().replaceAll("\\s+", " "), i);
            String line = null;
            while(!(line = reader.readLine().replaceAll("\\s+", " ").trim()).equals("***END***")){
                if (line.matches("\\s*")){
                    continue;
                }
                file.lines.add(new Line(line));
            }
            files.add(file);
        }
        String line = null;
        int lineNumber = 0;
        while(!(line = reader.readLine().replaceAll("\\s+", " ").trim()).equals("***END***")){
            if (line.matches("\\s*")){
                continue;
            }
            for (CodeFile file : files){
                file.addIfMatches(line, lineNumber);
            }
            lineNumber++;
        }
        Collections.sort(files);
        int max = 0;
        if (files.size() > 0) {
            max = files.get(0).longestSequence;
        }
        System.out.print(max);
        int i = -1;
        if(max != 0) {
            while (++i < files.size() && files.get(i).longestSequence == max) {
                System.out.print(" " + files.get(i).name);
            }
        }
    }

    private static class CodeFile implements Comparable<CodeFile>{
        public ArrayList<Line> lines;
        public String name;
        public final int id;
        public int longestSequence = 0;

        public CodeFile(String name, int id) {
            this.id = id;
            this.name = name;
            this.lines = new ArrayList<>();
        }

        public void addIfMatches(String line, int lineNumber) {
            for (int i = lines.size()-1; i >= 0 ; i--) {
                if (lines.get(i).addIfMatches(line, lineNumber)){
                    if (i-1 >= 0 && lines.get(i-1).contains(lineNumber - 1)){
                        lines.get(i).setSequence(lines.get(i-1).getSequence() + 1);
                    }
                    else{
                        lines.get(i).setSequence(1);
                    }
                    longestSequence = Math.max(longestSequence, lines.get(i).getSequence());
                }
            }
        }

        @Override
        public int compareTo(CodeFile other) {
            if (longestSequence > other.longestSequence){
                return -1;
            }
            else if (longestSequence < other.longestSequence){
                return 1;
            }
            else{
                if (id < other.id){
                    return -1;
                }
                else if (id > other.id){
                    return 1;
                }
                return 0;
            }

        }
    }

    private static class Line{
        private String line;
        private int sequence = 0;
        private HashSet<Integer> matches;
        public Line(String line){
            this.line = line;
            matches = new HashSet<>();
        }

        public boolean addIfMatches(String line, int lineNumber){
            if (this.line.equals(line)){
                matches.add(lineNumber);
                return true;
            }
            return false;
        }

        public boolean contains(int lineNumber){
            return matches.contains(lineNumber);
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }
    }
}
