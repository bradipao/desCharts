/*******************************************************************************
 * Descharts library
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
 *******************************************************************************/

package it.bradipao.lib.descharts;

import java.util.ArrayList;

/**
* ChartValueSerie() class represents a line graph to be plotted. 
* It contains dataset of LABEL,Y couples (ChartValue class) and
* some graphical properties of line (color and width).
*/

public class ChartValueSerie {

   // data holder
   public ArrayList<ChartValue> mPointList = new ArrayList<ChartValue>();
   
   // color, width, unit for width (pixel or dip)
   public int mColor = 0xFF000000;
   public float mWidth = 2;
   public boolean mUseDip = true;
   
   // minimum and maximum for Y
   public float mYmin=0,mYmax=1;
   
   // params
   private boolean mShow = true;
   private boolean mAutoYminmax = true;
   
   /**
   * Constructor, properties are default.
   */
   public ChartValueSerie() {
   }

   /**
   * Constructor, properties are default except color.
   */
   public ChartValueSerie(int color) {
      mColor = color;
   }

   /**
   * Constructor, properties are default except color and width.
   */
   public ChartValueSerie(int color,float width) {
      mColor = color;
      mWidth = width;
   }
   
   /**
   * Constructor, color, width and usedip are provided.
   */
   public ChartValueSerie(int color,float width,boolean usedip) {
      mColor = color;
      mWidth = width;
      mUseDip = usedip;
   }
   
   /**
   * Returns the ArrayList of points dataset.
   */
   public ArrayList<ChartValue> getPointList() {
      return mPointList;
   }
   
   /**
   * Sets the ArrayList of points dataset.
   */
   public void setPointList(ArrayList<ChartValue> points) {
      this.mPointList = points;
   }
   
   /**
   * Clears the ArrayList of points dataset.
   */
   public void clearPointList() {
      this.mPointList.clear();
   }
   
   /**
   * Adds a point to the ArrayList of points dataset.
   */
   public void addPoint(ChartValue point){
      // update range first
      if (mAutoYminmax) {
         if (mPointList.size()>0) {
            if (point.y>mYmax) mYmax = point.y;
            else if (point.y<mYmin) mYmin = point.y;
         } else mYmin = mYmax = point.y;
      }
      // add point
      mPointList.add(point);
   }
   
   /**
   * Shifts a point to the ArrayList of points dataset.
   * Adding is done calling addPoint(), then points are removed
   * from the beginning of the list untile there are maximum
   * max points in the list.
   */
   public void shiftPoint(ChartValue point,int max){
      addPoint(point);
      while (mPointList.size()>max) mPointList.remove(0);
      if (mAutoYminmax) calcRanges();
   }
   
   /**
   * Removes a point from the points dataset.
   */
   public void removePoint(ChartValue point){
      mPointList.remove(point);
      if (mAutoYminmax) calcRanges();
   }
   
   /**
   * Returns a point from the points dataset.
   */
   public ChartValue getPoint(int index){
      return mPointList.get(index);
   }
   
   /**
   * Returns size of the points dataset.
   */
   public int getSize(){
      return mPointList.size();
   }

   /**
   * Calculates maximum and minimum of both X and Y coordinates
   * in the points dataset.
   */
   private void calcRanges() {
      int i;
      if (mPointList.size()==0) return;
      // auto Y calculation
      if (mAutoYminmax) {
         mYmin = mPointList.get(0).y;
         mYmax = mPointList.get(0).y;
         for (i=1;i<mPointList.size();i++) {
            if (mPointList.get(i).y>mYmax) mYmax = mPointList.get(i).y;
            else if (mPointList.get(i).y<mYmin) mYmin = mPointList.get(i).y;
         }
      }      
   }
   
   /**
   * Setter for AutoMinMax properties.
   */
   public void setAutoMinmax(boolean bAutoY) {
      this.mAutoYminmax = bAutoY;
      if (bAutoY) calcRanges();
   }
   
   /**
   * Setter for AutoMinMax properties and values.
   */
   public void setAutoMinmax(boolean bAutoY,float fYmin,float fYmax) {
      this.mAutoYminmax = bAutoY;
      if (!bAutoY) {
         this.mYmin = fYmin;
         this.mYmax = fYmax;         
      }
      if (bAutoY) calcRanges();
   }
   
   /**
   * Setter for Show property.
   */
   public void setVisible(boolean bShow) {
      this.mShow = bShow;
   }
   
   /**
   * Returns state of Show property.
   */
   public boolean isVisible() {
      return this.mShow;
   }
   
   /**
   * Setter for Color and Width properties.
   */
   public void setStyle(int iColor,float fWidth) {
      mColor = iColor;
      mWidth = fWidth;
   }
   
   /**
   * Setter for Color, Width and UseDip properties.
   */
   public void setStyle(int iColor,float fWidth,boolean bUsedip) {
      mColor = iColor;
      mWidth = fWidth;
      mUseDip = bUsedip;
   }
    
}
