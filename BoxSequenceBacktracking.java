import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoxSequenceBacktracking {
    static int maxLength;

    public static void main(String[] args) {
        List<Box> boxes = readBoxesFromFile("teste.txt");
        maxLength = 0;
        findMaxLengthSequence(boxes, new ArrayList<>(), 0);
        System.out.println("Comprimento da maior sequência de caixas: " + maxLength);
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

    public static void findMaxLengthSequence(List<Box> boxes, List<Box> currentSequence, int index) {
        if (index == boxes.size()) {
            maxLength = Math.max(maxLength, currentSequence.size());
            return;
        }

        // Try adding the current box to the sequence
        boolean added = false;
        if (canAddToSequence(currentSequence, boxes.get(index))) {
            currentSequence.add(boxes.get(index));
            findMaxLengthSequence(boxes, currentSequence, index + 1);
            currentSequence.remove(currentSequence.size() - 1); // Backtrack
            added = true;
        }

        // Skip the current box
        findMaxLengthSequence(boxes, currentSequence, index + 1);

        // If the current box was not added, try skipping it again to avoid duplicate combinations
        if (!added) {
            findMaxLengthSequence(boxes, currentSequence, index + 1);
        }
    }

    public static boolean canAddToSequence(List<Box> sequence, Box box) {
        for (Box existingBox : sequence) {
            if (!existingBox.canFitInside(box)) {
                return false;
            }
        }
        return true;
    }

    static class Box {
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
}
