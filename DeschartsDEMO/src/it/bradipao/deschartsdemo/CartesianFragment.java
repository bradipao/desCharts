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

import it.bradipao.lib.descharts.CartesianView;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CartesianFragment extends Fragment {

   // views
   private View rootView;
   private CartesianView vChart;
      
   @Override
   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

      // inflate layout
      rootView = inflater.inflate(R.layout.fr_cartesian,container,false);
      // get views
      getViews();
      
      vChart.setXgrid(false,-1,1,10);
      
      return rootView;
   }
   
   private void getViews() {
      vChart = (CartesianView) rootView.findViewById(R.id.chart);
   }
   
}
