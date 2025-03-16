package sol;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admissions {

    public Admissions() {}

    // Modified to make all inputs a double, instead SAT & EC an int
    public double admitV1(double gpa, double sat, double extracurric) {
        return ((gpa * .6) +
            (sat * .1) +
            (extracurric * .15));
    }

    //------------------------------- V2 ---------------------------------------

    // TODO Task 2: Write admitV2 here

    //------------------------------- V3 ---------------------------------------

    // TODO Task 3: Create likely_succeed & likely_contribute

    // TODO Task 4: Write admitV3 here


    //------------------------------- V4 ---------------------------------------

    // TODO Task 5: Write admitV4 here
    //------------------------------- V5 ---------------------------------------


    // TODO Task 6: Define a helper-class that'll be the return type of admitV5

    // TODO Task 6: Uncomment & Implement admitV5
//    public ? admitV5(HashMap<String, Double> studentData,
//                     HashMap<String, Function<HashMap<String, Double>, Double>> labelToWeight) {
//        return ;
//    }



    //--------------------------Matrix Practice---------------------------------

    public static LabeledMatrix matrixPractice1() {

        // create a matrix that will be a result of multiplying:

        //               Practice Matrix 1
        //            gpa     sat    extracurr  honors  awards
        // Walter   : 3.5   1450.0     4.0        0       2
        // Jesse    : 3.8   1350.0     3.2        1       1
        // Gus      : 3.0   1250.0     5.0        0       5

        //               Practice Matrix 2
        //              fn1     fn2
        //    gpa       0.4     0.1
        //    sat       0.2     0.3
        // extracurr    0.1     0.4
        //   honors     0.2     0.0
        //   awards     0.1     0.2

        //TODO Task 7: Use comment above to create and multiply Matrix 1 & 2
        return null;
    }

    public static LabeledMatrix matrixPractice2() {

        // 5 students by 2 params with 2 params by 3 functions


        //               Practice Matrix 3
        //              fn1     fn2     fn3
        //  isLeader    0.5     0.7     0.1
        //  isVarsity   0.5     0.3     0.9


        //               Practice Matrix 4
        //             isLeader    isVarsity
        // Kratos   :      1           1
        // Loki     :      0           0
        // Odin     :      1           0
        // Thor     :      0           1
        // Heimdall :      0           1

        //TODO Task 7: Use comment above to create and multiply Matrix 3 & 4
        return null;
    }

    public static Double findGPAinFile(String dirPath) {

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(dirPath)));

            // GPA → Matches the exact text "GPA".
            // \s* → Matches any number of spaces (including none).
            // : → Matches the literal colon.
            // \s* → Matches any number of spaces (including none) after the colon.
            // \d+ → Matches at least one digit (whole number part).
            // (\.\d+)? → Optionally matches a decimal point followed by more digits (for decimal GPAs).
            // so, this matches GPA: 4.0, GPA : 2, GPA : 3.95
            // this will also match something invalid like gpa higher than 4 or negative gpa
            Pattern pattern = Pattern.compile("GPA\\s*:\\s*\\d+(\\.\\d+)?");
            Matcher matcher = pattern.matcher(fileContent);

            // Find matches
            if (matcher.find()) {
                String found = matcher.group();
                System.out.println("Match found: " + found);
                String[] parts = found.split(":");
                return Double.parseDouble(parts[1]);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return -1.0;
    }

    public static void main(String[] args) {
        // an example of making a function object
        Function<Integer,Integer> a = n -> n * 2;
        System.out.println(a.apply(5));

        // --------------How to create and multiply matrices--------------------

        // STEP 1: set your params, students, and function labels, all of which are List<String>
        // params is the number of characteristics each student has in their map
        List<String> params = Arrays.asList("gpa", "sat", "extracurric");

        // students is the list of students you want to use
        List<String> students = Arrays.asList("Student 1", "Student 2");

        // functions is the list of function names that will work on params
        List<String> functions = Arrays.asList("likelySucceed", "likelyWellRounded", "likelyContribute");


        // STEP 2: choose an option to create your matrices!
        // IMPORTANT note: to multiply two matrices, their inner dimension needs to align
        // For example, if I have 2 students with 3 params each, my first matrix is 2 x 3

        //               Example Matrix 1
        //            gpa     sat    extracurr
        // student 1: 3.5   1450.0     4.0
        // student 2: 3.8   1350.0     3.2

        // then, in my second matrix, I would want params to be my rows, and functions to be columns

        //               Example Matrix 2
        //              fn1     fn2   fn3
        //    gpa      0.75    0.5    0.3
        //    sat      0.25    0.35   0
        // extracurr     0     0.15,  0.7

        // as you might note, every column represents a function, that sums up to cumulative weight
        // of 1!


        // Now, let's take a look at every option that we have
        // Option 1: enter every number manually, row by row
        LabeledMatrix mat1 = new LabeledMatrix(students, params, 3.5, 1450.0, 4.0, 3.8, 1350, 3.2);
        LabeledMatrix mat2 = new LabeledMatrix(params, functions, 0.75, 0.5, 0.3, 0.25, 0.35, 0, 0, 0.15, 0.7);

        // Option 2: use a csv file which is located in the current directory
        LabeledMatrix mat3 = new LabeledMatrix(students, params, "data/StudentMatrix.csv");
        LabeledMatrix mat4 = new LabeledMatrix(params, functions, "data/FunctionMatrix.csv");

        // Option 3: create an empty matrix, and then use setRow/setCol to put values either
        // by row or by column. The first argument is the index of said row/col

        LabeledMatrix mat5 = new LabeledMatrix(students, params);
        mat5.setRow(0, new double[]{3.5, 1450, 4.0});
        mat5.setRow(1, new double[]{3.8, 1350, 3.2});

        LabeledMatrix mat6 = new LabeledMatrix(params, functions);
        mat6.setCol(0, new double[]{0.75, 0.25, 0});
        mat6.setCol(1, new double[]{0.5, 0.35, 0.15});
        mat6.setCol(2, new double[]{0.3, 0.00, 0.7});


        // STEP 3: Finally, let's multiple some matrices!
        // Each multiplication will return a new matrix as a result
        // the order of arguments to multiplyMatrices is important!!

        LabeledMatrix res = LabeledMatrix.multiplyMatrices(mat1, mat2);
        LabeledMatrix res2 = LabeledMatrix.multiplyMatrices(mat3, mat4);
        LabeledMatrix res3 = LabeledMatrix.multiplyMatrices(mat5, mat6);
        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);

        //------------------------------- Task 9 ---------------------------------------

        // TODO Task 9: Call findGPAinFile with the correct regexPAth
        String regexPath = "";
        Double gpa = findGPAinFile(regexPath);
        System.out.println(gpa);
    }
}