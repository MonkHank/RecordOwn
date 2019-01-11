package com.monk.aidldemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.monk.LogUtil;
import com.monk.aidldemo.R;
import com.monk.aidldemo.TestDialogFragment;

/**
 * @author monk
 * @date 2018-12-21
 */
public class ScrollingActivity extends AppCompatActivity {
    private final String tag = "ScrollingActivity";
    private Button button;

    public static void intoHere(AppCompatActivity activity) {
        Intent intent = new Intent(activity, ScrollingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        View view = LayoutInflater.from(this).inflate(R.layout.content_scrolling, null, true);
        button = view.findViewById(R.id.button);
        linearLayout.addView(view);
        LogUtil.v(tag,"相对parent的高度："+ button.getTop());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(TestDialogFragment.newInstance(), "DialogFragment");
        ft.commit();

        // 如果没有配置configChanges，那么这个在横竖屏切换时候会有内存泄漏,上面不会
//        new AlertDialog.Builder(this).setTitle("Title").setMessage("Message").create().show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtil.v(tag,"相对parent的高度："+button.getTop());

    }
}
