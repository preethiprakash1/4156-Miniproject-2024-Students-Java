package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains the unit tests for the Course class. Includes setup for test environment and test cases.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /**
   * Sets up a Course for testing and attributes any associated courses. Includes information
   * including instructor, location, time, and capacity.
   */
  @BeforeEach
  public void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";

    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest() {
    testCourse.setEnrolledStudentCount(200);

    assertTrue(testCourse.enrollStudent());
    assertEquals(201, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void enrollStudentTest_noSpaceAvailable() {
    testCourse.setEnrolledStudentCount(300);

    assertFalse(testCourse.enrollStudent());
    assertEquals(300, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(300);

    assertTrue(testCourse.dropStudent());
    assertEquals(299, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void dropStudentTest_atZeroStudents() {
    testCourse.setEnrolledStudentCount(0);

    assertFalse(testCourse.dropStudent());
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }

  @Test
  public void getEnrollmentCapacityTest() {
    assertEquals(250, testCourse.getEnrollmentCapacity());
  }

  @Test
  public void getEnrolledStudentCount() {
    assertEquals(0, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void reassignInstructorTest() {
    String newInstructorName = "Adam Cannon";
    testCourse.reassignInstructor(newInstructorName);

    assertEquals(newInstructorName, testCourse.getInstructorName());
  }

  @Test
  public void reassignLocationTest() {
    String newLocation = "309 HAV";
    testCourse.reassignLocation(newLocation);

    assertEquals(newLocation, testCourse.getCourseLocation());
  }

  @Test
  public void reassignTimeTest() {
    String newTime = "2:40-3:55";
    testCourse.reassignTime(newTime);

    assertEquals(newTime, testCourse.getCourseTimeSlot());
  }

  @Test
  public void setEnrolledStudentCountTest() {
    int newEnrolledStudentCount = 300;
    testCourse.setEnrolledStudentCount(newEnrolledStudentCount);

    assertEquals(newEnrolledStudentCount, testCourse.getEnrolledStudentCount());
  }

  @Test
  public void isCourseFullTest() {
    testCourse.setEnrolledStudentCount(200);

    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFullTest_courseIsAtCapacity() {
    testCourse.setEnrolledStudentCount(250);

    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFullTest_courseIsFull() {
    testCourse.setEnrolledStudentCount(300);

    assertTrue(testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

