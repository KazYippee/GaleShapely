import java.io.*;
import java.util.*;

public class Matcher {
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Matcher <input_file>");
            return;
        }
        
        try {
            List<String> lines = readFile(args[0]);
            int n = Integer.parseInt(lines.get(0).trim());
            
            if (n == 0) {
                return;
            }
            
            int[][] hospitalPrefs = new int[n][n];
            int[][] studentPrefs = new int[n][n];
            
            for (int i = 0; i < n; i++) {
                String[] parts = lines.get(i + 1).trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    hospitalPrefs[i][j] = Integer.parseInt(parts[j]) - 1;
                }
            }
            
            for (int i = 0; i < n; i++) {
                String[] parts = lines.get(i + n + 1).trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    studentPrefs[i][j] = Integer.parseInt(parts[j]) - 1;
                }
            }
            
            int[][] studentRank = new int[n][n];
            for (int s = 0; s < n; s++) {
                for (int j = 0; j < n; j++) {
                    studentRank[s][studentPrefs[s][j]] = j;
                }
            }
            
            int[] matching = galeShapley(n, hospitalPrefs, studentRank);
            
            for (int i = 0; i < n; i++) {
                System.out.println((i + 1) + " " + (matching[i] + 1));
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static int[] galeShapley(int n, int[][] hospitalPrefs, int[][] studentRank) {
        int[] hospitalMatch = new int[n];
        int[] studentMatch = new int[n];
        int[] nextProposal = new int[n];
        
        Arrays.fill(hospitalMatch, -1);
        Arrays.fill(studentMatch, -1);
        
        Queue<Integer> freeHospitals = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            freeHospitals.add(i);
        }
        
        while (!freeHospitals.isEmpty()) {
            int h = freeHospitals.poll();
            
            if (nextProposal[h] < n) {
                int s = hospitalPrefs[h][nextProposal[h]];
                nextProposal[h]++;
                
                if (studentMatch[s] == -1) {
                    hospitalMatch[h] = s;
                    studentMatch[s] = h;
                } else {
                    int currentH = studentMatch[s];
                    if (studentRank[s][h] < studentRank[s][currentH]) {
                        hospitalMatch[currentH] = -1;
                        hospitalMatch[h] = s;
                        studentMatch[s] = h;
                        freeHospitals.add(currentH);
                    } else {
                        freeHospitals.add(h);
                    }
                }
            }
        }
        
        return hospitalMatch;
    }
    
    private static List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                lines.add(line);
            }
        }
        br.close();
        return lines;
    }
}
