package com.monk.eventdispatch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.MotionEvent;

import com.monk.aidldemo.R;
import com.monk.base.BaseCompatActivity;
import com.monk.commonutils.LogUtil;

import butterknife.BindView;

/**
 * @author monk
 * @date 2019-01-11
 */
public class EventDispatchActivity extends BaseCompatActivity {
    @BindView(R.id.myLayout) MyLayout myLayout;
    @BindView(R.id.myView) MyView myView;
    @BindView(R.id.btMessage) AppCompatButton btMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_event_dispatch);

        simpleName=simpleName+"：";
        btMessage.setOnClickListener(v -> {
            new Thread(() -> {
                Looper.prepare();
                Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        LogUtil.e(tag,simpleName+": msg = "+msg.obj);
                    }
                };
                Message msg=new Message();
                msg.obj = "no-ui thread message";
                handler.sendMessage(msg);
                Looper.loop();
                // TODO: 2019-06-03 loop之后的代码不会执行，如果执行了，说明程序奔溃了
            }).start();
        });


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i(tag,simpleName+"：dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i(tag,simpleName+"：onTouchEvent");
        return super.onTouchEvent(event);
    }
}
