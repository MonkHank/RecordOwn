package com.monk.modulefragment.rxjava2.imetate2

/**
 * 描述此次订阅，用于取消或判断此次订阅是否已经订阅
 * @author monk
 * @date 2019-03-01
 */
interface Disposable {
    fun isDisposed(): Boolean
    fun dispose()
}
