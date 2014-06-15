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
* ChartPointSerie() class represents a line graph to be plotted. 
* It contains dataset of X,Y couples (ChartPoint class) and
* some graphical properties of line (color and width).
*/

public class ChartPointSerie {

   // data holder
   public ArrayList<ChartPoint> mPointList = new ArrayList<ChartPoint>();
   
   // color, width, unit for width (pixel or dip)
   public int mColor = 0xFF000000;
   public float mWidth = 2;
   public boolean mUseDip = true;
   
   // minimum and maximum for X and Y
   public float mXmin=0,mXmax=1,mYmin=0,mYmax=1;
   
   // params
   private boolean mShow = true;
   private boolean mOrderonx = false;
   private boolean mAutoXminmax = true;
   private boolean mAutoYminmax = true;
   
   /**
   * Constructor, properties are default.
   */
   public ChartPointSerie() {
   }

   /**
   * Constructor, properties are default except color.
   */
   public ChartPointSerie(int color) {
      mColor = color;
   }

   /**
   * Constructor, properties are default except color and width.
   */
   public ChartPointSerie(int color,float width) {
      mColor = color;
      mWidth = width;
   }
   
   /**
   * Constructor, color, width and usedip are provided.
   */
   public ChartPointSerie(int color,float width,boolean usedip) {
      mColor = color;
      mWidth = width;
      mUseDip = usedip;
   }
   
   /**
   * Returns the ArrayList of points dataset.
   */
   public ArrayList<ChartPoint> getPointList() {
      return mPointList;
   }
   
   /**
   * Sets the ArrayList of points dataset.
   */
   public void setPointList(ArrayList<ChartPoint> points) {
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
   * If the mOrderonx property is not enabled, point is added at the end
   * of the ArrayList, else it is added in the position that preserves
   * crescent ordering of X coordinate.
   */
   public void addPoint(ChartPoint point){
      // update range first (internal calculation)
      if (mAutoXminmax) {
         if (mPointList.size()>0) {
            if (point.x>mXmax) mXmax = point.x;
            else if (point.x<mXmin) mXmin = point.x;
         } else mXmin = mXmax = point.x;
      }
      if (mAutoYminmax) {
         if (mPointList.size()>0) {
            if (point.y>mYmax) mYmax = point.y;
            else if (point.y<mYmin) mYmin = point.y;
         } else mYmin = mYmax = point.y;
      }
      // if not automatic ordering on X, add and return
      if (!mOrderonx) {
         mPointList.add(point);
         return;
      }
      // else find position and add
      ChartPoint p;
      int i;
      for(i=0;i<mPointList.size();i++){
         p = mPointList.get(i);
         if (point.x<p.x){
            mPointList.add(i,point);
            return;
         }
      }
      mPointList.add(point);
   }
   
   /**
   * Shifts a point to the ArrayList of points dataset.
   * Adding is done calling addPoint(), then points are removed
   * from the beginning of the list untile there are maximum
   * max points in the list.
   */
   public void shiftPoint(ChartPoint point,int max){
      addPoint(point);
      while (mPointList.size()>max) mPointList.remove(0);
      if (mAutoXminmax||mAutoYminmax) calcRanges();
   }
   
   /**
   * Removes a point from the points dataset.
   */
   public void removePoint(ChartPoint point){
      mPointList.remove(point);
      if (mAutoXminmax||mAutoYminmax) calcRanges();
   }
   
   /**
   * Returns a point from the points dataset.
   */
   public ChartPoint getPoint(int index){
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
      // auto X min/max calculation
      if (mAutoXminmax) {
         mXmin = mPointList.get(0).x;
         mXmax = mPointList.get(0).x;   
         for (i=1;i<mPointList.size();i++) {
            if (mPointList.get(i).x>mXmax) mXmax = mPointList.get(i).x;
            else if (mPointList.get(i).x<mXmin) mXmin = mPointList.get(i).x;
         }
      }
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
   public void setAutoMinmax(boolean bAutoX,boolean bAutoY) {
      this.mAutoXminmax = bAutoX;
      this.mAutoYminmax = bAutoY;
      if (bAutoX||bAutoY) calcRanges();
   }
   
   /**
   * Setter for AutoMinMax properties and values.
   */
   public void setAutoMinmax(boolean bAutoX,boolean bAutoY,float fXmin,float fXmax,float fYmin,float fYmax) {
      this.mAutoXminmax = bAutoX;
      this.mAutoYminmax = bAutoY;
      if (!bAutoX) {
         this.mXmin = fXmin;
         this.mXmax = fXmax;         
      }
      if (!bAutoY) {
         this.mYmin = fYmin;
         this.mYmax = fYmax;         
      }
      if (bAutoX||bAutoY) calcRanges();
   }
   
   /**
   * Setter for OrderOnX property.
   */
   public void setOrderOnX(boolean bOrderonx) {
      if (mPointList.size()>0) return;
      this.mOrderonx = bOrderonx;
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
