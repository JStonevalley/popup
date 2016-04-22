package inputgeneration;

import algorithms.EratosthenesSieve;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestGenerator {
    public static void main(String[] args) throws IOException {
        TestGenerator generator = new TestGenerator();
        //generator.generateYSATest(5, 8, 5, 10);
        //generator.generateClass(1000);
        //generator.generateInterval(0, 100000);
        generator.generateCoordinates(20, 1000);
    }

    public void generateYSATest(int numVariables, int numClauses, int maxRowLength, int numTestCases) throws IOException {
        FileWriter writer = new FileWriter("src\\resources\\YetSatisfiableAgain.in");
        writer.write(numTestCases + "\n");
        for (int c = 0; c < numTestCases; c++) {
            writer.write(numVariables + " " + numClauses + "\n");
            Random random = new Random();
            int numV;
            for (int i = 0; i < numClauses; i++) {
                numV = random.nextInt(maxRowLength - 1) + 1;
                for (int j = 0; j < numV; j++) {
                    if (random.nextInt(2) == 1)
                        writer.write("X" + (random.nextInt(numVariables) + 1));
                    else
                        writer.write("~X" + (random.nextInt(numVariables) + 1));
                    if (j < numV-1)
                        writer.write(" v ");
                }
                writer.write("\n");
            }
        }
        writer.close();
    }

    public void generateClass(int numEnemies) throws IOException {
        String[] people = new String[] {"Abraham", "Adam", "Niklas", "Jonas", "Erik", "Sandra", "Johan", "Andreas", "Linda", "Julia", "Maja", "Linnea", "Patrick", "Bob", "Graham", "Fernando", "Jenson", "Lewis", "Kimi", "Romain", "Max", "Nico", "Gunlog", "Zlatan", "Matilda", "Elin", "Jan", "Lena", "Magnus", "Stina"};
        FileWriter writer = new FileWriter("src\\resources\\class.in");
        for (int i = 0; i < 20; i++) {
            writer.write(people.length + "\n");
            for (int j = 0; j < people.length; j++) {
                writer.write(people[j] + "\n");
            }
            writer.write(numEnemies + "\n");
            Random random = new Random();
            for (int j = 0; j < numEnemies; j++) {
                writer.write(people[random.nextInt(people.length)] + " " + people[random.nextInt(people.length)] + "\n");
            }
            writer.flush();
        }
        writer.close();
    }

    public void generateFactovisors(int limit, int cardinality) throws IOException {
        FileWriter writer = new FileWriter("src\\resources\\factovisors.in");
        Random random = new Random();
        for (int i = 0; i < cardinality; i++) {
            writer.write((random.nextInt(limit) + 1) + " " + (random.nextInt(limit) + 1) + "\n");
        }
        writer.close();
    }

    public void generatePrimeBase(int cardinality) throws IOException {
        FileWriter writer = new FileWriter("src\\resources\\factovisorsBase.in");
        EratosthenesSieve s = new EratosthenesSieve((int) Math.sqrt(Integer.MAX_VALUE));
        Random random = new Random();
        for (int i = 0; i < cardinality; i++) {
            int factor = random.nextInt((int) Math.sqrt(Integer.MAX_VALUE));
            if (s.isPrime(factor))
                writer.write(factor + " " + (random.nextInt(Integer.MAX_VALUE - 1)) + "\n");
        }
        writer.close();
    }

    public void generateInterval(int base,int limit) throws IOException {
        FileWriter writer = new FileWriter("src\\resources\\zeroes.in");
        for (int i = base; i < limit; i++) {
            writer.write(base + " " + i + "\n");
        }
        writer.write("-1 -1");
        writer.close();
    }

    public void generateCoordinates(int max, int numCases) throws IOException {
        FileWriter writer = new FileWriter("src\\resources\\tractor.in");
        writer.write(numCases + "\n");
        Random r = new Random();
        for (int i = 0; i < numCases; i++) {
            writer.write(r.nextInt(max) + " " + r.nextInt(max) + "\n");
        }
        writer.close();
    }
}
