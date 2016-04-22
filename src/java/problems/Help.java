package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Help {

    public static void main(String [] args) throws IOException, IllegalArgumentException {
        Help help = new Help();
        help.help();
    }

    private boolean setMaps(HashMap<String, Index> pP, HashMap<String, Index> pS, String key, String value){
        if (pP.get(key).hasValue() && !pP.get(key).getWord().equals(value) ) {
            return false;
        }
        ArrayList<String> pPList = pP.get(key).getPlaceholders();
        while (!pP.get(key).getPlaceholders().isEmpty())
        {
            String placeholder = pP.get(key).getPlaceholders().remove(0);
            ArrayList<String> pSList = pS.get(placeholder).getPlaceholders();
            int index = 0;
            while (index < pSList.size()) {
                if (key.equals(pSList.get(index))){
                    pSList.remove(index);
                    break;
                }
                index++;
            }

            if (pSList.size() == 0) {
                pS.get(placeholder).setWord(value);
            }
            else if (pS.get(placeholder).hasValue() && !pS.get(placeholder).getWord().equals(value)) {
                return false;
            }
            else if (pSList.size() > 0) {
                setMaps(pS, pP, placeholder, value);
            }
        }
        pP.get(key).clearPlaceholders();
        pP.get(key).setWord(value);
        return true;
    }

    public void help() throws IOException, IllegalArgumentException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String numCasesLine;
        numCasesLine = reader.readLine();
        int numCases;
        numCases = Integer.parseInt(numCasesLine);

        for (int i = 0; i < numCases; i++) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            String[] tokens1 = line1.split(" ");
            String[] tokens2 = line2.split(" ");
            HashMap<String, Index> placeholders1 = new HashMap<>();
            HashMap<String, Index> placeholders2 = new HashMap<>();
            ArrayList<String> solution1 =new ArrayList<>();
            ArrayList<String> solution2 = new ArrayList<>();
            String token1, token2;
            if (tokens1.length != tokens2.length){
                System.out.println('-');
                continue;
            }
            boolean valid = true;
            for (int j = 0; j < tokens1.length; j++) {
                token1 = tokens1[j];
                token2 = tokens2[j];
                if (token1.charAt(0) == '<' && token2.charAt(0) != '<') {
                    if (!placeholders1.containsKey(token1)) {
                        placeholders1.put(token1, new Index(token2));
                    }
                    else {
                        valid = setMaps(placeholders1, placeholders2, token1, token2);
                    }
                    if (valid) {
                        solution1.add(token2);
                        solution2.add(token2);
                    }
                    else {
                        break;
                    }
                }
                else if (token2.charAt(0) == '<' && token1.charAt(0) != '<') {
                    if (!placeholders2.containsKey(token2)) {
                        placeholders2.put(token2, new Index(token1));
                    }
                    else {
                        valid = setMaps(placeholders2, placeholders1, token2, token1);
                    }
                    if (valid) {
                        solution1.add(token1);
                        solution2.add(token1);
                    }
                    else {
                        break;
                    }
                }
                else if (token1.charAt(0) == '<' && token2.charAt(0) == '<') {
                    if (!placeholders1.containsKey(token1)){
                        if (token2.charAt(0) == '<') {
                            placeholders1.put(token1, new Index());
                            placeholders1.get(token1).addPlaceholder(token2);
                        }
                        else{
                            placeholders1.put(token1, new Index(token2));
                        }
                    }
                    if (!placeholders2.containsKey(token2)){
                        if (token1.charAt(0) == '<') {
                            placeholders2.put(token2, new Index());
                            placeholders2.get(token2).addPlaceholder(token1);
                        }
                        else{
                            placeholders2.put(token2, new Index(token1));
                        }
                    }
                    if ((!placeholders1.get(token1).hasValue()) && (!placeholders2.get(token2).hasValue())) {
                        if (!placeholders1.get(token1).containsPlaceholder(token2)) {
                            placeholders1.get(token1).addPlaceholder(token2);
                        }
                        if (!placeholders2.get(token2).containsPlaceholder(token2)) {
                            placeholders2.get(token2).addPlaceholder(token1);
                        }
                    }
                    else if (placeholders1.get(token1).hasValue() && placeholders2.get(token2).hasValue()) {
                        if (!(placeholders1.get(token1).getWord().equals(placeholders2.get(token2).getWord()))) {
                            valid = false;
                            break;
                        }
                    }
                    else if (placeholders1.get(token1).hasValue()) {
                        valid = setMaps(placeholders2, placeholders1, token2, placeholders1.get(token1).getWord());
                        if (!valid) {
                            break;
                        }
                    }
                    else if (placeholders2.get(token2).hasValue()) {
                        valid = setMaps(placeholders1, placeholders2, token1, placeholders2.get(token2).getWord());
                        if (!valid) {
                            break;
                        }
                    }
                    solution1.add(token1);
                    solution2.add(token2);
                }
                else {
                    solution1.add(token1);
                    solution2.add(token2);
                }
            }
            if (valid) {
                for (int k = 0; k < solution1.size(); k++) {
                    if (solution1.get(k).charAt(0) == '<') {
                        if (!placeholders1.get(solution1.get(k)).hasValue()) {
                            solution1.set(k, "text");
                        }
                        else{
                            solution1.set(k, placeholders1.get(solution1.get(k)).getWord());
                        }
                    }
                    if (solution2.get(k).charAt(0) == '<') {
                        if (!placeholders2.get(solution2.get(k)).hasValue()) {
                            solution2.set(k, "text");
                        }
                        else{
                            solution2.set(k, placeholders2.get(solution2.get(k)).getWord());
                        }
                    }
                    if (!solution1.get(k).equals(solution2.get(k))) {
                        System.out.print('-');
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    for (int k = 0; k < solution1.size(); k++) {
                        System.out.print(solution1.get(k) + ' ');
                    }
                }
            } else {
                System.out.print('-');
            }
            System.out.println();
        }
    }
    private class Index{
        private String word;
        private ArrayList<String> placeholders;

        public Index(String word){
            this.word = word;
            this.placeholders = new ArrayList<>();
        }

        public Index(){
            this.placeholders = new ArrayList<>();
        }

        public boolean hasValue(){
            if (word != null){
                return true;
            }
            return false;
        }

        public String getWord(){
            return word;
        }

        public void setWord(String word){
            this.word = word;
        }

        public void addPlaceholder(String placeholder){
            placeholders.add(placeholder);
        }

        public ArrayList<String> getPlaceholders(){
            return placeholders;
        }

        public void clearPlaceholders() {
            placeholders.clear();
        }

        public boolean containsPlaceholder(String placeholder){
            return placeholders.contains(placeholder);
        }
    }
}
