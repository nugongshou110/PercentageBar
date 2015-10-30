# PercentageBar
dynamic custom bar graph
自定义动态柱状图
![这里写图片描述](http://img.blog.csdn.net/20151030213801386)
#PS：gif录出来有些卡顿，真机测试很流畅
## 如何使用
###1.设置最大值
```java
//相当于图中的40亿
mBarGraph.setMax(40);
```
###2.设置单位
```java
mBarGraph.setUnit("亿元");
```
###3.设置柱状图宽度
```java
mBarGraph.setBarWidth(50);
```
###4.设置一共有几个柱状图
```java
mBarGraph.setTotalBarNum(7);
```
###5.设置一共有几条竖线
```java
mBarGraph.setVerticalLineNum(4);
```
###6.设置每个柱状图的目标值
```java
 private ArrayList<Float> respectTarget;
 ...
 respectTarget = new ArrayList<Float>();
 respectTarget.add(35.0f);
        respectTarget.add(20.0f);
        respectTarget.add(18.0f);
        respectTarget.add(15.0f);
        respectTarget.add(10.0f);
        respectTarget.add(8.0f);
        respectTarget.add(5.0f);
   mBarGraph.setRespectName(respName);
```
###7.设置每个柱状图的名字
```java
private ArrayList<String> respName;
 respName.add("滴滴");
        respName.add("小米");
        respName.add("京东");
        respName.add("美团");
        respName.add("魅族");
        respName.add("酷派");
        respName.add("携程");
  mBarGraph.setRespectName(respName);
```

