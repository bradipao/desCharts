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

import it.bradipao.lib.descharts.StyledChartPoint;
import it.bradipao.lib.descharts.StyledChartPointSerie;
import it.bradipao.lib.descharts.StyledXyChartView;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class StyledXyChartFragment extends Fragment {

   // views
   private View rootView;
   private StyledXyChartView vChart;
   
   // views : series color, size and dip
   ToggleButton tbS1,tbS2;
   Spinner spS1size,spS2size;
   CheckBox cbS1dip,cbS2dip;
   // views : border, axis and grid
   ToggleButton tbBorder,tbAxis,tbGrid;
   Spinner spBorderC,spAxisC,spGridC,spBorderW,spAxisW,spGridW;
   // views : dip and antialis
   CheckBox cbUseDip,cbUseAA;
   // views : X and Y text
   ToggleButton tbXtext,tbYtext,tbXtextBottom,tbYtextLeft;
   Spinner spTextC,spTextS;
   // views : background color
   Spinner spBkgC;
   
   // vars
   private String[] colors = {"BLACK","RED","GREEN","BLUE","VIOLET","YELLOW","WHITE","lt RED","lt GREEN","lt BLUE","lt VIOLET","lt YELLOW"};
   private int[] icolor = {Color.BLACK,0xffcc0000,0xff669900,0xff0099cc,0xff9933cc,0xffff8800,Color.WHITE,0xffff4444,0xff99cc00,0xff33bbee,0xffaa66cc,0xffffbb33};
   private String[] widths = {"0.5","1.0","1.5","2.0","2.5","3.0"};
   private float[] fwidth = {0.5f,1.0f,1.5f,2.0f,2.5f,3.0f};
   private String[] sizes = {"6.0","6.5","7.0","7.5","8.0","9.0","10.0"};
   private float[] fsizes = {6.0f,6.5f,7.0f,7.5f,8.0f,9.0f,10.0f};
   
   @Override
   public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

      // inflate layout
      rootView = inflater.inflate(R.layout.fr_styledxychart,container,false);
      // get views
      getViews();
      setupViews();
      setupListeners();
      
      // create FIRST dummy serie
      StyledChartPointSerie rr = new StyledChartPointSerie(2);
      rr.addPoint(new StyledChartPoint(-90, 99,0xff99cc00,0xffeeeeee));
      rr.addPoint(new StyledChartPoint(-49, 80,0xffff4444,0xffffcccc));
      rr.addPoint(new StyledChartPoint( -5,180,0xff99cc00,0xffeeff99));
      rr.addPoint(new StyledChartPoint( 17, 99,0xffffbb33,0xffffee99));
      rr.addPoint(new StyledChartPoint( 54, 80,0xff33bbee,0xffeeeeee));
      rr.addPoint(new StyledChartPoint(125,120,0xff99cc00,0xffeeeeee));
      rr.addPoint(new StyledChartPoint(158, 20,0xffff4444,0xffeeeeee));
      rr.addPoint(new StyledChartPoint(209, 50,0xffff4444,0xffffcccc));
      rr.addPoint(new StyledChartPoint(297,109,0xff33bbee,0xff99ddff));
      
      // create SECOND dummy serie
      StyledChartPointSerie gg = new StyledChartPointSerie(2);
      gg.addPoint(new StyledChartPoint( 17,-10,Color.BLACK,Color.TRANSPARENT,0xffff8800,5));
      gg.addPoint(new StyledChartPoint( 54, 20,Color.BLACK,Color.TRANSPARENT,0xffcc0000,5));
      gg.addPoint(new StyledChartPoint(125,-50,Color.BLACK,Color.TRANSPARENT,0xff669900,5));
      gg.addPoint(new StyledChartPoint(158, 89,Color.BLACK,Color.TRANSPARENT,Color.GRAY,8));
      gg.addPoint(new StyledChartPoint(209, 20,Color.BLACK,Color.TRANSPARENT,Color.GRAY,4));
      gg.addPoint(new StyledChartPoint(217,Float.NaN,Color.BLACK,Color.TRANSPARENT,Color.GRAY,4));
      gg.addPoint(new StyledChartPoint(250, 99,Color.BLACK,Color.TRANSPARENT,Color.GRAY,4));
      gg.addPoint(new StyledChartPoint(261, 75,Color.BLACK,Color.TRANSPARENT,Color.GRAY,4));
      gg.addPoint(new StyledChartPoint(295, 33,Color.BLACK,Color.TRANSPARENT,Color.GRAY,4));
      
      // add lines to chart
      vChart.addSerie(rr);
      vChart.addSerie(gg);
      
      return rootView;
   }
   
   private void getViews() {
      // chart view
      vChart = (StyledXyChartView) rootView.findViewById(R.id.chart);
      // series color, size and dip
      tbS1 = (ToggleButton) rootView.findViewById(R.id.tbSerie1);
      spS1size = (Spinner) rootView.findViewById(R.id.spSerie1Size);
      cbS1dip = (CheckBox) rootView.findViewById(R.id.cbSerie1Dip);
      tbS2 = (ToggleButton) rootView.findViewById(R.id.tbSerie2);
      spS2size = (Spinner) rootView.findViewById(R.id.spSerie2Size);
      cbS2dip = (CheckBox) rootView.findViewById(R.id.cbSerie2Dip);
      // grid visibility
      tbBorder = (ToggleButton) rootView.findViewById(R.id.tbBorder);
      tbAxis = (ToggleButton) rootView.findViewById(R.id.tbAxis);
      tbGrid = (ToggleButton) rootView.findViewById(R.id.tbGrid);
      // grid color spinner
      spBorderC = (Spinner) rootView.findViewById(R.id.spBorderC);
      spAxisC = (Spinner) rootView.findViewById(R.id.spAxisC);
      spGridC = (Spinner) rootView.findViewById(R.id.spGridC);
      // grid width spinner
      spBorderW = (Spinner) rootView.findViewById(R.id.spBorderW);
      spAxisW = (Spinner) rootView.findViewById(R.id.spAxisW);
      spGridW = (Spinner) rootView.findViewById(R.id.spGridW);
      // dip and antialias checkbox
      cbUseDip = (CheckBox) rootView.findViewById(R.id.cbUseDip);
      cbUseAA = (CheckBox) rootView.findViewById(R.id.cbUseAA);
      // text label
      tbXtext = (ToggleButton) rootView.findViewById(R.id.tbXtext);
      tbYtext = (ToggleButton) rootView.findViewById(R.id.tbYtext);
      tbXtextBottom = (ToggleButton) rootView.findViewById(R.id.tbXtextBottom);
      tbYtextLeft = (ToggleButton) rootView.findViewById(R.id.tbYtextLeft);
      // text color and size spinner
      spTextC = (Spinner) rootView.findViewById(R.id.spTextColor);
      spTextS = (Spinner) rootView.findViewById(R.id.spTextSize);
      // background color
      spBkgC = (Spinner) rootView.findViewById(R.id.spBkgColor);
   }
   
   private void setupViews() {
      // series
      tbS1.setChecked(true);
      tbS2.setChecked(true);
      ArrayAdapter<String> adS1size = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,widths);
      ArrayAdapter<String> adS2size = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,widths);
      adS1size.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      adS2size.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spS1size.setAdapter(adS1size);
      spS2size.setAdapter(adS2size);
      spS1size.setSelection(1);
      spS2size.setSelection(3);
      cbS1dip.setChecked(true);
      cbS2dip.setChecked(true);
      // border, axis, grid
      tbBorder.setChecked(true);
      tbAxis.setChecked(true);
      tbGrid.setChecked(true);
      ArrayAdapter<String> adBorderC = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,colors);
      ArrayAdapter<String> adAxisC = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,colors);
      ArrayAdapter<String> adGridC = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,colors);
      adBorderC.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      adAxisC.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      adGridC.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spBorderC.setAdapter(adBorderC);
      spAxisC.setAdapter(adAxisC);
      spGridC.setAdapter(adGridC);
      // grid color, size
      ArrayAdapter<String> adBorderW = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,widths);
      ArrayAdapter<String> adAxisW = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,widths);
      ArrayAdapter<String> adGridW = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,widths);
      adBorderW.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      adAxisW.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      adGridW.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spBorderW.setAdapter(adBorderW);
      spAxisW.setAdapter(adAxisW);
      spGridW.setAdapter(adGridW);
      spBorderW.setSelection(1);
      spAxisW.setSelection(1);
      spGridW.setSelection(1);
      // dip and antialias checkbox
      cbUseDip.setChecked(false);
      cbUseAA.setChecked(true);
      // text label
      tbXtext.setChecked(true);
      tbYtext.setChecked(true);
      tbXtextBottom.setChecked(true);
      tbYtextLeft.setChecked(true);
      // text color and size spinner
      ArrayAdapter<String> adTextC = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,colors);
      ArrayAdapter<String> adTextS = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,sizes);
      adTextC.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      adTextS.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spTextC.setAdapter(adTextC);
      spTextS.setAdapter(adTextS);
      spTextS.setSelection(2);
      
      // background color spinner
      ArrayAdapter<String> adBkgC = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,colors);
      adBkgC.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
      spBkgC.setAdapter(adBkgC);
      spBkgC.setSelection(6);
   }
   
   private void setupListeners() {
      // serie togglebutton listener
      OnClickListener mSerieListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            vChart.setLineVis(0,tbS1.isChecked());
            vChart.setLineVis(1,tbS2.isChecked());
         }
      };
      tbS1.setOnClickListener(mSerieListener);
      tbS2.setOnClickListener(mSerieListener);
      
      // serie color and size listener
      OnItemSelectedListener mSerieColorListener = new OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
            vChart.setLineStyle(0,fwidth[spS1size.getSelectedItemPosition()],cbS1dip.isChecked());
            vChart.setLineStyle(1,fwidth[spS2size.getSelectedItemPosition()],cbS2dip.isChecked());
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
         }
      };
      spS1size.setOnItemSelectedListener(mSerieColorListener);
      spS2size.setOnItemSelectedListener(mSerieColorListener);
      
      // serie dip checkbox listener
      OnCheckedChangeListener mSerieDipListener = new OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            vChart.setLineStyle(0,fwidth[spS1size.getSelectedItemPosition()],cbS1dip.isChecked());
            //vChart.setLineStyle(1,icolor[spS2color.getSelectedItemPosition()],fwidth[spS2size.getSelectedItemPosition()],cbS2dip.isChecked());
         }
      };
      cbS1dip.setOnCheckedChangeListener(mSerieDipListener);
      cbS2dip.setOnCheckedChangeListener(mSerieDipListener);
      
      // grid togglebutton listener
      OnClickListener mGridListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            vChart.setGridVis(tbBorder.isChecked(),tbGrid.isChecked(),tbAxis.isChecked());
         }
      };
      tbBorder.setOnClickListener(mGridListener);
      tbAxis.setOnClickListener(mGridListener);
      tbGrid.setOnClickListener(mGridListener);
      
      // grid color spinner listener
      OnItemSelectedListener mGridColorListener = new OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
            vChart.setGridColor(icolor[spBorderC.getSelectedItemPosition()],
                            icolor[spGridC.getSelectedItemPosition()],
                            icolor[spAxisC.getSelectedItemPosition()]);
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
         }
      };
      spBorderC.setOnItemSelectedListener(mGridColorListener);
      spGridC.setOnItemSelectedListener(mGridColorListener);
      spAxisC.setOnItemSelectedListener(mGridColorListener);
      
      // grid width spinner listener
      OnItemSelectedListener mGridWidthListener = new OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
            if (cbUseDip.isChecked()) vChart.setGridWidthDip(fwidth[spBorderW.getSelectedItemPosition()],
                  fwidth[spGridW.getSelectedItemPosition()],
                  fwidth[spAxisW.getSelectedItemPosition()]);
            else vChart.setGridWidth(fwidth[spBorderW.getSelectedItemPosition()],
                  fwidth[spGridW.getSelectedItemPosition()],
                  fwidth[spAxisW.getSelectedItemPosition()]);
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
         }
      };
      spBorderW.setOnItemSelectedListener(mGridWidthListener);
      spGridW.setOnItemSelectedListener(mGridWidthListener);
      spAxisW.setOnItemSelectedListener(mGridWidthListener);

      // usedip checkbox listener
      cbUseDip.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
               if (isChecked) vChart.setGridWidthDip(fwidth[spBorderW.getSelectedItemPosition()],
                     fwidth[spGridW.getSelectedItemPosition()],
                     fwidth[spAxisW.getSelectedItemPosition()]);
               else vChart.setGridWidth(fwidth[spBorderW.getSelectedItemPosition()],
                     fwidth[spGridW.getSelectedItemPosition()],
                     fwidth[spAxisW.getSelectedItemPosition()]);
         }
      });
      
      // grid antialias checkbox listener
      cbUseAA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
            vChart.setGridAA(isChecked);
         }
      });
      
      // text togglebutton listener
      OnClickListener mTextListener = new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            vChart.setTextVis(tbXtext.isChecked(),tbYtext.isChecked(),tbXtextBottom.isChecked(),tbYtextLeft.isChecked());
         }
      };
      tbXtext.setOnClickListener(mTextListener);
      tbYtext.setOnClickListener(mTextListener);
      tbXtextBottom.setOnClickListener(mTextListener);
      tbYtextLeft.setOnClickListener(mTextListener);
      
      // text color and size spinner
      OnItemSelectedListener mTextStyleListener = new OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
            vChart.setTextStyle(icolor[spTextC.getSelectedItemPosition()],
                            fsizes[spTextS.getSelectedItemPosition()]);
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
         }
      };
      spTextC.setOnItemSelectedListener(mTextStyleListener);
      spTextS.setOnItemSelectedListener(mTextStyleListener);
      
      // background color listener
      OnItemSelectedListener mBkgColorListener = new OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
            vChart.setBackgroundColor(icolor[spBkgC.getSelectedItemPosition()]);
         }
         @Override
         public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
         }
      };
      spBkgC.setOnItemSelectedListener(mBkgColorListener);
      
   }
   
}
