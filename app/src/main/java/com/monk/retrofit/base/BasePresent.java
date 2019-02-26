package com.monk.retrofit.base;

/**
 * @author monk
 * @date 2019-02-19
 */
public class BasePresent <T extends IBaseView>{
    protected T mMvpView;

    public BasePresent(T mMvpView) {
        this.mMvpView = mMvpView;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void detachMvpView(){
        mMvpView=null;
    }
}
