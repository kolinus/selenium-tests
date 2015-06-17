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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import org.pentaho.gui.web.puc.BrowseFiles;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-366
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-948
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE356 {

  // Instance of the driver (browser emulator)
  private static WebDriver  DRIVER;
  // The base url to be append the relative url in test
  private static String     BASE_URL;
  // Log instance
  private static Logger     LOG                = LogManager.getLogger( CDE356.class );
  //Failure variable ==1 if test did not fail
  private static int        Failure            = 0;
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE356.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert New dashboard can be created from the edit dashboard screen
   *
   * Description:
   *    The test pretends validate the CDE-356 issue, so when user tries to create a new dashboard from a edit
   *    dashboard screen it works as expected
   * Steps:
   *    1. Wait for new Dashboard to be created, add Row and click New
   *    2. CLick cancel on the popup and assert row is still present. Click New
   *    3. Click Ok on the popup, assert row is no longer present, add row and save dashboard
   *    4. Click new, click cancel and assert same dashboard is shown
   *    5. Click new, click Ok and assert new dashboard is shown
   */
  @Test( timeout = 240000 )
  public void tc01_CdeDashboard_CreateNewFromEdit() {
    LOG.info( "tc01_CdeDashboard_CreateNewFromEdit" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Add Row and assert it was added
    WebElement addRow = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='table-cdfdd-layout-treeOperations']//a[@title='Add Row']" ) );
    assertNotNull( addRow );
    addRow.click();
    WebElement addedRow = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( addedRow );
    String elementName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertEquals( "Row", elementName );

    //Click New
    WebElement newButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.newDashboard()']" ) );
    assertNotNull( newButton );
    newButton.click();
    WebElement warningPopup = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popup_state_state0" ) );
    assertNotNull( warningPopup );
    String warningMessage = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='popup_state_state0']/div/span" ) );
    assertEquals( "Unsaved changes will be lost.", warningMessage );

    /*
     * ## Step 2
     */
    //Click Cancel and assert row is still there
    WebElement cancelButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    ElementHelper.WaitForElementNotPresent( DRIVER, By.id( "popup_state_state0" ) );
    addedRow = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( addedRow );
    elementName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertEquals( "Row", elementName );

    //Click New
    newButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.newDashboard()']" ) );
    assertNotNull( newButton );
    newButton.click();
    warningPopup = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popup_state_state0" ) );
    assertNotNull( warningPopup );
    warningMessage = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='popup_state_state0']/div/span" ) );
    assertEquals( "Unsaved changes will be lost.", warningMessage );

    /*
     * ## Step 3
     */
    //Click Ok and assert row is not present
    WebElement okButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( okButton );
    okButton.click();
    ElementHelper.WaitForElementNotPresent( DRIVER, By.id( "popup_state_state0" ) );
    ElementHelper.WaitForElementNotPresent( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );

    //Add row
    addRow = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='table-cdfdd-layout-treeOperations']//a[@title='Add Row']" ) );
    assertNotNull( addRow );
    addRow.click();
    addedRow = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( addedRow );
    elementName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertEquals( "Row", elementName );

    //Save dashboard
    WebElement saveButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.save()']" ) );
    assertNotNull( saveButton );
    saveButton.click();

    /*
     * ## Step 4
     */
    //Save dashboard and click save
    Failure = 1;
  }

  @AfterClass
  public static void tearDownClass() {
    if (Failure == 0) {
      BrowseFiles browse = new BrowseFiles( DRIVER );
      browse.DeleteMultipleFilesByName( "/public", "CDE366" );
      browse.EmptyTrash();
    }
    LOG.info( "tearDown##" + CDE356.class.getSimpleName() );
  }
}