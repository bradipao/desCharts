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
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

/**
* StackedLineChartView() class represents a stacke line graph widget.
*/

public class StackedLineChartView extends CartesianView {

   // data holder
   private ArrayList<ChartValueSerie> mSeries = new ArrayList<ChartValueSerie>();
   private ChartValueSerie mStacked = new ChartValueSerie();
   private int mXnum=0;

   // objects
   private Paint mPnt = new Paint();
   private Paint mPntFill = new Paint();
   private Path mPathFill;
   
   /** 
    * Constructor.
    */
   public StackedLineChartView(Context context){
      super(context);
      initPaint();
   }
   
   /** 
    * Constructor.
    */
   public StackedLineChartView(Context context,AttributeSet attrs) {
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
         if (p_yscale_auto) calcYgridRange();
         // calculate drawing coefficients
         calcXYcoefs();

         // create bitmap and canvas to draw
         mBmp = Bitmap.createBitmap(p_width,p_height,Config.ARGB_8888);
         mCnv = new Canvas(mBmp);

         // draw data
         drawData();
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
   public void addSerie(ChartValueSerie serie) {
      mSeries.add(serie);
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Returns ArrayList of series.
    */
   public ArrayList<ChartValueSerie> getSeries() {
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
    * Sets line color, fillcolor and size and dip.
    */
   public void setLineStyle(int index,int color,int fillcolor,float size,boolean usedip) {
      mSeries.get(index).setStyle(color,fillcolor,size,usedip);
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Gets X,Y ranges across all series
    */
   protected void getXYminmax() {
      // calculate stacked serie
      calcStackedSerie();
      // calculate minmax
      mXnum = mStacked.getSize();
      mYmax = mStacked.mYmax;
      ChartValueSerie serie;
      for (ii=0;ii<mSeries.size();ii++) {
         serie = mSeries.get(ii);
         if (ii==0) {
            mYmin = serie.mYmin;
         } else {
            if (serie.mYmin<mYmin) mYmin = serie.mYmin;
         }
      }
   }
   
   /** 
    * Draw data from all series
    */
   protected void drawData() {
      ChartValueSerie serie;
      ChartValue v;
      float pY;
      for (ii=mSeries.size()-1;ii>=0;ii--) {
         serie = mSeries.get(ii);
         if (serie.isVisible()) {
            // set paint
            mPnt.reset();
            mPnt.setStyle(Paint.Style.STROKE);
            mPnt.setColor(serie.mColor);
            mPntFill.reset();
            mPntFill.setStyle(Paint.Style.FILL);
            mPntFill.setColor(serie.mFillColor);
            Log.d("CHART","fillcolor: "+serie.mFillColor);
            if (serie.mUseDip) mPnt.setStrokeWidth(dipToPixel(serie.mWidth));
            else mPnt.setStrokeWidth(serie.mWidth);
            mPnt.setAntiAlias(true);
            mPntFill.setAntiAlias(false);
            // iterate through points
            for (jj=0;jj<mStacked.mPointList.size();jj++) {
               v = mStacked.mPointList.get(jj);
               pY = v.y;
               if (jj==0) {
                  mPath.reset();
                  mPath.moveTo(sX+bX+jj*aX,eY-(pY-bY)*aY);
               } else {
                  mPath.lineTo(sX+bX+jj*aX,eY-(pY-bY)*aY);
               }
               mStacked.updatePoint(jj,v.y-serie.getPoint(jj).y);
            }
            // create fill path and draw if requested
            if (serie.mFillColor!=0) {
               mPathFill = new Path(mPath);
               mPathFill.lineTo(sX+bX+(mStacked.mPointList.size()-1)*aX,eY);
               mPathFill.lineTo(sX+bX,eY);
               mPathFill.close();
               mCnv.drawPath(mPathFill,mPntFill);
            }
            // draw line
            mCnv.drawPath(mPath,mPnt);
            
         }
      }
   }
     
   /** 
    * Calculates drawing coefficients
    */
   protected void calcXYcoefs() {
      aX = (float) dX/mXnum;
      bX = (float) aX/2;
      aY = (float) dY/Math.abs(mYmaxGrid-mYminGrid);
      bY = (float) mYminGrid;
   }
   
   /** 
    * Draw X label on top or bottom
    */
   protected void drawXlabel() {
      mPntText.setTextAlign(Paint.Align.CENTER);
      mPath.reset();
      ChartValueSerie mLabel = mSeries.get(0);
      String label;
      int numlab = mLabel.getSize();
      int numdiv = 1 + numlab/10;      // TODO add customizable number of labels
      if (p_xtext_bottom) {
         for (ii=0;ii<mLabel.getSize();ii++) {
            mPath.moveTo(sX+bX+ii*aX,eY-3);
            mPath.lineTo(sX+bX+ii*aX,eY+3);
            label = mLabel.mPointList.get(ii).t;
            if ((label!=null)&&(ii<numlab)&&((ii%numdiv)==0))
               mCnv.drawText(label,sX+bX+ii*aX,eY+p_text_size+2,mPntText);
         }
      } else {
         for (ii=0;ii<mLabel.getSize();ii++) {
            mPath.moveTo(sX+bX+ii*aX,sY-3);
            mPath.lineTo(sX+bX+ii*aX,sY+3);
            label = mLabel.mPointList.get(ii).t;
            if ((label!=null)&&(ii<numlab)&&((ii%numdiv)==0))
               mCnv.drawText(label,sX+bX+ii*aX,sY-p_text_size+3,mPntText);
         }               
      }
      mCnv.drawPath(mPath,mPntAxis);
   }   

   /** 
    * Calculates cumulated values of all series stacked
    */
   protected void calcStackedSerie() {
      // return if no serie exists
      if (mSeries.size()==0) return;
      // clear Stacked serie
      mStacked.clearPointList();
      // else iterate through values of first serie
      ChartValueSerie f = mSeries.get(0);
      float acc = 0;
      for (ii=0;ii<f.getSize();ii++) {
         // first serie always exists
         if (f.isVisible()) acc = f.getPoint(ii).y;
         else acc = 0;
         // add next series if present
         for (jj=1;jj<mSeries.size();jj++) {
            if ((mSeries.get(jj).isVisible())&&(ii<mSeries.get(jj).getSize()))
               acc += mSeries.get(jj).getPoint(ii).y;
         }
         // store in Stacked
         mStacked.addPoint(new ChartValue(null,acc));
      }
   }
   
}
