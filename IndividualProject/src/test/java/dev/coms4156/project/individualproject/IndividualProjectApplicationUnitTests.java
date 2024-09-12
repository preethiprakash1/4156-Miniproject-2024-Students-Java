package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains the unit tests for the IndividualProjectApplication class. Includes setup for test
 * environment and test cases.
 */
@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationUnitTests {

  /**
   * Sets up a test IndividualProjectApplication before each test. Includes database mode and file
   * path.
   */
  @BeforeEach
  public void setupIndividualProjectApplicationForTesting() {
    individualProjectApplication = new IndividualProjectApplication();
    MyFileDatabase myFileDatabase = new MyFileDatabase(0, filePath);
    individualProjectApplication.overrideDatabase(myFileDatabase);
  }

  @Test
  public void runTest_withSetupArg() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    individualProjectApplication.run(new String[]{"setup"});

    assertEquals("System Setup\n", outContent.toString());
  }

  @Test
  public void runTest_withoutSetupArg() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    individualProjectApplication.run(new String[]{});

    assertEquals("Start up\n", outContent.toString());
  }

  // ********* HAVE TO FINISH
  @Test
  public void resetDataFileTest() {
  }

  // ********* HAVE TO FINISH
  @Test
  public void onTerminationTest() {
  }

  /** The testIndividualProjectApplicatione instance used for testing. */
  public static IndividualProjectApplication individualProjectApplication;
  public static String filePath = "./data.txt";
}

