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

/**
* StyledChartPoint() class represents a point in the X,Y space
* with point specific style (line color, fill color, marker color and size). 
*/

public class StyledChartPoint {
   public float x = 0;
   public float y = 0;
   public int lineColor = 0xff000000;
   public int fillColor = 0x00000000;
   public int markColor = 0x00000000;
   public float markSize = 0;
   
   /**
   * Constructor, X and Y are zeroed.
   */
   public StyledChartPoint() {   
   }
   
   /**
   * Constructor, X and Y are assigned.
   */
   public StyledChartPoint(float x,float y) {
      this.x = x;
      this.y = y;
   }
   
   /**
   * Constructor, line color, X and Y are assigned.
   */
   public StyledChartPoint(float x,float y,int lineColor) {
      this.x = x;
      this.y = y;
      this.lineColor = lineColor;
   }
   
   /**
   * Constructor, line color, fill color, X and Y are assigned.
   */
   public StyledChartPoint(float x,float y,int lineColor,int fillColor) {
      this.x = x;
      this.y = y;
      this.lineColor = lineColor;
      this.fillColor = fillColor;
   }

   /**
   * Constructor, line color, fill color, marker color, marker seize, X and Y are assigned.
   */
   public StyledChartPoint(float x,float y,int lineColor,int fillColor,int markColor,float markSize) {
      this.x = x;
      this.y = y;
      this.lineColor = lineColor;
      this.fillColor = fillColor;
      this.markColor = markColor;
      this.markSize = markSize;
   }
   
}