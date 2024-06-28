import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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

class Edge implements Comparable<Edge> {
    int to;
    int weight;

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}


public class BoxSequenceFinderPrim {
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
        int n = boxes.size();
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];
        int[] minWeight = new int[n];
        int operations = 0;
    
        // Initialize minWeight array with max value
        for (int i = 0; i < n; i++) {
            minWeight[i] = Integer.MAX_VALUE;
        }
    
        // Start with the first box
        minWeight[0] = 0;
        parent[0] = -1;
    
        // Priority queue to store the edges
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(0, 0));
    
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int from = edge.to;
    
            if (visited[from]) {
                continue;
            }
    
            visited[from] = true;
    
            for (int to = 0; to < n; to++) {
                operations++;
                if (!visited[to] && from != to && boxes.get(from).canFitInside(boxes.get(to))) {
                    int weight = boxes.get(to).length;
                    if (weight < minWeight[to]) {
                        parent[to] = from;
                        minWeight[to] = weight;
                        pq.offer(new Edge(to, weight));
                    }
                }
            }
        }
    
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            maxLength += minWeight[i];
        }
    
        System.out.println("Operações realizadas: " + operations);
        return maxLength;
    }
    
}
