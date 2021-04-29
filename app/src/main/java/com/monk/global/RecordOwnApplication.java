package com.monk.global;

import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.monk.basic.BaseApplication;

/**
 * @author monk
 * @date 2019-01-03
 */
public class RecordOwnApplication extends BaseApplication {
    private final String tag = "RecordOwnApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        UIRouter.getInstance().registerUI("app");

    }
}
