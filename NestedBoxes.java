import java.io.*;
import java.util.*;

public class NestedBoxes {

    static class Box implements Comparable<Box> {
        int[] dimensions;

        Box(int l, int w, int h) {
            dimensions = new int[]{l, w, h};
            Arrays.sort(dimensions);
        }

        boolean fitsIn(Box other) {
            for (int i = 0; i < 3; i++) {
                if (this.dimensions[i] >= other.dimensions[i]) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int compareTo(Box other) {
            for (int i = 0; i < 3; i++) {
                if (this.dimensions[i] != other.dimensions[i]) {
                    return this.dimensions[i] - other.dimensions[i];
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return Arrays.toString(dimensions);
        }
    }

    public static void main(String[] args) throws IOException {
        List<Box> boxes = readBoxesFromFile("teste.txt");

        long startTime = System.nanoTime();
        int sortOperations = 0;

        // Counting operations during sorting
        sortOperations += boxes.size() * Math.log(boxes.size()) / Math.log(2); // Approximation for comparison operations in sort

        Collections.sort(boxes);

        int n = boxes.size();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLength = 1;
        int fitOperations = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                fitOperations++;
                if (boxes.get(j).fitsIn(boxes.get(i))) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("A sequência mais longa de caixas aninhadas é: " + maxLength);
        System.out.println("Operações de ordenação (aproximadas): " + sortOperations);
        System.out.println("Operações de encaixe: " + fitOperations);
        System.out.println("Tempo de execução (nanosegundos): " + duration);
    }

    private static List<Box> readBoxesFromFile(String filename) throws IOException {
        List<Box> boxes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            int l = Integer.parseInt(parts[0]);
            int w = Integer.parseInt(parts[1]);
            int h = Integer.parseInt(parts[2]);
            boxes.add(new Box(l, w, h));
        }
        br.close();
        return boxes;
    }
}
