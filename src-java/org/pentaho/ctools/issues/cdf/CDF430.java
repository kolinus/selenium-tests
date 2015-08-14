/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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
package org.pentaho.ctools.issues.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-430
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1007
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDF430 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF430.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert i18n messages are prioritized correctly
   *
   * Description:
   *    The test pretends validate the CDF-430 issue, so  i18n messages are prioritized correctly for CDE, CDF and Sparkl dashboards.
   *
   * Steps:
   *    1. Open created Sparkl sample dashboard and assert text is displayed as expected
   *    2. Open created CDE sample dashboard and assert text is displayed as expected
   *    3. Open created CDF sample dashboard and assert text is displayed as expected
   *
   */
  @ Test
  public void tc01_i18nMessages_PioritizedCorrectly() {
    this.log.info( "tc01_i18nMessages_PioritizedCorrectly" );

    /*
     * ## Step 1
     */
    //Set locale
    this.driver.get( this.baseUrl + "Home?locale=en-US" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Go to Sparkl sample
    this.driver.get( this.baseUrl + "plugin/CDE404/api/i18ntest" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Panel_1" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Panel_2" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Panel_3" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( this.driver, By.id( "Panel_1" ), "my message 1, coming from messages.properties" );
    String text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "Panel_1" ) );
    assertEquals( text, "my message 1, coming from messages.properties" );
    this.elemHelper.WaitForTextPresence( this.driver, By.id( "Panel_2" ), "my message 2, overriden by messages_en.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "Panel_2" ) );
    assertEquals( text, "my message 2, overriden by messages_en.properties" );
    this.elemHelper.WaitForTextPresence( this.driver, By.id( "Panel_3" ), "my message 3, overriden by messages_en-US.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "Panel_3" ) );
    assertEquals( text, "my message 3, overriden by messages_en-US.properties" );

    /*
     * ## Step 2
     */
    //Go to CDE sample
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Panel_1" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Panel_2" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Panel_3" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( this.driver, By.id( "Panel_1" ), "my message 1, coming from messages.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "Panel_1" ) );
    assertEquals( text, "my message 1, coming from messages.properties" );
    this.elemHelper.WaitForTextPresence( this.driver, By.id( "Panel_2" ), "my message 2, overriden by messages_en.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "Panel_2" ) );
    assertEquals( text, "my message 2, overriden by messages_en.properties" );
    this.elemHelper.WaitForTextPresence( this.driver, By.id( "Panel_3" ), "my message 3, overriden by messages_en-US.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "Panel_3" ) );
    assertEquals( text, "my message 3, overriden by messages_en-US.properties" );

    /*
     * ## Step 3
     */
    //Go to CDE sample
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDF%3Acdf_i18nTest.xcdf/generatedContent?locale=en-US" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='sampleButton01']/button/span" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='sampleButton02']/button/span" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='sampleButton03']/button/span" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='sampleButton01']/button/span" ), "My button 01 label" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sampleButton01']/button/span" ) );
    assertEquals( text, "My button 01 label" );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='sampleButton02']/button/span" ), "messages_en button 02 label" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sampleButton02']/button/span" ) );
    assertEquals( text, "messages_en button 02 label" );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='sampleButton03']/button/span" ), "messages_en-US button 03 label" );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sampleButton03']/button/span" ) );
    assertEquals( text, "messages_en-US button 03 label" );

  }

}
