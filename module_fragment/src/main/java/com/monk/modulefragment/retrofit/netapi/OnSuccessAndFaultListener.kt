package com.monk.modulefragment.retrofit.netapi

/**
 * Created by 眼神 on 2018/3/27.
 */
interface OnSuccessAndFaultListener {
    fun onSuccess(result: String?)
    fun onFault(errorMsg: String?)
}