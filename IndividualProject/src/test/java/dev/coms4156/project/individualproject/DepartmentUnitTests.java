package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains the unit tests for the Department class. Includes setup for test environment and test
 * cases.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /**
   * Sets up a test Department before each test and attributes any associated courses. Includes
   * information including department, location, time, and number of majors.
   */
  @BeforeEach
  public void setupDepartmentForTesting() {
    Course econ1105 = new Course("Waseem Noor", "417 IAB", "10:10-11:25", 210);
    HashMap<String, Course> testCourses = new HashMap<>();
    testCourses.put("1105", econ1105);
    testDepartment = new Department("ECON", testCourses, "Michael Woodford", 2345);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "ECON 1105: \n"
            + "Instructor: Waseem Noor; Location: 417 IAB; Time: 10:10-11:25\n";

    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void getNumberOfMajorsTest() {
    assertEquals(2345, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    assertEquals("Michael Woodford", testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    Course econ1105 = new Course("Waseem Noor", "417 IAB", "10:10-11:25", 210);

    assertTrue(testDepartment.getCourseSelection().containsKey("1105"));
    assertTrue(checkCourseEquality(econ1105, testDepartment.getCourseSelection().get("1105")));
  }

  @Test
  public void addPersonToMajorTest() {
    testDepartment.addPersonToMajor();

    assertEquals(2346, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testDepartment.dropPersonFromMajor();

    assertEquals(2344, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    Course econ3211 = new Course("Murat Yilmaz", "310 FAY", "11:40-12:55", 96);

    testDepartment.addCourse("3211", econ3211);

    assertTrue(testDepartment.getCourseSelection().containsKey("3211"));
    assertTrue(checkCourseEquality(econ3211, testDepartment.getCourseSelection().get("3211")));
  }

  @Test
  public void createCourseTest() {
    Course econ3211 = new Course("Murat Yilmaz", "310 FAY", "11:40-12:55", 96);

    testDepartment.createCourse("3211", "Murat Yilmaz", "310 FAY", "11:40-12:55", 96);

    assertTrue(testDepartment.getCourseSelection().containsKey("3211"));
    assertTrue(checkCourseEquality(econ3211, testDepartment.getCourseSelection().get("3211")));
  }

  private boolean checkCourseEquality(Course course1, Course course2) {
    return course1.getInstructorName().equals(course2.getInstructorName())
            && course1.getCourseLocation().equals(course2.getCourseLocation())
            && course1.getCourseTimeSlot().equals(course2.getCourseTimeSlot())
            && course1.getEnrollmentCapacity() == course2.getEnrollmentCapacity();
  }

  /** The test department instance used for testing. */
  public static Department testDepartment;
}

