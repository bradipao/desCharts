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
 ******************************************************************************/

package it.bradipao.lib.descharts;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;

/**
* XyChartView() class represents an XY graph widget.
*/

public class XyChartView extends CartesianView {

   // data holder
   private ArrayList<ChartPointSerie> mSeries = new ArrayList<ChartPointSerie>();

   // objects
   private Paint mPnt = new Paint();
   
   /** 
    * Constructor.
    */
   public XyChartView(Context context){
      super(context);
      initPaint();
   }
   
   /** 
    * Constructor.
    */
   public XyChartView(Context context,AttributeSet attrs) {
      super(context,attrs);
      initPaint();
   }
   
   /** 
    * Draws the plot.
    */
   public void onDraw(Canvas cnv) {

      // create cache bitmap if necessary
      if ((mBmp==null)||(bRedraw)) {
         
         // get view sizes
         getViewSizes();
         // get min,max data ranges
         getXYminmax();
         // calculate the adjusted grid ranges and drawing parameters
         if (p_xscale_auto) calcXgridRange();
         if (p_yscale_auto) calcYgridRange();
         // calculate drawing coefficients
         calcXYcoefs();

         // create bitmap and canvas to draw
         mBmp = Bitmap.createBitmap(p_width,p_height,Config.ARGB_8888);
         mCnv = new Canvas(mBmp);
         
         // draw inner grid
         if (p_grid_vis) drawGrid();
         // label on X axis
         if (p_xtext_vis) drawXlabel();
         // label on Y axis
         if (p_ytext_vis) drawYlabel();
         // draw outer border
         if (p_border_vis) drawBorder();
         // draw zero axis if internal
         if (p_axis_vis) drawAxis();
         // draw data
         drawData();
         
         // bitmap refresh complete
         bRedraw = false;
      }
      
      // draw bitmap
      cnv.drawBitmap(mBmp,0,0,null);
   }
   
   /** 
    * Clears ArrayList of series.
    */
   public void clearSeries(){
      while (mSeries.size()>0){
         mSeries.remove(0);
      }
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Adds a serie to data holder ArrayList.
    */
   public void addSerie(ChartPointSerie serie) {
      mSeries.add(serie);
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Returns ArrayList of series.
    */
   public ArrayList<ChartPointSerie> getSeries() {
      return mSeries;
   }
   
   /** 
    * Sets line visibility.
    */
   public void setLineVis(int index,boolean show) {
      mSeries.get(index).setVisible(show);
      bRedraw = true;
      postInvalidate();
   }
   
   /**
    * Sets line color and size.
    */
   public void setLineStyle(int index,int color,float size) {
      mSeries.get(index).setStyle(color,size);
      bRedraw = true;
      postInvalidate();
   }

   /**
    * Sets line color and size and dip.
    */
   public void setLineStyle(int index,int color,float size,boolean usedip) {
      mSeries.get(index).setStyle(color,size,usedip);
      bRedraw = true;
      postInvalidate();
   }

   /** 
    * Gets X,Y ranges across all series
    */
   protected void getXYminmax() {
      ChartPointSerie serie;
      for (ii=0;ii<mSeries.size();ii++) {
         serie = mSeries.get(ii);
         if (ii==0) {
            mXmin = serie.mXmin;
            mXmax = serie.mXmax;
            mYmin = serie.mYmin;
            mYmax = serie.mYmax;
         } else {
            if (serie.mXmin<mXmin) mXmin = serie.mXmin;
            if (serie.mXmax>mXmax) mXmax = serie.mXmax;
            if (serie.mYmin<mYmin) mYmin = serie.mYmin;
            if (serie.mYmax>mYmax) mYmax = serie.mYmax;     
         }
      }
   }
   
   /** 
    * Draw data from all series
    */
   protected void drawData() {
      float pX,pY;
      boolean pValid;
      ChartPoint point;
      for (ChartPointSerie serie : mSeries) { 
         if (serie.isVisible()) {
            // set paint
            mPnt.reset();
            mPnt.setStyle(Paint.Style.STROKE);
            mPnt.setColor(serie.mColor);
            if (serie.mUseDip) mPnt.setStrokeWidth(dipToPixel(serie.mWidth));
            else mPnt.setStrokeWidth(serie.mWidth);
            mPnt.setAntiAlias(true);
            // iterate through points
            pValid = false;
            mPath.reset();
            for (ii=0;ii<serie.mPointList.size();ii++) {
               point = serie.mPointList.get(ii);
               pX = point.x;
               pY = point.y;
               if (Float.isNaN(pX)||Float.isNaN(pY)) {
                  pValid = false;
               } else if (!pValid) {
                  mPath.moveTo(sX+(pX-bX)*aX,eY-(pY-bY)*aY);
                  pValid = true;
               } else {
                  mPath.lineTo(sX+(pX-bX)*aX,eY-(pY-bY)*aY);
               }
            }
            // draw line
            mCnv.drawPath(mPath,mPnt);
         }
      }
   }

}
