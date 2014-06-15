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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CartesianView extends View {

   // view params
   int p_width = 0;
   int p_height = 0;
   int p_paddtop = 8;
   int p_paddright = 8;
   int p_paddbottom = 8;
   int p_paddleft = 8;
   
   // visibility and appearance
   boolean p_xscale_auto = true;
   boolean p_yscale_auto = true;
   boolean p_border_vis = true;
   boolean p_grid_vis = true;
   boolean p_axis_vis = true;
   boolean p_xtext_vis = true;
   boolean p_ytext_vis = true;
   boolean p_xtext_bottom = true;
   boolean p_ytext_left = true;
   boolean p_grid_aa = true;
   
   // color params
   int p_background_color = 0xFFFFFFFF;
   int p_border_color = 0xFF000000;
   int p_grid_color = 0xFF000000;
   int p_axis_color = 0xFF000000;
   int p_text_color = 0xFF000000;
   
   // size params
   float p_border_width = dipToPixel(1.0f);
   float p_grid_width = dipToPixel(1.0f);
   float p_axis_width = dipToPixel(1.0f);
   float p_text_size = dipToPixel(7.0f);
   
   // vars
   boolean bRedraw = false;
   // chart area coordinates
   float sX,sY,dX,dY,eX,eY;
   // limits for X and Y data
   float mXmin,mXmax,mYmin,mYmax;
   // limits for grid
   float mXminGrid,mXmaxGrid,mYminGrid,mYmaxGrid,mXdivGrid,mYdivGrid;
   int mXgridNum,mYgridNum;
   // drawing coefficients
   float aX,bX,aY,bY;
   
   // objects
   Canvas mCnv = null;
   Bitmap mBmp = null;
   Paint mPntBorder = new Paint();
   Paint mPntGrid = new Paint();
   Paint mPntAxis = new Paint();
   Paint mPntText = new Paint();
   Typeface mFontText = Typeface.create("sans-serif-condensed",Typeface.NORMAL);
   Path mPath = new Path();
   
   int ii,jj;
   float ff;
   
   /** 
    * Constructor.
    */
   public CartesianView(Context context){
      super(context);
      initPaint();
   }
   
   /** 
    * Constructor.
    */
   public CartesianView(Context context,AttributeSet attrs) {
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
         
         // bitmap refresh complete
         bRedraw = false;
      }
      
      // draw bitmap
      cnv.drawBitmap(mBmp,0,0,null);
   }
   
   /** 
    * Sets view padding.
    */
   public void setPadding(int pad) {
      p_paddtop = pad;
      p_paddright = pad;
      p_paddbottom = pad;
      p_paddleft = pad;
   }

   /** 
    * Sets view padding in DIP.
    */
   public void setPaddingDip(int pad) {
      p_paddtop = (int) dipToPixel(pad);
      p_paddright = (int) dipToPixel(pad);
      p_paddbottom = (int) dipToPixel(pad);
      p_paddleft = (int) dipToPixel(pad);
   }
   
   /** 
    * Sets view padding.
    */
   public void setPadding(int paddtop,int padright,int paddbot,int padleft) {
      p_paddtop = paddtop;
      p_paddright = padright;
      p_paddbottom = paddbot;
      p_paddleft = padleft;
   }

   /** 
    * Sets view padding in DIP.
    */
   public void setPaddingDip(int paddtop,int padright,int paddbot,int padleft) {
      p_paddtop = (int) dipToPixel(paddtop);
      p_paddright = (int) dipToPixel(padright);
      p_paddbottom = (int) dipToPixel(paddbot);
      p_paddleft = (int) dipToPixel(padleft);
   }
   
   /** 
    * Sets view background color.
    */
   public void setBackgroundColor(int color) {
      p_background_color = color;
      super.setBackgroundColor(color);
   }
   
   /** 
    * Sets X grid autoscale and (if false) grid range and number of steps.
    */
   public void setXgrid(boolean autoXscale,float xmin,float xmax,int num) {
      p_xscale_auto = autoXscale;
      if (!autoXscale) {
         mXminGrid = xmin;
         mXmaxGrid = xmax;
         mXgridNum = num;
         mXdivGrid = (xmax-xmin)/num;
      }
      postInvalidate();
   }
   
   /** 
    * Sets Y grid autoscale and (if false) grid range and number of steps.
    */
   public void setYgrid(boolean autoYscale,float ymin,float ymax,int num) {
      p_yscale_auto = autoYscale;
      if (!autoYscale) {
         mYminGrid = ymin;
         mYmaxGrid = ymax;
         mYgridNum = num;
         mYdivGrid = (ymax-ymin)/num;
      }
      postInvalidate();
   }
   
   /** 
    * Sets border, grid and axis visibility.
    */
   public void setGridVis(boolean bBorderShow,boolean bGridShow,boolean bAxisShow) {
      p_border_vis = bBorderShow;
      p_grid_vis = bGridShow;
      p_axis_vis = bAxisShow;
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Sets border, grid and axis color.
    */
   public void setGridColor(int borderColor,int gridColor,int axisColor) {
      p_border_color = borderColor;
      p_grid_color = gridColor;
      p_axis_color = axisColor;
      initPaint();
      bRedraw = true;
      postInvalidate();
   }

   /** 
    * Sets border, grid and axis width.
    */
   public void setGridWidth(float borderWidth,float gridWidth,float axisWidth) {
      p_border_width = borderWidth;
      p_grid_width = gridWidth;
      p_axis_width = axisWidth;
      initPaint();
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Sets border, grid and axis width in DIP.
    */
   public void setGridWidthDip(float borderWidth,float gridWidth,float axisWidth) {
      p_border_width = dipToPixel(borderWidth);
      p_grid_width = dipToPixel(gridWidth);
      p_axis_width = dipToPixel(axisWidth);
      initPaint();
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Enables AntiAlias rendering for border, grid and axis.
    */
   public void setGridAA(boolean antialias) {
      p_grid_aa = antialias;
      initPaint();
      bRedraw = true;
      postInvalidate();
   }

   /** 
    * Sets X text and Y text visibility and position.
    */
   public void setTextVis(boolean xtext,boolean ytext,boolean xbottom,boolean yleft) {
      p_xtext_vis = xtext;
      p_ytext_vis = ytext;
      p_xtext_bottom = xbottom;
      p_ytext_left = yleft;
      bRedraw = true;
      postInvalidate();
   }
   
   /**
    * Sets text color and size.
    */
   public void setTextStyle(int color,float size) {
      p_text_color = color;
      p_text_size = dipToPixel(size);
      initPaint();
      bRedraw = true;
      postInvalidate();
   }
   
   /** 
    * Inits Paint objects
    */
   protected void initPaint() {
      mPntBorder.setStyle(Paint.Style.STROKE);
      mPntBorder.setColor(p_border_color);
      mPntBorder.setStrokeWidth(p_border_width);
      mPntBorder.setAntiAlias(p_grid_aa);
      mPntGrid.setStyle(Paint.Style.STROKE);
      mPntGrid.setColor(p_grid_color);
      mPntGrid.setStrokeWidth(p_grid_width);
      mPntGrid.setPathEffect(new DashPathEffect(new float[] {2,2},0));
      mPntGrid.setAntiAlias(p_grid_aa);
      mPntAxis.setStyle(Paint.Style.STROKE);
      mPntAxis.setColor(p_axis_color);
      mPntAxis.setStrokeWidth(p_axis_width);
      mPntAxis.setAntiAlias(p_grid_aa);
      mPntText.setColor(p_text_color);
      mPntText.setTypeface(mFontText);
      mPntText.setTextSize(p_text_size);
      mPntText.setStyle(Paint.Style.FILL);
      mPntText.setAntiAlias(true);
      setBackgroundColor(p_background_color);
   }
   
   /** 
    * Gets view sizes
    */
   protected void getViewSizes() {
      // params from view
      p_width = getWidth();
      p_height = getHeight();
      sX = p_paddleft;
      sY = p_paddtop;
      eX = p_width - p_paddright;
      eY = p_height - p_paddbottom;
      // adjust depending on text labels
      if (p_ytext_vis &&  p_ytext_left)   sX += 3*p_text_size;
      if (p_ytext_vis && !p_ytext_left)   eX -= 3*p_text_size;
      if (p_xtext_vis &&  p_xtext_bottom) eY -= p_text_size+2;
      if (p_xtext_vis && !p_xtext_bottom) sY += p_text_size+2;
      // chart area range
      dX = eX - sX;
      dY = eY - sY;
   }
   
   /** 
    * Sets dummy values for X,Y ranges
    */
   protected void getXYminmax() {
      mXmin = -9;
      mXmax = 9;
      mYmin = -90;
      mYmax = 90;
   }
   
   /** 
    * Automatic calculation of X grid range
    */
   protected void calcXgridRange() {
      // grid step (10's power lower than current range)
      mXdivGrid = (float) Math.pow(10,Math.floor(Math.log10(Math.abs(mXmax-mXmin))));
      // align Xmin to the left-most grid
      mXminGrid = (float)(mXdivGrid*Math.floor(mXmin/mXdivGrid));
      // align Xmax to the right-most grid
      mXmaxGrid = (float)(mXdivGrid*Math.ceil(mXmax/mXdivGrid));
      // gridnum is always between 2 and 10
      mXgridNum = (int)((mXmaxGrid-mXminGrid)/mXdivGrid);
      // slightly adjust for form-factor, to avoid rectangular grids (square better)
      if ((dX/dY)<1.2) {
         if (mXgridNum<=2) mXgridNum *= 5;
         else if (mXgridNum==3) mXgridNum *= 3;
         else if (mXgridNum<=5) mXgridNum *= 2;
      } else {
         if (mXgridNum<=2) mXgridNum *= 6;
         else if (mXgridNum==3) mXgridNum *= 4;
         else if (mXgridNum==4) mXgridNum *= 3;
         else if (mXgridNum<=6) mXgridNum *= 2;         
      }
   }
   
   /** 
    * Automatic calculation of Y grid range
    */
   protected void calcYgridRange() {
      // grid step (10's power lower than current range)
      mYdivGrid = (float) Math.pow(10,Math.floor(Math.log10(Math.abs(mYmax-mYmin))));
      // align Ymin to the left-most grid
      mYminGrid = (float)(mYdivGrid*Math.floor(mYmin/mYdivGrid));
      // align Ymax to the right-most grid
      mYmaxGrid = (float)(mYdivGrid*Math.ceil(mYmax/mYdivGrid));
      // gridnum is always between 2 and 10
      mYgridNum = (int)((mYmaxGrid-mYminGrid)/mYdivGrid);
      // slightly adjust for form-factor, to avoid rectangular grids (square better)
      if ((dY/dX)<1.2) {
         if (mYgridNum<=2) mYgridNum *= 5;
         else if (mYgridNum<=3) mYgridNum *= 3;
         else if (mYgridNum<=5) mYgridNum *= 2;
      } else {
         if (mYgridNum<=2) mYgridNum *= 6;
         else if (mYgridNum==3) mYgridNum *= 4;
         else if (mYgridNum==4) mYgridNum *= 3;
         else if (mYgridNum<=6) mYgridNum *= 2;         
      }
   }
   
   /** 
    * Calculates drawing coefficients
    */
   protected void calcXYcoefs() {
      aX = (float) dX/Math.abs(mXmaxGrid-mXminGrid);
      bX = (float) mXminGrid;
      aY = (float) dY/Math.abs(mYmaxGrid-mYminGrid);
      bY = (float) mYminGrid;
   }
   
   /** 
    * Draw inner grid
    */
   protected void drawGrid() {
      mPath.reset();
      for (ii=1;ii<mXgridNum;ii++) {
         mPath.moveTo(sX+ii*(dX/mXgridNum),sY);
         mPath.lineTo(sX+ii*(dX/mXgridNum),eY);
      }
      for (ii=1;ii<mYgridNum;ii++) {
         mPath.moveTo(sX,sY+ii*(dY/mYgridNum));
         mPath.lineTo(eX,sY+ii*(dY/mYgridNum));
      }
      mCnv.drawPath(mPath,mPntGrid);
   }
   
   /** 
    * Draw X label on top or bottom
    */
   protected void drawXlabel() {
      mPntText.setTextAlign(Paint.Align.CENTER);
      mPath.reset();
      if (p_xtext_bottom) {
         for (ii=1;ii<mXgridNum;ii++) {
            mPath.moveTo(sX+ii*(dX/mXgridNum),eY-3);
            mPath.lineTo(sX+ii*(dX/mXgridNum),eY+3);
            ff = mXminGrid + ii*(mXmaxGrid-mXminGrid)/mXgridNum;
            mCnv.drawText(String.format("%.1f",ff),sX+ii*(dX/mXgridNum),eY+p_text_size+2,mPntText);
         }
      } else {
         for (ii=1;ii<mXgridNum;ii++) {
            mPath.moveTo(sX+ii*(dX/mXgridNum),sY-3);
            mPath.lineTo(sX+ii*(dX/mXgridNum),sY+3);
            ff = mXminGrid + ii*(mXmaxGrid-mXminGrid)/mXgridNum;
            mCnv.drawText(String.format("%.1f",ff),sX+ii*(dX/mXgridNum),sY-p_text_size+2,mPntText);
         }               
      }
      mCnv.drawPath(mPath,mPntAxis);
   }
   
   /** 
    * Draw Y label on left or right
    */
   protected void drawYlabel() {
      if (p_ytext_left) mPntText.setTextAlign(Paint.Align.RIGHT);
      else mPntText.setTextAlign(Paint.Align.LEFT);
      mPath.reset();
      if (p_ytext_left) {
         for (ii=1;ii<mYgridNum;ii++) {
            mPath.moveTo(sX-3,eY-ii*(dY/mYgridNum));
            mPath.lineTo(sX+3,eY-ii*(dY/mYgridNum));
            ff = mYminGrid + ii*(mYmaxGrid-mYminGrid)/mYgridNum;
            mCnv.drawText(String.format("%.1f",ff),sX-6,eY-ii*(dY/mYgridNum)+p_text_size/2,mPntText);
         }
      } else {
         for (ii=1;ii<mYgridNum;ii++) {
            mPath.moveTo(eX-3,eY-ii*(dY/mYgridNum));
            mPath.lineTo(eX+3,eY-ii*(dY/mYgridNum));
            ff = mYminGrid + ii*(mYmaxGrid-mYminGrid)/mYgridNum;
            mCnv.drawText(String.format("%.1f",ff),eX+6,eY-ii*(dY/mYgridNum)+p_text_size/2,mPntText);
         }
      }
      mCnv.drawPath(mPath,mPntAxis);
   }
   
   /** 
    * Draw outer border
    */
   protected void drawBorder() {
      mPath.reset();
      mPath.moveTo(sX,sY);
      mPath.lineTo(eX,sY);
      mPath.lineTo(eX,eY);
      mPath.lineTo(sX,eY);
      mPath.lineTo(sX,sY);
      mCnv.drawPath(mPath,mPntBorder);            
   }
   
   /** 
    * Draw X and Y axis
    */
   protected void drawAxis() {
      mPath.reset();
      mPath.moveTo(sX-bX*aX,sY);
      mPath.lineTo(sX-bX*aX,eY);
      mPath.moveTo(sX,eY+bY*aY);
      mPath.lineTo(eX,eY+bY*aY);
      mCnv.drawPath(mPath,mPntAxis);            
   }
   
   /** 
    * Converts value expressed in dp (device independent pixel) in value
    * expressed in actual display pixel (depends on display metrics).
    */
   protected float dipToPixel(float dips) {
      return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dips,getResources().getDisplayMetrics());
   }

   
}
