package com.peoplesafe.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monk.aidldemo.R;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author JackieHank
 * @date 2017-09-08 16:09.
 */
public class SegmentControlView extends LinearLayout {

    private TextView textView1;
    private TextView textView2;

    public SegmentControlView(Context context) {
        this(context,null);
        initView();
    }
    public SegmentControlView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }
    public SegmentControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView1.setText("错题");
        textView2.setText("收藏");
        try {
            XmlPullParser xrp = getResources().getXml(R.xml.selector_segmentcontrol);
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
        } catch (Exception e) {
        }
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView1.setPadding(dp2Px(getContext(),10), dp2Px(getContext(),5), dp2Px(getContext(),10), dp2Px(getContext(),5));
        textView2.setPadding(dp2Px(getContext(),10), dp2Px(getContext(),5), dp2Px(getContext(),10), dp2Px(getContext(),5));
        setSegmentTextSize(18);
        textView1.setBackgroundResource(R.drawable.segment_left);
        textView2.setBackgroundResource(R.drawable.segment_right);
        textView1.setSelected(true);
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.invalidate();

        textView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    return;
                }
                textView1.setSelected(true);
                textView2.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    return;
                }
                textView2.setSelected(true);
                textView1.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });
    }

    /**
     * 设置字体大小 单位dip
     * <p>2014年7月18日</p>
     * @param dp
     * @author RANDY.ZHANG
     */
    public void setSegmentTextSize(int dp) {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    private onSegmentViewClickListener listener;
    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {
        this.listener = listener;
    }
    /**
     * 设置文字
     * <p>2014年7月18日</p>
     * @param text
     * @param position
     * @author RANDY.ZHANG
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            textView1.setText(text);
        }
        if (position == 1) {
            textView2.setText(text);
        }
    }

    public interface onSegmentViewClickListener{
        /**
         *
         * <p>2014年7月18日</p>
         * @param v
         * @param position 0-左边 1-右边
         * @author RANDY.ZHANG
         */
        void onSegmentViewClick(View v, int position);
    }
}
