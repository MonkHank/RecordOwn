package com.monk.eventdispatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.monk.utils.LogUtil;
import com.monk.aidldemo.R;

/**
 * @author monk
 * @date 2019-01-11
 */
public class EventDispatchActivity extends AppCompatActivity {
    private final String tag = "EventDispatchActivity";
    private MyLayout myLayout;
    private MyView myView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);

        myLayout = findViewById(R.id.myLayout);
        myView = findViewById(R.id.myView);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i(tag,"dispatchTouchEvent");
//        return super.dispatchTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i(tag,"onTouchEvent");
        return super.onTouchEvent(event);
    }
}
