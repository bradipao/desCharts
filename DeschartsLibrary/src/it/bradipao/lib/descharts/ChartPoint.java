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
* ChartPoint() class represents a point in the X,Y space. 
*/

public class ChartPoint {
   public float x = 0;
   public float y = 0;
   
   /**
   * Constructor, X and Y are zeroed.
   */
   public ChartPoint() {   
   }
   
   /**
   * Constructor, X and Y are assigned.
   */
   public ChartPoint(float x,float y) {
      this.x = x;
      this.y = y;
   }
   
}
