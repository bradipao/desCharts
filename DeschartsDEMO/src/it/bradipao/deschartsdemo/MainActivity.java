/*******************************************************************************
 * Descharts library DEMO
 * Copyright (c) 2014 Bradipao <bradipao@gmail.com>
 * https://plus.google.com/+SidBradipao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package it.bradipao.deschartsdemo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

   // Bundle key representing the current dropdown position.
   private static final String STATE_SELECTED_ITEM = "selected_nav_item";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.ac_main);
      
      // setup the action bar to show a dropdown list
      final ActionBar aBar = getActionBar();
      aBar.setDisplayShowTitleEnabled(false);
      aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

      // setup the dropdown list navigation in the action bar
      aBar.setListNavigationCallbacks(
         new ArrayAdapter<String>(aBar.getThemedContext(),
            android.R.layout.simple_list_item_1,android.R.id.text1,
            new String[] { getString(R.string.title_section1),
                           getString(R.string.title_section2),
                           getString(R.string.title_section3),
                           getString(R.string.title_section4),
                           getString(R.string.title_section5),
                           getString(R.string.title_section6)  } ),this);
   }

   @Override
   public void onRestoreInstanceState(Bundle savedInstanceState) {
      // restore the previously serialized current dropdown position
      if (savedInstanceState.containsKey(STATE_SELECTED_ITEM)) {
         getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_ITEM));
      }
   }

   @Override
   public void onSaveInstanceState(Bundle outState) {
      // serialize the current dropdown position
      outState.putInt(STATE_SELECTED_ITEM,getActionBar().getSelectedNavigationIndex());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // inflate the menu; this adds items to the action bar if it is present
      getMenuInflater().inflate(R.menu.main,menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // handle action bar item clicks here
      int id = item.getItemId();
      if (id == R.id.action_settings) {
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public boolean onNavigationItemSelected(int position, long id) {
      // initiate fragment transaction
      FragmentTransaction tx = getFragmentManager().beginTransaction();
      // perform transaction according to selected entry
      switch (position) {
      case 0:
         tx.replace(android.R.id.content,new XyChartFragment());
         break;
      case 1:
         tx.replace(android.R.id.content,PlaceholderFragment.newInstance(position+1));
         break;
      case 2:
         tx.replace(android.R.id.content,PlaceholderFragment.newInstance(position+1));
         break;
      case 3:
         tx.replace(android.R.id.content,PlaceholderFragment.newInstance(position+1));
         break;
      case 4:
         tx.replace(android.R.id.content,PlaceholderFragment.newInstance(position+1));
         break;
      case 5:
         tx.replace(android.R.id.content,new CartesianFragment());
         break;
      default:
         break;
      }
      // commit transaction and return
      tx.commit();
      return true;
   }

}
