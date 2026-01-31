import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GaleShapleyTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private void writeStringToFile(String path, String content) throws IOException {
        try (PrintWriter out = new PrintWriter(path)) {
            out.print(content);
        }
    }

    @Before
    public void setUpStreams() {
        //we need to capture the output from Matcher/Verifier main methods
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

 
    @Test
    public void testExampleProvided() throws Exception {
        //this tests the 'example.in' file provided
        //it validates that our Matcher produces a result that our Verifier calls "VALID STABLE"
        
        String inputPath = "data/example.in";
        String outputPath = "tests/temp_example.out";

        Matcher.main(new String[]{inputPath});
        String matcherOutput = outContent.toString();
        
        writeStringToFile(outputPath, matcherOutput);

        //clear stream for the next runn
        outContent.reset(); 

        Verifier.main(new String[]{inputPath, outputPath});
        String verifierOutput = outContent.toString();

        assertTrue("Matcher should produce some output", matcherOutput.length() > 0);
        assertTrue("Verifier should verify the example as VALID STABLE", 
                   verifierOutput.contains("VALID STABLE"));
        
        new File(outputPath).delete();
    }



    @Test
    public void testEdgeCaseOneHospitalOneStudent() throws Exception {
       
        String content = "1\n1\n1";
        String inputPath = "tests/n1_test.in";
        String outputPath = "tests/n1_test.out";
        
        writeStringToFile(inputPath, content);

        Matcher.main(new String[]{inputPath});
        String matcherOutput = outContent.toString();
        writeStringToFile(outputPath, matcherOutput);
        
        outContent.reset();

        Verifier.main(new String[]{inputPath, outputPath});
        String verifierOutput = outContent.toString();

        //the match should obviously be "1 1"
        assertTrue("Output should contain match 1 1", matcherOutput.contains("1 1"));
        assertTrue("Verifier should accept n=1 case", verifierOutput.contains("VALID STABLE"));

        new File(inputPath).delete();
        new File(outputPath).delete();
    }

    @Test
    public void testEdgeCaseZeroN() throws Exception {
        //if n=0, the program shouldn't crash
        String content = "0";
        String inputPath = "tests/n0_test.in";
        String outputPath = "tests/n0_test.out";

        writeStringToFile(inputPath, content);

        try {
            Matcher.main(new String[]{inputPath});
        } catch (Exception e) {
            fail("Matcher crashed on n=0 input: " + e.getMessage());
        }
        
        String matcherOutput = outContent.toString();
        writeStringToFile(outputPath, matcherOutput);
        outContent.reset();

        Verifier.main(new String[]{inputPath, outputPath});
        String verifierOutput = outContent.toString();

        assertTrue("Should handle n=0 gracefully", 
            verifierOutput.contains("VALID STABLE") || verifierOutput.trim().isEmpty());

        new File(inputPath).delete();
        new File(outputPath).delete();
    }

    @Test
    public void testVerifierDetectsInvalidDuplicateMatch() throws Exception {
        // here we fake a bad output where Student 2 is matched twice
        // Input: n=2
        // H1: 1 2, H2: 2 1
        // S1: 1 2, S2: 2 1
        String inputContent = "2\n1 2\n2 1\n1 2\n2 1";
        String badMatchContent = "1 2\n2 2"; // Both hospitals matched to student 2

        String inputPath = "tests/invalid_logic.in";
        String outputPath = "tests/invalid_logic.out";
        
        writeStringToFile(inputPath, inputContent);
        writeStringToFile(outputPath, badMatchContent);

        Verifier.main(new String[]{inputPath, outputPath});
        String output = outContent.toString();

        assertFalse("Should NOT be valid", output.contains("VALID STABLE"));
        assertTrue("Should detect invalid matching", 
            output.toUpperCase().contains("INVALID") || output.contains("matched multiple times"));
            
        new File(inputPath).delete();
        new File(outputPath).delete();
    }

    @Test
    public void testVerifierDetectsUnstableBlockingPair() throws Exception {
    // Scenario: Blocking Pair (H1, S1).
    //H1 and S1 rank each other first but are forcefully matched to others (S2 and H2)
    //since they prefer each other over their current partners, the match is unstable
        //inputs: H1:1 2, H2:2 1, S1:1 2, S2:1 2
        String inputContent = "2\n1 2\n2 1\n1 2\n1 2"; 
        
        String badMatchContent = "1 2\n2 1"; 

        String inputPath = "tests/unstable.in";
        String outputPath = "tests/unstable.out";

        writeStringToFile(inputPath, inputContent);
        writeStringToFile(outputPath, badMatchContent);

        Verifier.main(new String[]{inputPath, outputPath});
        String output = outContent.toString();

        assertFalse("Should NOT be valid stable", output.contains("VALID STABLE"));
        assertTrue("Should detect instability", 
            output.toUpperCase().contains("UNSTABLE") || output.contains("blocking pair"));

        new File(inputPath).delete();
        new File(outputPath).delete();
    }

}