package com.monk.androidtest

import android.os.Bundle
import com.monk.activity.base.BaseCompatActivity
import com.monk.androidtest.kotlinxc.GithubApi
import com.monk.commonutils.L
import kotlinx.coroutines.*

/**
 * runalone -> mergeManifest.xml， 另外俩不用配
 */
class ActEmpty :BaseCompatActivity<ActEmpty>() {

    val scope= MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.launch {
            val result = GithubApi.createGithubApi().searchRepos("Android", 0, 20)
            L.v("cccc","result = $result")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}