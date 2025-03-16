package sol;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AdmissionsTest {

  // Common variables to be reused in tests
  private HashMap<String, Double> studentData;
  private Admissions admissions;

  @Before
  public void setUp() {
    // Initialize common test data for all tests
    this.studentData = new HashMap<>();
    this.studentData.put("gpa", 3.8);
    this.studentData.put("sat", 1450.0);
    this.studentData.put("extracurric", 4.5);

    this.admissions = new Admissions();  // Initialize the Admissions instance
  }

  @Test
  public void testAdmitV1() {
    // Calculate expected result
    Double expected = (3.8 * 0.6) + (1450.0 * 0.1) + (4.5 * 0.15);
    Double result = this.admissions.admitV1(3.8, 1450.0, 4.5);

    // Assert (use Double instead of double to avoid using delta)
    Assert.assertEquals(expected, result);
  }

  @Test
  public void testAdmitV2() {
    // Calculate expected result
    Double expected = (3.8 * 0.6) + (1450.0 * 0.1) + (4.5 * 0.15);
//    Double result = this.admissions.admitV2(studentData);
//
//    // Assert
//    Assert.assertEquals(expected, result);
  }

  @Test
  public void testAdmitV3() {
    // Expected results for both likelySucceed and likelyContribute
    Double expectedSucceed = (3.8 * 0.75) + (1450.0 * 0.25);
    Double expectedContribute = (3.8 * 0.3) + (4.5 * 0.7);

    // Call the method to test
//    ArrayList<Double> result = this.admissions.admitV3(studentData);
//
//    // Assert
//    Assert.assertEquals(expectedSucceed, result.get(0));
//    Assert.assertEquals(expectedContribute, result.get(1));
  }

  @Test
  public void testAdmitV4() {
    // Define test weights (functions) for GPA, SAT, and Extracurricular
    ArrayList<Function<HashMap<String, Double>, Double>> weights = new ArrayList<>();
    weights.add(n -> n.get("gpa") * 0.6);
    weights.add(n -> n.get("sat") * 0.1);
    weights.add(n -> n.get("extracurric") * 0.15);

    // Expected results for the weights applied to the parameters
    Double expectedGPA = 3.8 * 0.6;
    Double expectedSAT = 1450.0 * 0.1;
    Double expectedExtracurric = 4.5 * 0.15;

    // Call the method to test
//    ArrayList<Double> result = this.admissions.admitV4(studentData, weights);
//
//    // Assert that the results are correct
//    Assert.assertEquals(expectedGPA, result.get(0));
//    Assert.assertEquals(expectedSAT, result.get(1));
//    Assert.assertEquals(expectedExtracurric, result.get(2));
  }

  @Test
  public void testMatrixPratice1(){
    List<String> studentNames1 = Arrays.asList("Walter", "Jesse", "Gus");
    List<String> weightFunctions1 = Arrays.asList("fn1", "fn2");

    LabeledMatrix resultMatrixExpected = new LabeledMatrix(studentNames1,
        weightFunctions1, 292, 437.35, 272.14, 406.86, 252.20, 378.30);

    Assert.assertEquals(resultMatrixExpected,
        Admissions.matrixPractice1());
  }

  @Test
  public void testMatrixPratice2(){
    List<String> StudentNames2 = Arrays.asList("Kratos", "Loki", "Odin", "Thor", "Heimdall");
    List<String> WeightFunctions2 = Arrays.asList("fn1", "fn2", "fn3");

    LabeledMatrix resultMatrixExpected = new LabeledMatrix(StudentNames2
        , WeightFunctions2, 1, 1, 1, 0, 0, 0, 0.5, 0.7, 0.1, 0.5, 0.3, 0.9, 0.5, 0.3, 0.9);

    Assert.assertEquals(resultMatrixExpected,
        Admissions.matrixPractice2());
  }
}