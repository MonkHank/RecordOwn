package com.peoplesafe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.monk.aidldemo.R;
import com.monk.commonutils.LogUtil;

/**
 * @author MonkeyHank
 * @date 2017-09-22 09:39
 */
public class QQListView extends ListView {
    private final String tag = "QQListView";
    /*** 用户滑动的最小距离*/
    private int touchSlop;
    /***  是否响应滑动*/
    private boolean isSliding;

    private PopupWindow mPopupWindow;
    private int mPopupWindowHeight;
    private int mPopupWindowWidth;

    private Button mDelBtn;
    private DelButtonClickListener mListener;

    /*** 当前手指触摸的View*/
    private View mCurrentView;

    /*** 当前手指触摸的位置*/
    private int mCurrentViewPos;

    public QQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = LayoutInflater.from(context);

        // todo 需要研究一下
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        View view = mInflater.inflate(R.layout.delete_btn, null);
        mDelBtn = view.findViewById(R.id.id_item_btn);

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 获取popupWindow宽高
        mPopupWindow.getContentView().measure(0, 0);
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        LogUtil.i(tag,"dispatchTouchEvent:"+action);
        int xDown=0,yDown=0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                 xDown = (int) ev.getX();
                 yDown = (int) ev.getY();
                if (mPopupWindow.isShowing()) {
                    dismissPopWindow();
                    return false;
                }
                // 获得当前手指按下时的item的位置
                mCurrentViewPos = pointToPosition(xDown, yDown);
                LogUtil.i(tag,"currentViewPos:"+mCurrentViewPos);
                int firstVisiblePosition = getFirstVisiblePosition();
                // 获得当前手指按下时的item
                mCurrentView = getChildAt(mCurrentViewPos - firstVisiblePosition);
                LogUtil.i(tag,"fistVisiblePosition:"+firstVisiblePosition);
                break;
            case MotionEvent.ACTION_MOVE:
                int xMove = (int) ev.getX();
                int yMove = (int) ev.getY();
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    LogUtil.e(tag, "touchslop = " + touchSlop + " , dx = " + dx + " , dy = " + dy);
                    isSliding = true;
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        LogUtil.i(tag,"onTouchEvent:"+action);
        if (isSliding) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    // 获得当前item的位置x与y
                    mCurrentView.getLocationOnScreen(location);

                    // 设置popupWindow的动画
                    mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
                    mPopupWindow.update();
                    mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP,
                            location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight() / 2
                                    - mPopupWindowHeight / 2);
                    // 设置删除按钮的回调
                    mDelBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.clickHappend(mCurrentViewPos);
                                mPopupWindow.dismiss();
                            }
                        }
                    });
                    // Log.e(tag, "mPopupWindow.getHeight()=" + mPopupWindowHeight);
                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;
                    break;
                default:
                    break;
            }
            // 相应滑动期间屏幕itemClick事件，避免发生冲突
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void dismissPopWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void setDelButtonClickListener(DelButtonClickListener listener) {
        mListener = listener;
    }

    public interface DelButtonClickListener {
        void clickHappend(int position);
    }
}
