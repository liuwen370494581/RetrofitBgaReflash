package star.liuwen.com.enddesignview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.enddesignview.bean.PieData;

/**
 * Created by liuwen on 2017/2/28.
 */
public class sofaView extends View {
    private Paint mPaint;

    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    //饼装图初始绘制角度
    private float mStartAngle = 0;
    //数据
    private List<PieData> mData;
    //宽高
    private int mWidth, mHeight;


    public sofaView(Context context) {
        super(context);

    }

    public sofaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);//设置画笔的颜色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为填充
        mPaint.setStrokeWidth(10f);//设置画笔宽度为10px
        mPaint.setAntiAlias(true);
    }

    /**
     * 2.测量View大小(onMeasure)
     * <p>
     * Q: 为什么要测量View大小？
     * <p>
     * A: View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//取出宽度的确切数值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//取出宽度的测量模式

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//取出高度的确切数值
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);//取出高度的测量模式
    }


    /**
     * 3.确定View大小(onSizeChanged) 一般的话 只要确定w和h的值
     * 这个函数比较简单，我们只需关注 宽度(w), 高度(h) 即可，这两个参数就是View最终的大小。
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

    }

    /**
     * 4.确定子View布局位置(onLayout)
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    /**
     * 5.绘制内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画上颜色
        //canvas.drawColor(Color.BLUE);
        //绘制内容 新建一个画笔
        //1.绘制点
        //canvas.drawPoint(200, 200, mPaint);
        //canvas.drawPoints(new float[]{500, 500, 500, 600, 500, 700}, mPaint);
        //2.绘制直线
        // canvas.drawLine(0,200,500,1000,mPaint);
        //3 绘制两条线
        //canvas.drawLines(new float[]{0,300,300,600,100,200,300,400},mPaint);
        //4、绘制矩形
        //canvas.drawRect(200, 200, 400, 400, mPaint);
        //5.绘制带有圆弧的矩形
        // canvas.drawRoundRect(200, 200, 500, 500, 20f, 20f, mPaint);
        //5.1 绘制带有圆弧的矩形
        //RectF rectf=new RectF(200,200,500,500);
        //canvas.drawRoundRect(rectf,30f,30f,mPaint);
        //6 绘制椭圆
        // RectF rectfTy=new RectF(300,300,600,600);
        // canvas.drawOval(rectfTy,mPaint);
        //7.绘制圆
        //canvas.drawCircle(500,500,200,mPaint);
        //8 绘制圆弧
        RectF rectfyh = new RectF(200, 200, 600, 600);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectfyh, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectfyh, 0, 90, true, mPaint);


        if (mData == null) {
            return;
        }

        float currentStartAngle = mStartAngle;//初始化角度 0
        canvas.translate(mWidth / 2, mHeight / 2);//将画布坐标从原点移动到中心的位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);//饼图圆半径
        RectF rectfs = new RectF(-r, -r, r, r);
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectfs, currentStartAngle, pie.getAngle(), true, mPaint);
            currentStartAngle += pie.getAngle();
        }


    }

    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();
    }

    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();
    }

    public void initData(ArrayList<PieData> mData) {
        if (mData == null || mData.size() == 0) {
            return;
        }

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            sumValue += pie.getValue();
            int j = i % mColors.length;
            pie.setColor(mColors[j]);

        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            float percentage = pie.getValue() / sumValue;
            float angle = percentage * 360;
            pie.setPercentage(angle);
            sumAngle += angle;
        }
    }
}
