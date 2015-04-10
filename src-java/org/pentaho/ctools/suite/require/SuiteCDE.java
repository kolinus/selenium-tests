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
package org.pentaho.ctools.suite.require;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.pentaho.ctools.cde.require.AddinReference;
import org.pentaho.ctools.cde.require.AddinReferenceEdit;
import org.pentaho.ctools.cde.require.AjaxRequestReference;
import org.pentaho.ctools.cde.require.BullertChartTestCase;
import org.pentaho.ctools.cde.require.CCCV2ShowCase;
import org.pentaho.ctools.cde.require.MapComponentFullTest;
import org.pentaho.ctools.cde.require.MapComponentReference;
import org.pentaho.ctools.cde.require.PopupComponent;
import org.pentaho.ctools.cde.require.SampleDashboard;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  SampleDashboard.class,
  AddinReference.class,
  AddinReferenceEdit.class,
  AjaxRequestReference.class,
  BullertChartTestCase.class,
  CCCV2ShowCase.class,
  MapComponentFullTest.class,
  MapComponentReference.class,
  PopupComponent.class

})
public class SuiteCDE {}
