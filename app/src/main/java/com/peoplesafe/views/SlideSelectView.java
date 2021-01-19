package com.peoplesafe.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;


/**
 * @author monk
 * @date 15/2/10
 */
public class SlideSelectView extends View {
	private final String tag="SlideSelectView";

    /** 大圆半径*/
	private  final float RADIU_BIG = 15;
    /*** 线的高度*/
	private final float HEIGHT_LINE = 3;
    /*** 线距离两头的边距*/
	private final float MARGEN_LINE = RADIU_BIG*8;
    /*** 竖直线的数量*/
	private final int countOfSmallLine;
    /*** 竖直线的横坐标*/
	private float [] linesX;
	private final Context mContext;
	private final Paint mPaint;
	private final TextPaint mTextPaint;
	private float mHeight;
	private float mWidth;
    /*** 大圆的横坐标*/
	private float bigCircleX;
	private final float textSize;
    /*** 当前大球距离最近的位置*/
	private int currentPosition;
    /*** 小圆之间的间距*/
	private float distanceX;
	private String[] text4Rates={ "特小","小", "中","大", "超大" };
    /*** 依附效果实现*/
	private ValueAnimator valueAnimator;
    /*** 用于纪录松手后的大圆x坐标*/
	private float currentPositionX;

	private onSelectListener selectListener;
	private final float density;

	public interface onSelectListener {
		void onSelect(int position);
	}

	public SlideSelectView(Context context) {
		this(context, null);
	}

	public SlideSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		density = context.getResources().getDisplayMetrics().density;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideSelectView);
		countOfSmallLine = a.getInt(R.styleable.SlideSelectView_circleCount, 5);
		textSize = a.getInt(R.styleable.SlideSelectView_textSize, (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
		a.recycle();

		mPaint = new Paint();
		mPaint.setColor(Color.GRAY);
		mPaint.setAntiAlias(true);

//		textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		mTextPaint.setColor(Color.GRAY);
		mTextPaint.setTextSize(textSize);

		currentPosition = countOfSmallLine / 2;
	}

    /**
     * 设置显示文本
     * @param strings
     */
	public void setString(String[] strings) {
		text4Rates = strings;
		if (countOfSmallLine != text4Rates.length) {
			throw new IllegalArgumentException("the count of small circle must be equal to the " +
					"text array length !");
		}
	}

	public void setOnSelectListener(onSelectListener listener) {
		selectListener = listener;
	}

	public void setPosition(int position) {
		currentPosition=position;
//		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
        //画水平线
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(HEIGHT_LINE);
		canvas.drawLine(MARGEN_LINE, mHeight / 2, mWidth - MARGEN_LINE, mHeight / 2,mPaint);

        //画竖线
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(HEIGHT_LINE);
		for(int i = 0; i< countOfSmallLine; i++){
			canvas.drawLine(linesX[i],mHeight/2, linesX[i],mHeight/2-12,mPaint);
		}

        //画大圆的默认位置
		canvas.drawCircle(bigCircleX, mHeight / 2, RADIU_BIG, mPaint);

        //画文字
		canvas.drawText(text4Rates[currentPosition], linesX[currentPosition] , (mHeight / 2) - RADIU_BIG-20,mTextPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
            //并不是所有机型(oppo A37就不会)按下就会执行ACTION_MOVE事件，ACTION_DOWN还是要的；
			case MotionEvent.ACTION_DOWN:
				if(event.getX()>=MARGEN_LINE&&event.getX()<=(mWidth-MARGEN_LINE)){
					bigCircleX=event.getX();
					int position=(int)((event.getX()-MARGEN_LINE)/(distanceX/2));
					currentPosition=(position+1)/2;
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE://按下并不一定会执行，而是按下后会延迟一点时间才执行；
				if (event.getX() >= MARGEN_LINE && event.getX() <= (mWidth - MARGEN_LINE)) {	//防止滑出边界
					bigCircleX = event.getX();
					int position = (int) ((event.getX() - MARGEN_LINE) / (distanceX / 2));
					currentPosition = (position + 1) / 2;//更新当前位置
					invalidate();
					LogUtil.e(tag,"e.getX():"+ event.getX());
				}
				break;
			case MotionEvent.ACTION_UP:
				if (event.getX() >= MARGEN_LINE && event.getX() <= (mWidth - MARGEN_LINE)) {//防止滑出边界
					//当前位置距离最近的小竖线的距离
					float currentDistance = event.getX() - MARGEN_LINE - currentPosition * distanceX;
					if ((currentPosition == 0 && currentDistance < 0) || (currentPosition == (text4Rates.length - 1) && currentDistance > 0)) {
						if (null != selectListener) {
							selectListener.onSelect(currentPosition);
						}
						LogUtil.v(tag,"e.getX():"+ event.getX());
						return true;
					}
					currentPositionX = bigCircleX;
					valueAnimator = ValueAnimator.ofFloat(currentDistance);
					valueAnimator.setInterpolator(new AccelerateInterpolator());
					valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float slideDistance = (float) animation.getAnimatedValue();
							bigCircleX = currentPositionX - slideDistance;
							invalidate();
						}
					});
					valueAnimator.setDuration(100);
					valueAnimator.start();
					if (null != selectListener) {
						selectListener.onSelect(currentPosition);
					}
					LogUtil.d(tag,"e.getX():"+ event.getX());
				}
				break;
				default:
				    break;
		}
		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mHeight = h+10*density;
		mWidth = w;
		//计算每个小竖线的x坐标
		linesX = new float[countOfSmallLine];
		distanceX = (mWidth - MARGEN_LINE * 2) / (countOfSmallLine - 1);
		for (int i = 0; i < countOfSmallLine; i++) {
			linesX[i] = i * distanceX + MARGEN_LINE;
		}
		bigCircleX = linesX[currentPosition];
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int[] screenSize = getScreenSize((Activity) mContext);
		int resultWidth;
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY) {
			resultWidth = widthSize;
		} else {
			resultWidth = screenSize[0];
			if (widthMode == MeasureSpec.AT_MOST) {
				resultWidth = Math.min(widthSize, screenSize[0]);
			}
		}
		int resultHeight;
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if (heightMode == MeasureSpec.EXACTLY) {
			resultHeight = heightSize;
		} else {
			resultHeight = (int) (RADIU_BIG * 6);
			if (heightMode == MeasureSpec.AT_MOST) {
				resultHeight = Math.min(heightSize, resultHeight);
			}
		}
		setMeasuredDimension(resultWidth, resultHeight);
	}

	private static int[] getScreenSize(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return new int[]{metrics.widthPixels, metrics.heightPixels};
	}
}