/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Text Input Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class TextInputComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( TextInputComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the TextInputComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/TextInputComponent
    this.elemHelper.Get( driver, PageUrl.TEXT_INPUT_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    /*
     * ## Step 1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String expectedPageTitle = "Community Dashboard Framework";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    // Wait for visibility of 'TextInputComponent'
    String expectedSampleTitle = "TextInputComponent";
    String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );

    // Validate the sample that we are testing is the one
    assertEquals( actualPageTitle, expectedPageTitle );
    assertEquals( actualSampleTitle, expectedSampleTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );

    /*
     * ## Step 1
     */
    // Render again the sample
    this.elemHelper.Click( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.elemHelper.FindElements( driver, By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Insert a small text
   * Description:
   *    We pretend validate when we insert a small text an alert is raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test
  public void tc3_InputSmallPhrase_AlertDispayed() {
    this.log.info( "tc3_InputSmallPhrase_AlertDispayed" );

    /*
     * ## Step 1
     */
    String strInputString = "Hello World!";
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).clear();
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( strInputString );
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    String actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    String expctedConfirmationMsg = "you typed: " + strInputString;
    assertEquals( actualConfirmationMsg, expctedConfirmationMsg );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Insert a long text
   * Description:
   *    We pretend validate when we insert a long text an alert is raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test
  public void tc4_InputLongPhrase_AlertDispayed() {
    this.log.info( "tc4_InputLongPhrase_AlertDispayed" );

    /*
     * ## Step 1
     */
    String strInputString = "Hello World! Hello World! Hello World! Hello World! Hello World! Hello World!";
    strInputString += strInputString;
    strInputString += strInputString;
    strInputString += strInputString;
    strInputString += strInputString;
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).clear();
    //After clean text, we need to trait the pop-up
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );

    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( strInputString );
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    String actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    String expctedConfirmationMsg = "you typed: " + strInputString;
    assertEquals( actualConfirmationMsg, expctedConfirmationMsg );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Insert special characters
   * Description:
   *    We pretend validate when we insert a special characters an alert is
   *    raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test
  public void tc5_InputSpecialPhrase_AlertDispayed() {
    this.log.info( "tc5_InputSpecialPhrase_AlertDispayed" );

    /*
     * ## Step 1
     */
    String strInputString = "`|!\"1#$%&/()=?*»ª:_Ç<>/*-+";
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).clear();
    //After clean text, we need to trait the pop-up
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );

    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( strInputString );
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */

    String actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    String expctedConfirmationMsg = "you typed: " + strInputString;
    assertEquals( actualConfirmationMsg, expctedConfirmationMsg );
  }
}
