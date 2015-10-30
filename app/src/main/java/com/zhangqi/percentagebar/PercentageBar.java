package com.zhangqi.percentagebar;

/**
 * Created by zhangqi on 15/10/30.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class PercentageBar extends View{
    //画线的画笔
    private Paint mLinePaint;
    //画柱状图的画笔
    private Paint mBarPaint;
    //写字的画笔
    private Paint mTextPaint;

    //开始X坐标
    private int startX;
    //开始Y坐标
    private int startY;
    //结束X坐标
    private int stopX;

    //测量值 宽度
    private int measuredWidth;
    //测量值 高度
    private int measuredHeight;
    //每条柱状图的宽度
    private int barWidth;
    //设置最大值，用于计算比例
    private float max;
    //设置每条柱状图的目标值，除以max即为比例
    private ArrayList<Float> respTarget;
    //设置一共有几条柱状图
    private int totalBarNum;
    //设置每条柱状图的当前比例
    private Float[] currentBarProgress;
    //每条竖线的当前比例
    private int currentVerticalLineProgress;
    //最上面一条横线的比例
    private int currentHorizentalLineProgress;
    //每条柱状图的名字
    private ArrayList<String> respName;
    //每条竖线之间的间距
    private int deltaX;
    //每条柱状图之间的间距
    private int deltaY;
    //一共有几条竖线
    private int verticalLineNum;
    //单位
    private String unit;
    //每条竖线之间相差的值
    private float numPerUnit;

    public PercentageBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PercentageBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PercentageBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        //设开始X坐标为0
        startX =0;
        //设开始Y坐标为50
        startY =50;
        //初始化柱状图画笔
        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setColor(0xff40E0D0);
        mBarPaint.setStyle(Style.FILL);
        //初始化线的画笔
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Style.FILL);
        mLinePaint.setColor(0xffcdcdcd);
        mLinePaint.setStrokeWidth(2);


    }

    /**
     * 测量方法，主要考虑宽和高设置为wrap_content的时候，我们的view的宽高设置为多少
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽和高都为wrap_content的时候，我们将宽设置为我们输入的max值，也就是柱状图的最大值
        //高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) max, startY+10+totalBarNum*(barWidth+2*10));
            //如果宽度为wrap_content  高度为match_parent或者精确数值的时候
        }else if (widthSpecMode == MeasureSpec.AT_MOST) {
            //宽度设置为max，高度为父容器高度
            setMeasuredDimension((int) max, heightSpecSize);
            //如果宽度为match_parent或者精确数值的时候，高度为wrap_content
        }else if (heightSpecMode == MeasureSpec.AT_MOST) {
            //宽度设置为父容器的宽度，高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
            setMeasuredDimension(widthSpecSize, startY+10+totalBarNum*(barWidth+2*10));
        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获得测量后的宽度
        measuredWidth = getMeasuredWidth();
        //获得测量后的高度
        measuredHeight = getMeasuredHeight();
        //计算结束X的值
        stopX = measuredWidth-barWidth;
        //计算每条竖线之间的间距
        deltaX = (stopX-(startX+7*barWidth/5))/verticalLineNum;
        //计算每条柱状图之间的间距
        deltaY = (measuredHeight-startY-barWidth*totalBarNum)/totalBarNum;
        //计算出每条竖线所代表的数值
        numPerUnit = max/verticalLineNum;
        //初始化最上面横线的初始进度
        currentHorizentalLineProgress = stopX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画柱状图
         */
        for(int i = 0 ; i<totalBarNum ; i++){
            if (currentBarProgress[i]<(respTarget.get(i)/max)*stopX) {
                currentBarProgress[i]+=10;
                postInvalidateDelayed(10);
            }
            canvas.drawText(respName.get(i),startX,startY+deltaY+i*(deltaY+barWidth)+3*barWidth/4, mTextPaint);
            canvas.drawRect(startX+7*barWidth/5, startY+deltaY+i*(deltaY+barWidth), currentBarProgress[i], startY+deltaY+i*(deltaY+barWidth)+barWidth, mBarPaint);
        }
        /**
         * 画竖线
         */
        for(int i=0 ; i<verticalLineNum ; i++){
            if (currentVerticalLineProgress< measuredHeight) {
                currentVerticalLineProgress+=3;
                postInvalidateDelayed(10);
            }
            canvas.drawLine((startX+7*barWidth/5)+(i+1)*deltaX, startY, (startX+7*barWidth/5)+(i+1)*deltaX, currentVerticalLineProgress, mLinePaint);
            canvas.drawText(numPerUnit*(i+1)+unit, (startX+7*barWidth/5)+(i+1)*deltaX-barWidth, startY-barWidth/5, mTextPaint);
        }
        /**
         * 画最上面的横线
         */
        if (currentHorizentalLineProgress>startX+7*barWidth/5) {
            currentHorizentalLineProgress-=10;
            postInvalidateDelayed(10);
        }
        canvas.drawLine(stopX, startY, currentHorizentalLineProgress, startY, mLinePaint);
    }

    /**
     * 设置每个柱状图的宽度
     * @param width
     */
    public void setBarWidth(int width){
        this.barWidth = width;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(3*barWidth/5);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(0xffababab);



    }
    /**
     * 设置最大值
     * @param max
     */
    public void setMax(float max){
        this.max = max;
    }

    /**
     * 设置一共有几个柱状图
     * @param totalNum
     */
    public void setTotalBarNum(int totalNum){
        this.totalBarNum = totalNum;
        currentBarProgress = new Float[totalNum];
        for(int i = 0 ; i<totalNum ; i++){
            currentBarProgress[i] = 0.0f;
        }
    }

    /**
     * 分别设置每个柱状图的目标值
     * @param respTarget
     */
    public void setRespectTargetNum(ArrayList<Float> respTarget){
        this.respTarget = respTarget;

    }

    /**
     * 分别设置每个柱状图的名字
     * @param respName
     */
    public void setRespectName(ArrayList<String> respName){
        this.respName = respName;
    }

    /**
     * 设置单位
     * @param unit
     */
    public void setUnit(String unit){
        this.unit = unit;
    }

    /**
     * 设置有几条竖线
     * @param num
     */
    public void setVerticalLineNum(int num){
        this.verticalLineNum = num;
    }



}
