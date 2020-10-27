package com.monk.dragviewlibrary;

import android.content.Context;
import android.graphics.Rect;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.monk.commonutils.LogUtil;

/**
 * 正好趁这次机会来实现Module之间的通信，比如app module的MainActivity 访问这个module的某个activity或 fragment
 * @author monk
 * @date 2019-01-28
 */
public class DragViewGroup extends FrameLayout {
    private final String tag = "DragViewGroup";
    private int mSlop;
    private View mDragView;
    private State mCurrentState;
    private float mCurrentX,mCurrentY;

    public DragViewGroup(Context context) {
        this(context,null);
    }

    public DragViewGroup( Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSlop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        LogUtil.d(tag,"touchSlop:"+mSlop);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (isPointOnViews(event)) {
                    mCurrentState=State.DRAGGING;
                    mCurrentX=event.getX();
                    mCurrentY=event.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX= (int) (event.getX()-mCurrentX);
                int deltaY= (int) (event.getY()-mCurrentY);
                if (mCurrentState == State.DRAGGING && mDragView != null
                        && (Math.abs(deltaX) > mSlop || Math.abs(deltaY) > mSlop)) {
                    ViewCompat.offsetLeftAndRight(mDragView,deltaX);
                    ViewCompat.offsetTopAndBottom(mDragView,deltaY);
                    mCurrentX=event.getX();
                    mCurrentY=event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mCurrentState == State.DRAGGING) {
                    mCurrentState=State.IDLE;
                    mDragView=null;
                }
                break;
             default:
                break;
        }
        return true;
    }

    private boolean isPointOnViews(MotionEvent event) {
        boolean result=false;
        Rect rect= new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 如何说明childView的getX()是改变值还是定值 e.g：0
            rect.set((int)childView.getX(),(int)childView.getY(),(int)(childView.getX()+childView.getWidth()),
                    (int) (childView.getY()+childView.getHeight()));
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                mDragView=childView;
                result=true;
                break;
            }
        }
        return result && mCurrentState!=State.DRAGGING;
    }
}
