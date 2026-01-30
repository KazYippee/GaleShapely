# Gale-Shapley Stable Matching

## Team Members
- Zach Merlo (UFID: 24342603)
- Insert Homie (UFID: XXXXXXXX)

## Overview
Implementation of the Gale-Shapley algorithm for hospital-student stable matching, with a verifier to check matching validity and stability.

## Compilation
Compile the Java source files from the project root:

```bash
javac src/Matcher.java
javac src/Verifier.java
```

## Running the Matcher
To run the matching algorithm:

```bash
java -cp src Matcher data/example.in
```

**Expected Output:**
```
1 2
2 3
3 1
```

You can also redirect output to a file:
```bash
java -cp src Matcher data/example.in > data/my_output.out
```

## Running the Verifier
To verify a matching:

```bash
java -cp src Verifier data/example.in data/example.out
```

**Expected Output:**
```
VALID STABLE
```

## Input Format
- First line: integer `n` (number of hospitals/students)
- Next `n` lines: hospital preference lists (each line contains `n` integers: a permutation of 1..n)
- Next `n` lines: student preference lists (each line contains `n` integers: a permutation of 1...n)

## Output Format
**Matcher Output:**
- `n` lines, each containing: `i j` (hospital `i` matched to student `j`)

**Verifier Output:**
- `VALID STABLE` if the matching is valid and stable
- `INVALID: <reason>` if the matching is invalid (e.g., duplicate matches, missing matches)
- `UNSTABLE: Hospital X and Student Y form blocking pair` if there's a blocking pair

## Assumptions
- Input files are well-formed with correct format
- Hospitals and students are numbered from 1 to n
- Each preference list is a complete permutation of all participants
- Standard Java runtime (Java 8 or higher) is available
- Files use UTF-8 encoding with Unix or Windows line endings Mostly because the assignment didn't really say

## Algorithm Details
The matcher implements the hospital-proposing deferred acceptance algorithm:
1. All hospitals start unmatched
2. Each unmatched hospital proposes to the next student on its preference list
3. Each student tentatively accepts the best proposal and rejects others
4. Rejected hospitals propose to their next choice
5. Process continues until all hospitals are matched

## Task C: Scalability Analysis

### Running Time Measurements
To measure performance for Task C, you can time the execution:

**On macOS/Linux:** 
```bash
time java -cp src Matcher data/test_n128.in > /dev/null
```

**On Windows (PowerShell):For Weirdos**
```powershell
Measure-Command { java -cp src Matcher data/test_n128.in | Out-Null }
```

### Test Cases
Generate test input files with varying `n` values (1, 2, 4, 8, 16, 32, 64, 128, 256, 512) and measure running time for both Matcher and Verifier.

### Results


**Observations:**
- Expected complexity: O(n²) for the matching algorithm
- The verifier also runs in O(n²) time

## Example Run

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

## Testing Edge Cases
The implementation handles:
- Empty files (n = 0)
- Single hospital and student (n = 1)
- Large inputs (tested up to n = 512)

## Dependencies
- Not much, you're probably chillin :3
