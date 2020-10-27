package com.peoplesafe.views;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monk.aidldemo.R;


/**
 * @author monk
 * @date 2018/11/6
 */
public class AddEquipmentItem extends FrameLayout {
    private final TextView tvContent;
    private final LinearLayout mLinearLayout;
    private final EditText etContent;

    public AddEquipmentItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.page_publish_task, this, true);
        mLinearLayout = view.findViewById(R.id.linearLayout);
        TextView tvName = view.findViewById(R.id.tvName);
        tvContent = view.findViewById(R.id.tvContent);
        etContent = view.findViewById(R.id.etContent);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AddEquipmentItem);
        if (ta.getText(R.styleable.AddEquipmentItem_tvName) != null) {
            tvName.setText(ta.getText(R.styleable.AddEquipmentItem_tvName));
        }
        if (ta.getText(R.styleable.AddEquipmentItem_tvContent) != null) {
            tvContent.setText(ta.getText(R.styleable.AddEquipmentItem_tvContent));
        }
        if (ta.getText(R.styleable.AddEquipmentItem_etContentHint) != null) {
            etContent.setHint(ta.getText(R.styleable.AddEquipmentItem_etContentHint));
        }
        int visibility = ta.getInteger(R.styleable.AddEquipmentItem_linearLayoutVisibility, 0);
        int tvVisibility = ta.getInteger(R.styleable.AddEquipmentItem_tvVisibility, 0);
        int etVisibility = ta.getInteger(R.styleable.AddEquipmentItem_etVisibility, 8);
        widgetVisibility(mLinearLayout, visibility);
        widgetVisibility(tvContent, tvVisibility);
        widgetVisibility(etContent, etVisibility);
        ta.recycle();
    }

    private void widgetVisibility(View view, int visibility) {
        switch (visibility) {
            case View.VISIBLE:
                view.setVisibility(View.VISIBLE);
                break;
            case View.GONE:
                view.setVisibility(View.GONE);
                break;
            case View.INVISIBLE:
                view.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public EditText getEtContent() {
        return etContent;
    }

    public void setLinearVisibility(int visibility) {
        mLinearLayout.setVisibility(visibility);
    }
}
