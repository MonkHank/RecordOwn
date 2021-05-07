package com.monk.modulefragment.otherfra

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.monk.activity.base.BaseFragment
import com.monk.commonutils.LogUtil
import com.monk.commonutils.ToastUtils
import com.monk.modulefragment.R

/**
 * @author monk
 * @date 2019-1-25 16:08:31
 */
class FragmentLocation : BaseFragment<FragmentLocation?>() {
    private var tvLocation: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fra_modulefra_location, container, false)
        tvLocation = view.findViewById(R.id.tvMsg)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (ContextCompat.checkSelfPermission(mActivity, "android.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mActivity, "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 0x01)
        } else {
            ToastUtils.showImageToast(mActivity, "已授权")
            gpsLocation()
            location
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        LogUtil.i(tag, "requestCode=$requestCode")
        if (requestCode == 0x01) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gpsLocation()
                location
            } else {
                ToastUtils.showImageToast(mActivity, "权限已拒绝")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun gpsLocation() {
        ToastUtils.showImageToast(mActivity, "权限已申请")
        //用户同意授权，执行读取文件的代码
        val locationManager: LocationManager = mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                LogUtil.i(tag, location.toString())
                tvLocation?.text = location.altitude.toString()
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                // gps状态改变
                LogUtil.i(tag, "provider=$provider,status=$status,extras=$extras")
            }

            override fun onProviderEnabled(provider: String) {
                LogUtil.i(tag, provider)
            }

            override fun onProviderDisabled(provider: String) {
                LogUtil.i(tag, provider)
            }
        }
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
            return
        }
        LogUtil.i(tag, "执行")
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    //Location[gps 31.205570,121.314256 acc=30 et=+43d22h41m23s854ms alt=38.6268196105957 vel=3.0
    // bear=0.13394096 {Bundle[mParcelledData.dataSize=40]}]
    /** 基站定位  */
    private val location: Unit
        @SuppressLint("MissingPermission") private get() {
            val locationManager: LocationManager = mActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            val location: Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            //        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //Location[gps 31.205570,121.314256 acc=30 et=+43d22h41m23s854ms alt=38.6268196105957 vel=3.0
            // bear=0.13394096 {Bundle[mParcelledData.dataSize=40]}]
            LogUtil.i(tag, location.toString())
        }

    companion object {
        fun newInstance(): FragmentLocation {
            val fragment = FragmentLocation()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}