import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Box {
    int width;
    int height;
    int length;

    public Box(int width, int height, int length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public boolean canFitInside(Box other) {
        return this.width < other.width && this.height < other.height && this.length < other.length;
    }
}

class Edge {
    int from;
    int to;
    int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class UnionFind {
    int[] parent;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public void union(int x, int y) {
        parent[find(x)] = find(y);
    }
}

public class BoxSequenceFinder {
    public static void main(String[] args) {
        List<Box> boxes = readBoxesFromFile("teste.txt");

        long startTime = System.nanoTime();
        int maxLengthSequence = findMaxLengthSequence(boxes);
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        System.out.println("Comprimento da maior sequência de caixas: " + maxLengthSequence);
        System.out.println("Tempo de execução: " + executionTime + " nanossegundos");
    }

    public static List<Box> readBoxesFromFile(String filename) {
        List<Box> boxes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dimensions = line.split(" ");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                int length = Integer.parseInt(dimensions[2]);
                boxes.add(new Box(width, height, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boxes;
    }

    public static int findMaxLengthSequence(List<Box> boxes) {
        List<Edge> edges = new ArrayList<>();
        int n = boxes.size();
        int operations = 0;

        // Create edges between boxes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                operations++;
                if (i != j && boxes.get(i).canFitInside(boxes.get(j))) {
                    int weight = boxes.get(j).length; // Weight of the edge represents length of the box j
                    edges.add(new Edge(i, j, weight));
                }
            }
        }

        // Sort edges by weight
        Collections.sort(edges, Comparator.comparingInt(edge -> edge.weight));

        // Kruskal's algorithm
        UnionFind uf = new UnionFind(n);
        int maxLength = 0;
        for (Edge edge : edges) {
            operations++;
            if (uf.find(edge.from) != uf.find(edge.to)) {
                uf.union(edge.from, edge.to);
                maxLength += edge.weight;
            }
        }

        System.out.println("Operações realizadas: " + operations);
        return maxLength;
    }
}
