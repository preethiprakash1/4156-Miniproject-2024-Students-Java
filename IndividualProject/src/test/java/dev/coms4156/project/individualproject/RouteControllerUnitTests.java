package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Contains the unit tests for the RouteController class. Includes setup for test environment and
 * test cases.
 */
@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MyFileDatabase myFileDatabase;

  private HashMap<String, Department> departmentMapping;
  private HashMap<String, Course> coursesMapping;

  /**
   * Sets up a course mappings and department mappings. Uses Mockito to mock the department
   * mapping returned.
   */
  @BeforeEach
  public void setUp() {
    departmentMapping = new HashMap<>();
    coursesMapping = new HashMap<>();
    Course econ1105 = new Course("Waseem Noor", "417 IAB", "10:10-11:25", 210);
    coursesMapping.put("1105", econ1105);
    Department department = new Department("ECON", coursesMapping, "Michael Woodford", 2345);
    departmentMapping.put("ECON", department);

    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    IndividualProjectApplication.myFileDatabase = myFileDatabase;
  }

  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Welcome, in order to make an API "
                    + "call direct your browser or Postman to an endpoint \n\n This can be done "
                    + "using the following format: \n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void retrieveDepartmentTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveDept")
                    .param("deptCode", "ECON"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(departmentMapping.get("ECON")
                    .toString()));
  }

  @Test
  public void retrieveDepartmentTest_nonexistentDept() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveDept")
                    .param("deptCode", "NONEXISTENT"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void retrieveCourseTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveCourse")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                    .string(coursesMapping.get("1105").toString()));
  }

  @Test
  public void retrieveCourseTest_nonexistentCourse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveCourse")
                    .param("deptCode", "ECON")
                    .param("courseCode", "123"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void retrieveCourseTest_nonexistentDepartment() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveCourse")
                    .param("deptCode", "ABC")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void isCourseFullTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/isCourseFull")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("false"));
  }

  @Test
  public void isCourseFullTest_nonexistentCourse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/isCourseFull")
                    .param("deptCode", "ECON")
                    .param("courseCode", "123"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void getMajorCountFromDept() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/getMajorCountFromDept")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("There are: 2345 majors in the "
                    + "department"));
  }

  @Test
  public void getMajorCountFromDept_nonexistentDepartment() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/getMajorCountFromDept")
                    .param("deptCode", "ABC")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void idDeptChair() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/idDeptChair")
                    .param("deptCode", "ECON"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Michael Woodford is the "
                    + "department chair."));
  }

  @Test
  public void idDeptChair_nonexistentDepartment() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/idDeptChair")
                    .param("deptCode", "ABC"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void findCourseLocation() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseLocation")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("417 IAB is where the course is "
                    + "located."));
  }

  @Test
  public void findCourseLocation_nonexistentCourse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseLocation")
                    .param("deptCode", "ECON")
                    .param("courseCode", "123"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructor() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseInstructor")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Waseem Noor is the instructor for "
                    + "the course."));
  }

  @Test
  public void findCourseInstructor_nonexistentCourse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseInstructor")
                    .param("deptCode", "ECON")
                    .param("courseCode", "123"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void findCourseTime() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseTime")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("The course meets at: 10:10-11:25"));
  }

  @Test
  public void findCourseTime_nonexistentCourse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseTime")
                    .param("deptCode", "ECON")
                    .param("courseCode", "123"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void addMajorToDept() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/addMajorToDept")
                    .param("deptCode", "ECON"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attribute was updated "
                    + "successfully"));
    assertEquals(2346, departmentMapping.get("ECON").getNumberOfMajors());
  }

  @Test
  public void addMajorToDept_nonexistentDepartment() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/addMajorToDept")
                    .param("deptCode", "ABC"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void removeMajorFromDept() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/removeMajorFromDept")
                    .param("deptCode", "ECON"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attribute was updated or is at "
                    + "minimum"));
    assertEquals(2344, departmentMapping.get("ECON").getNumberOfMajors());
  }

  @Test
  public void removeMajorFromDept_nonexistentDepartment() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/removeMajorFromDept")
                    .param("deptCode", "ABC"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void dropStudentFromCourse_noStudents() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/dropStudentFromCourse")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Student has not been dropped."));
    assertEquals(0, coursesMapping.get("1105").getEnrolledStudentCount());
  }

  @Test
  public void setEnrollmentCount() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/setEnrollmentCount")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105")
                    .param("count", "100"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attributed was updated "
                    + "successfully."));
    assertEquals(100, coursesMapping.get("1105").getEnrolledStudentCount());
  }

  @Test
  public void changeCourseTime() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/changeCourseTime")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105")
                    .param("time", "11:10-11:25"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attributed was updated "
                    + "successfully."));
    assertEquals("11:10-11:25", coursesMapping.get("1105").getCourseTimeSlot());
  }

  @Test
  public void changeCourseTeacher() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/changeCourseTeacher")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105")
                    .param("teacher", "Preethi Prakash"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attributed was updated "
                    + "successfully."));
    assertEquals("Preethi Prakash", coursesMapping.get("1105").getInstructorName());
  }

  @Test
  public void changeCourseLocation() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/changeCourseLocation")
                    .param("deptCode", "ECON")
                    .param("courseCode", "1105")
                    .param("location", "Ferris"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Attributed was updated "
                    + "successfully."));
    assertEquals("Ferris", coursesMapping.get("1105").getCourseLocation());
  }
}