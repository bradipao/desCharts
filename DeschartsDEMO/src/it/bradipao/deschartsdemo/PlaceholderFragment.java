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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

   // section number argument
   private static final String ARG_SECTION_NUMBER = "section_number";

   /**
    * Returns a new instance of this fragment for the given section number.
    */
   public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER,sectionNumber);
      fragment.setArguments(args);
      return fragment;
   }

   /**
    * Constructor.
    */
   public PlaceholderFragment() {
   }

   @Override
   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fr_main,container,false);
      TextView textView = (TextView) rootView.findViewById(R.id.section_label);
      textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
      return rootView;
   }
   
}