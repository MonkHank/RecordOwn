package com.monk.location;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monk.aidldemo.R;
import com.monk.base.BaseFragment;
import com.monk.commonutils.LogUtil;
import com.monk.commonutils.ToastUtils;

/**
 * @author monk
 * @date 2019-1-25 16:08:31
 */
public class LocationFragment extends BaseFragment {
    private final String tag = "LocationFragment";

    private TextView tvLocation;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance() {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jni, container, false);
        tvLocation = view.findViewById(R.id.tvJni);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (ContextCompat.checkSelfPermission(mActivity, "android.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mActivity, "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0x01);
        } else {
            ToastUtils.showImageToast(mActivity, "已授权");
            gpsLocation();
            getLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtil.i(tag, "requestCode=" + requestCode);
        if (requestCode == 0x01) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gpsLocation();
                getLocation();
            } else {
                ToastUtils.showImageToast(mActivity, "权限已拒绝");
            }
        }
    }

    private void gpsLocation() {
        ToastUtils.showImageToast(mActivity, "权限已申请");
        //用户同意授权，执行读取文件的代码
        LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LogUtil.i(tag, location.toString());
                tvLocation.setText(location.getAltitude() + "");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // gps状态改变
                LogUtil.i(tag, "provider=" + provider + ",status=" + status + ",extras=" + extras);
            }

            @Override
            public void onProviderEnabled(String provider) {
                LogUtil.i(tag, provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                LogUtil.i(tag, provider);
            }
        };

        /**
         * 绑定监听
         * 参数1，设备：有 GPS_PROVIDER 和 NETWORK_PROVIDER 两种，前者是GPS,后者是 GPRS 以及 WIFI 定位
         * 参数2，位置信息更新周期.单位是毫秒
         * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
         * 参数4，监听
         * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
         */
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LogUtil.i(tag, "执行");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    /** 基站定位 */
    private void getLocation() {
        LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Location[gps 31.205570,121.314256 acc=30 et=+43d22h41m23s854ms alt=38.6268196105957 vel=3.0
        // bear=0.13394096 {Bundle[mParcelledData.dataSize=40]}]
        LogUtil.i(tag,location.toString());
    }
}
