package com.monk.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.monk.activity.base.BaseService;
import com.monk.commonutils.LogUtil;

import java.lang.ref.WeakReference;

/**
 * @author monk
 * @date 2018-12-21
 */
public class StartService extends BaseService {

    private DemoBinder demoBinder;


    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(simpleName, "onBind() -- " + intent);
        demoBinder = new DemoBinder(mService);
        return demoBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(tag,simpleName+"：onStartCommand(Intent)");
        return START_STICKY;
    }

    public static class DemoBinder extends Binder implements IBindInterface {
        private final WeakReference<BaseService> weakReference;

        public DemoBinder(BaseService mService) {
            weakReference = new WeakReference<>(mService);
        }

        @Override
        public void execute() {

            LogUtil.i("DemoBinder","执行了 binder 里面方法 execute()");
        }

        /**
         * 获取 Binder 依赖的 Service，方便调用者使用 Service 里的方法
         *
         * @return
         */
        public BaseService getService() {
            if (weakReference.get() != null) {
                return weakReference.get();
            }
            return null;
        }
    }

    public interface IBindInterface {
        void execute();
    }
}
