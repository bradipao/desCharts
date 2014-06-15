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

import it.bradipao.lib.descharts.ChartPoint;
import it.bradipao.lib.descharts.ChartPointSerie;
import it.bradipao.lib.descharts.XyChartView;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class XyChartFragment extends Fragment {

   // views
   private View rootView;
   private XyChartView vChart;
      
   @Override
   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

      // inflate layout
      rootView = inflater.inflate(R.layout.fr_xychart,container,false);
      // get views
      getViews();
      
      // create RED dummy serie
      ChartPointSerie rr = new ChartPointSerie(Color.RED,1);
      rr.addPoint(new ChartPoint(-98,99));
      rr.addPoint(new ChartPoint(-49,80));
      rr.addPoint(new ChartPoint(-5,180));
      rr.addPoint(new ChartPoint(17,99));
      rr.addPoint(new ChartPoint(54,80));
      rr.addPoint(new ChartPoint(125,120));
      rr.addPoint(new ChartPoint(158,20));
      rr.addPoint(new ChartPoint(209,50));
      rr.addPoint(new ChartPoint(317,109));
      
      // create GREEN dummy serie
      ChartPointSerie gg = new ChartPointSerie(Color.GREEN,2);
      gg.addPoint(new ChartPoint(17,-10));
      gg.addPoint(new ChartPoint(54,20));
      gg.addPoint(new ChartPoint(125,-50));
      gg.addPoint(new ChartPoint(158,89));
      gg.addPoint(new ChartPoint(209,20));
      gg.addPoint(new ChartPoint(317,Float.NaN));
      gg.addPoint(new ChartPoint(350,99));
      gg.addPoint(new ChartPoint(461,75));
      gg.addPoint(new ChartPoint(495,33));
      
      // create BLUE dummy serie
      ChartPointSerie bb = new ChartPointSerie(Color.BLUE,1);
      bb.addPoint(new ChartPoint(-98,-20));
      bb.addPoint(new ChartPoint(-49,-40));
      bb.addPoint(new ChartPoint(-5,Float.NaN));
      bb.addPoint(new ChartPoint(17,139));
      bb.addPoint(new ChartPoint(54,160));
      bb.addPoint(new ChartPoint(209,90));
      bb.addPoint(new ChartPoint(317,70));
      bb.addPoint(new ChartPoint(350,79));
      bb.addPoint(new ChartPoint(461,175));
      bb.addPoint(new ChartPoint(495,153));
      
      // add lines to LinePlotView
      vChart.addSerie(rr);
      vChart.addSerie(gg);
      vChart.addSerie(bb);
      
      return rootView;
   }
   
   private void getViews() {
      vChart = (XyChartView) rootView.findViewById(R.id.chart);
   }
}
