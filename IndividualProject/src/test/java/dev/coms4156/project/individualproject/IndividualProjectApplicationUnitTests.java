package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    individualProjectApplication = spy(IndividualProjectApplication.class);
    mockDatabase = mock(MyFileDatabase.class);
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }

  @Test
  public void runTest_withSetupArg() {
    individualProjectApplication.run(new String[]{"setup"});

    verify(individualProjectApplication, times(1)).resetDataFile();
  }

  @Test
  public void runTest_withoutSetupArg() {
    individualProjectApplication.run(new String[]{});

    verify(individualProjectApplication, times(0)).resetDataFile();
  }

  @Test
  public void onTerminationTest_withSetSaveData() {
    individualProjectApplication.setSaveData(true);

    individualProjectApplication.onTermination();

    verify(mockDatabase, times(1)).saveContentsToFile();
  }

  @Test
  public void onTerminationTest_withoutSetSaveData() {
    individualProjectApplication.setSaveData(false);

    individualProjectApplication.onTermination();

    verify(mockDatabase, times(0)).saveContentsToFile();
  }

  /** The testIndividualProjectApplicatione instance used for testing. */
  public static IndividualProjectApplication individualProjectApplication;
  public static MyFileDatabase mockDatabase;
  public static String filePath = "./data.txt";
}

