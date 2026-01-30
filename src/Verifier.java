import java.io.*;
import java.util.*;

public class Verifier {
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Verifier <input_file> <output_file>");
            return;
        }
        
        try {
            List<String> inputLines = readFile(args[0]);
            List<String> outputLines = readFile(args[1]);
            
            int n = Integer.parseInt(inputLines.get(0).trim());
            
            if (n == 0) {
                System.out.println("VALID STABLE");
                return;
            }
            
            int[][] hospitalPrefs = new int[n][n];
            int[][] studentPrefs = new int[n][n];
            
            for (int i = 0; i < n; i++) {
                String[] parts = inputLines.get(i + 1).trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    hospitalPrefs[i][j] = Integer.parseInt(parts[j]) - 1;
                }
            }
            
            for (int i = 0; i < n; i++) {
                String[] parts = inputLines.get(i + n + 1).trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    studentPrefs[i][j] = Integer.parseInt(parts[j]) - 1;
                }
            }
            
            int[] hospitalMatch = new int[n];
            int[] studentMatch = new int[n];
            Arrays.fill(studentMatch, -1);
            
            for (String line : outputLines) {
                String[] parts = line.trim().split("\\s+");
                int h = Integer.parseInt(parts[0]) - 1;
                int s = Integer.parseInt(parts[1]) - 1;
                hospitalMatch[h] = s;
                
                if (studentMatch[s] != -1) {
                    System.out.println("INVALID: Student " + (s + 1) + " matched multiple times");
                    return;
                }
                studentMatch[s] = h;
            }
            
            for (int i = 0; i < n; i++) {
                if (studentMatch[i] == -1) {
                    System.out.println("INVALID: Student " + (i + 1) + " not matched");
                    return;
                }
            }
            
            for (int h = 0; h < n; h++) {
                int s = hospitalMatch[h];
                
                int hRankOfS = -1;
                for (int j = 0; j < n; j++) {
                    if (hospitalPrefs[h][j] == s) {
                        hRankOfS = j;
                        break;
                    }
                }
                
                for (int j = 0; j < hRankOfS; j++) {
                    int sPrime = hospitalPrefs[h][j];
                    int hPrime = studentMatch[sPrime];
                    
                    int sPrimeRankOfH = -1;
                    int sPrimeRankOfHPrime = -1;
                    for (int k = 0; k < n; k++) {
                        if (studentPrefs[sPrime][k] == h) {
                            sPrimeRankOfH = k;
                        }
                        if (studentPrefs[sPrime][k] == hPrime) {
                            sPrimeRankOfHPrime = k;
                        }
                    }
                    
                    if (sPrimeRankOfH < sPrimeRankOfHPrime) {
                        System.out.println("UNSTABLE: Hospital " + (h + 1) + " and Student " + (sPrime + 1) + " form blocking pair");
                        return;
                    }
                }
            }
            
            System.out.println("VALID STABLE");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
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
