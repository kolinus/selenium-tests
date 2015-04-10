/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.ctools.cdf.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with component Visualization API.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VisualizationAPIComponent {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the TableComponent web page.
   */
  public static void init() {
    // The URL for the VisualizationAPIComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/VisualizationAPIComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name: Validate Page Contents Description: Here we want to
   * validate the page contents. Steps: 1. Check the widget's title.
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard
    // Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("VisualizationAPIComponent", ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name: Reload Sample Description: Reload the sample (not refresh
   * page). Steps: 1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name: Validate MAX number Description: When the user access the
   * component, it is presented the max number of array set. Steps: 1. Check the
   * presented value for MAX.
   */
  @Test(timeout = 60000)
  public void tc3_MaxNumber_PresentCorrectValue() {
    // ## Step 1
    String value = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sample']/div[2]/div/span"));
    for (int i = 0; i < 100; i++) {
      if (value.equals("35659")) {
        break;
      }
      else {
        value = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sample']/div[2]/div/span"));
      }
    }

    assertEquals("35659", value);
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name: Validate MIN number Description: When the user access the
   * component, it is presented the min number of array set. Steps: 1. Change
   * the option parameter to MIN and reload sample 2. Check the presented value
   * for MIN.
   *
   * @throws InterruptedException
   */
  @Test(timeout = 60000)
  public void tc4_MinNumber_PresentCorrectValue() throws InterruptedException {
    // ## Step 1 - Change the option parameter to MIN and reload sample
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();

    // Wait for board load
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='code']")));
    // Change contains to MIN
    ((JavascriptExecutor) driver).executeScript("$('#samplecode').text($('#samplecode').text().replace('MAX', 'MIN'));");

    String strText = "";
    while (strText.indexOf("MIN") == -1) {
      strText = ElementHelper.WaitForElementPresentGetText(driver, By.id("samplecode"));
    }

    // Click in Try me
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();
    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    // ## Step 2 - Check the presented value for MIN.
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sample']")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sample']/div[2]/div/span")));

    String value = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sample']/div[2]/div/span"));
    for (int i = 0; i < 100; i++) {
      if (value.equals("0")) {
        break;
      }
      else {
        value = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sample']/div[2]/div/span"));
      }
    }

    assertEquals("0", value);
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name: Validate AVG number Description: When the user access the
   * component, it is presented the avg number of array set. Steps: 1. Change
   * the option parameter to AVG and reload sample 2. Check the presented value
   * for AVG.
   *
   * @throws InterruptedException
   */
  @Test(timeout = 60000)
  public void tc5_AvgNumber_PresentCorrectValue() throws InterruptedException {
    // ## Step 1 - Change the option parameter to AVG and reload sample
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();

    // Wait for board load
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='code']")));
    // Change contains to AVG
    ((JavascriptExecutor) driver).executeScript("$('#samplecode').text($('#samplecode').text().replace('MIN', 'AVG'));");

    String strText = "";
    while (strText.indexOf("AVG") == -1) {
      strText = ElementHelper.WaitForElementPresentGetText(driver, By.id("samplecode"));
    }

    // Click in Try me
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();
    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    // ## Step 2 - Check the presented value for MIN.
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sample']")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sample']/div[2]/div/span")));

    String value = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sample']/div[2]/div/span"));
    for (int i = 0; i < 100; i++) {
      if (value.equals("4787.772727272727")) {
        break;
      }
      else {
        value = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sample']/div[2]/div/span"));
      }
    }
    assertEquals("4787.772727272727", value);
  }

  @AfterClass
  public static void tearDown() {}
}