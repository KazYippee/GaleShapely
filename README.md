
# Gale-Shapley Stable Matching

This repository contains a Java implementation of the Gale-Shapley algorithm for the hospital-student stable matching problem, a verifier for match validity and stability, and a graphing tool for scalability analysis. (Section headers may contain a little :3 energy.)

---

## Team Members :3
- Zach Merlo (UFID: 24342603)
- Ibrahim Zbib (UFID: 79090242)

---

## Part 1: Matching Engine (Matcher.java)

Matcher.java implements the hospital-proposing Gale-Shapley algorithm. It reads input files, processes proposals, and outputs the final matching between hospitals and students.

**How to run:**

```bash
javac src/Matcher.java
java -cp src Matcher data/example.in
```

**Sample output:**
```
1 2
2 3
3 1
```

You can also redirect output to a file:
```bash
java -cp src Matcher data/example.in > data/my_output.out
```

---

## Part 2: Verifier (Verifier.java)

Verifier.java checks that each hospital and student is matched exactly once and that there are no blocking pairs. It reports if the matching is valid and stable, or provides a clear error message if not.

**How to run:**

```bash
javac src/Verifier.java
java -cp src Verifier data/example.in data/example.out
```

**Possible output:**
```
VALID STABLE
```
Or, if there is an issue:
```
INVALID: Student 2 matched multiple times
UNSTABLE: Hospital 1 and Student 3 form blocking pair
```

---

## Part 3: Input & Output Format :3

**Input:**
- First line: integer `n` (number of hospitals/students)
- Next `n` lines: hospital preferences (each line is a permutation of 1..n)
- Next `n` lines: student preferences (each line is a permutation of 1..n)

**Matcher Output:**
- `n` lines: `i j` (hospital i matched to student j)

**Verifier Output:**
- `VALID STABLE` if the matching is valid and stable
- `INVALID: <reason>` if the matching is invalid
- `UNSTABLE: Hospital X and Student Y form blocking pair` if there is a blocking pair

---

## Part 4: Edge Cases
- Handles empty files (n = 0) :3
- Handles a single hospital and student (n = 1)
- Handles large inputs (tested up to n = 512)

---

## Part 5: Graphing Scalability (GraphPlotter.java) :3

GraphPlotter.java visualizes the running time of the matcher and verifier for different input sizes. The graph helps you understand how the algorithms scale as n increases.

**How to use:**
1. Add timing data to `data/timing.csv` (format: n,matcherTimeMs,verifierTimeMs)
2. Compile and run:
```bash
javac src/GraphPlotter.java
java -cp src GraphPlotter
```
3. A window will appear. The crimson line shows matcher times, the blue line shows verifier times. The legend identifies each line. If the lines overlap, try using different values in your CSV. :3

**Graph Features:**
- X-axis: n (number of hospitals/students)
- Y-axis: time in milliseconds (ms)
- Crimson line: Matcher
- Blue line: Verifier
- Legend for clarity
- Dots at each data point

---

## Part 6: Scalability & Timing (Task C)

To measure performance, use the following commands:

**On macOS/Linux:**
```bash
time java -cp src Matcher data/test_n128.in > /dev/null
```

**On Windows (PowerShell):**
```powershell
Measure-Command { java -cp src Matcher data/test_n128.in | Out-Null }
```

Try different n values (1, 2, 4, 8, ... 512) and record the results in timing.csv. Then run the graph to visualize scalability.

**Expected trend:** Both matcher and verifier should have O(n²) growth. :3

---

## Part 7: Example Run

```bash
# Compile
javac src/Matcher.java
javac src/Verifier.java

# Run matcher
java -cp src Matcher data/example.in
# Output:
# 1 2
# 2 3
# 3 1

# Verify the result
java -cp src Verifier data/example.in data/example.out
# Output:
# VALID STABLE
```

---

## Part 8: Assumptions & Dependencies
- Input files are well-formed
- Java 8 or higher
- No external libraries required

---

## Part 9: Testing 
- Handles all required edge cases
- If you can run Java, you can run this :3

- Below are the steps required to install the necessary dependencies, compile tests, and finally run them

### Dependencies
- paste the following commands into your terminal:

``` mkdir -p lib```

```curl -o lib/junit-4.13.2.jar https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar ```

```curl -o lib/hamcrest-core-1.3.jar https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar```

### Compile
- put this into your terminal after installing dependenceis to compile

#### macOS / Linux

```javac -cp .:lib/junit-4.13.2.jar:src tests/GaleShapleyTest.java```

#### Windows

```javac -cp ".;lib/junit-4.13.2.jar;src" tests/GaleShapleyTest.java```

---

### Run
#### Mac/Linux

```java -cp .:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:src:tests org.junit.runner.JUnitCore GaleShapleyTest```

#### Windows

```java -cp ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;src;tests" org.junit.runner.JUnitCore GaleShapleyTest```

The expected output should be :
```
JUnit version 4.13.2
.......
Time: 0.009

OK (7 tests)
```

## Please give 100 :3:3:3:3
