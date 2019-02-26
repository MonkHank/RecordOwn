package com.monk.retrofit.mvp.view;

import com.monk.retrofit.base.IBaseView;
import com.monk.retrofit.bean.Bean;

/**
 * @author monk
 * @date 2019-02-19
 */
public interface RetrofitView extends IBaseView {
    void showProgressBar();

    void dismissProgressBar();

    void showError(String error);

    void displayBean(Bean bean);
}
