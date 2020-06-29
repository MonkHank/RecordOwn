package com.monk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.monk.aidldemo.R;
import com.monk.base.BaseCompatActivity;
import com.monk.commonutils.ToastUtils;
import com.monk.utils.datepicker.CustomDatePicker;
import com.monk.utils.datepicker.DateUtils;
import com.peoplesafe.views.AddEquipmentItem;

import butterknife.BindView;
import butterknife.OnClick;

public class DateActivity extends BaseCompatActivity {

    @BindView(R.id.flStartDate) AddEquipmentItem flStartDate;
    @BindView(R.id.flEndDate) AddEquipmentItem flEndDate;
    @BindView(R.id.flDays) AddEquipmentItem flDays;
    @BindView(R.id.tvChole) TextView tvChole;
    @BindView(R.id.tvChole2) TextView tvChole2;
    @BindView(R.id.tvChole3) TextView tvChole3;

    private TextView tvStartDate, tvEndDate;
    private EditText etDays;

    private CustomDatePicker customDatePicker, customDatePicker1;
    private DateUtils dateUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_date);

        tvStartDate = flStartDate.getTvContent();
        tvEndDate = flEndDate.getTvContent();
        etDays = flDays.getEtContent();

        dateUtils = new DateUtils(mContext);
        initStartDate();
        initEndDate();

        int days = dateUtils.daysOfTwo("2019-08-19", dateUtils.getCurrentTime().split(" ")[0]) + 1;
        tvChole.setText("和芸芸认识的第 "+ days +" 天");

        int days2 = dateUtils.daysOfTwo("2019-09-10", dateUtils.getCurrentTime().split(" ")[0]) + 1;
        tvChole2.setText("从19.09.10到现在第 "+ days2 +" 天");

        int days3 = dateUtils.daysOfTwo("2019-10-08", dateUtils.getCurrentTime().split(" ")[0]) + 1;
        tvChole3.setText("从19.10.08到现在第 "+ days3 +" 天");
    }

    private void initStartDate() {
        customDatePicker = dateUtils.initDatePicker(tvStartDate, time -> {
            String startDate = time.split(" ")[0];
            String endTime = tvEndDate.getText().toString();
            long daysOfTwo = dateUtils.daysOfTwo(startDate, endTime);

            tvStartDate.setText(startDate);

            etDays.setText(daysOfTwo+" 天 （不包含开始日期） ");

        });
    }

    private void initEndDate() {
        customDatePicker1 = dateUtils.initDatePicker(tvEndDate, time -> {
            String startDate = tvStartDate.getText().toString();
            String endTime = time.split(" ")[0];

            long daysOfTwo = dateUtils.daysOfTwo(startDate, endTime);
            if (daysOfTwo < 0) {
                tvEndDate.setText(startDate);
                ToastUtils.showImageToast(mContext, "结束日期不能小于开始日期");
            } else {
                tvEndDate.setText(endTime);
            }

            etDays.setText(daysOfTwo+" 天 （不包含开始日期）");
        });
    }

    @OnClick({ R.id.flStartDate, R.id.flEndDate})
    public void clickEvent(View view) {
        String startDate = tvStartDate.getText().toString();
        String endDate = tvEndDate.getText().toString();
        switch (view.getId()) {
            case R.id.flStartDate:
                customDatePicker.show(startDate);
                break;
            case R.id.flEndDate:
                customDatePicker1.show(endDate);
                break;
        }
    }
}
