# ![descharts name logo](https://github.com/bradipao/desCharts/raw/master/media/ch_namelogo.png)

**What is _desCharts_?**
yet another android charting library

**Why the name _desCharts_?**
a tribute to [René Descartes](http://en.wikipedia.org/wiki/Ren%C3%A9_Descartes), father of analytical geometry

**Where is the manual?**
go to the [desCharts wiki pages](https://github.com/bradipao/desCharts/wiki)

## Show me the pics!

| XY chart                | Styled XY chart             |
|:-----------------------:|:---------------------------:|
| ![XY chart][ch1]        | ![Styled XY chart][ch2]     |

| Line chart              | Stacked Line chart          |
|:-----------------------:|:---------------------------:|
| ![Line chart][ch3]      | ![Stacked Line chart][ch4]  |

| Bar chart               | Stacked Bar chart           |
|:-----------------------:|:---------------------------:|
| ![Bar chart][ch5]       | ![Stacked Bar chart][ch6]   |


## Show me the code!!
```xml
<it.bradipao.lib.descharts.StyledXyChartView
    android:id="@+id/chart"
    android:layout_width="match_parent"
    android:layout_height="200dp" />
```

```java
// create FIRST serie
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

// create SECOND serie
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
```

## Credits and Acknowledgements
**Author** : Sid Bradipao (https://plus.google.com/+SidBradipao)

**Thanks to** : [Daniel Nadeau](https://plus.google.com/+DanielNadeau), I learned how to create a custom chart view studying his [HoloGraphLibrary](https://bitbucket.org/danielnadeau/holographlibrary), a wonderful android charting library, then I decided to write a library from scratch... just for fun. :)

## License

    Copyright 2014 Bradipao (bradipao@gmail.com)
    https://plus.google.com/+SidBradipao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[ch1]: https://github.com/bradipao/desCharts/raw/master/media/xychartview_sm.png
[ch2]: https://github.com/bradipao/desCharts/raw/master/media/styledxychartview_sm.png
[ch3]: https://github.com/bradipao/desCharts/raw/master/media/linechartview_sm.png
[ch4]: https://github.com/bradipao/desCharts/raw/master/media/stackedlinechartview_sm.png
[ch5]: https://github.com/bradipao/desCharts/raw/master/media/barchartview_sm.png
[ch6]: https://github.com/bradipao/desCharts/raw/master/media/stackedbarchartview_sm.png
