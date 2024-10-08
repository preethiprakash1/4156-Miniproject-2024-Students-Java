package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains the unit tests for the MyFileDatabase class. Includes setup for test environment and
 * test cases.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseUnitTests {

  /**
   * Sets up a test MyFileDatabase before each test. Includes database mode and file path.
   */
  @BeforeEach
  public void setupMyFileDatabaseForTesting() {
    myFileDatabase = new MyFileDatabase(0, filePath);
  }

  @Test
  public void setGetMappingTest() {
    HashMap<String, Department> departmentMap = new HashMap<>();
    Department newDepartment = new Department("ABC", new HashMap<>(), "Preethi Prakash", 100);
    departmentMap.put("ABC", newDepartment);

    myFileDatabase.setMapping(departmentMap);

    assertEquals(departmentMap, myFileDatabase.getDepartmentMapping());
  }

  @Test
  public void toStringTest() {
    HashMap<String, Department> departmentMap = new HashMap<>();
    Department newDepartment = new Department("ABC", new HashMap<>(), "Preethi Prakash", 100);
    departmentMap.put("ABC", newDepartment);
    String expectedString = "For the ABC department: \n" + newDepartment.toString();

    myFileDatabase.setMapping(departmentMap);

    assertEquals(expectedString, myFileDatabase.toString());
  }

  @Test
  public void deSerializeObjectFromFileTest() {
    Map<String, Department>  departmentMappings = myFileDatabase.deSerializeObjectFromFile();

    assertNotNull(departmentMappings);
  }

  @Test
  public void saveContentsToFileTest() {
    MyFileDatabase newFileDatbase = new MyFileDatabase(0, "new_data.txt");
    Department department = new Department("CHEM", new HashMap<>(), "Laura J. Kaufman", 250);
    HashMap<String, Department> departmentMap = new HashMap<>();
    departmentMap.put("CHEM", department);
    newFileDatbase.setMapping(departmentMap);

    newFileDatbase.saveContentsToFile();
    Map<String, Department> mapping = newFileDatbase.getDepartmentMapping();

    assertEquals(department, mapping.get("CHEM"));
  }

  /** The test file database instance used for testing. */
  public static MyFileDatabase myFileDatabase;
  public static String filePath = "./data.txt";
}

