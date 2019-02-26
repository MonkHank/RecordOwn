package com.monk.retrofit.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.monk.aidldemo.R;
import com.monk.retrofit.bean.Bean;
import com.monk.retrofit.mvp.present.RetrofitPresent;
import com.monk.retrofit.mvp.present.RetrofitPresentImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author monk
 * @date 2019-2-19 14:01:46
 */
public class RetrofitActivity extends AppCompatActivity implements RetrofitView {
    private final String tag = "RetrofitActivity";
    @BindView(R.id.tool_bar_2)
    Toolbar toolbar;
    private RetrofitPresent retrofitPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        retrofitPresent = new RetrofitPresentImpl(this);
        // 协信
        retrofitPresent.getCopyList("8198b9a7-9f53-4932-a197-c76bac59ed4f");
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void displayBean(Bean bean) {

    }
}

