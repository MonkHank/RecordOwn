package com.monk.androidtest.kotlinxc

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.monk.activity.base.BaseCompatActivity
import com.monk.commonutils.l
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * runalone -> mergeManifest.xml， 另外俩不用配
 */
class ActEmpty : BaseCompatActivity<ActEmpty>() {

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this)
        tv.text = "androidtest"
        tv.textSize=30f
        setContentView(tv)

        scope.launch {
            val result = GithubApi.createGithubApi().searchRepos("Android",0,20)
            l.v("cccc","result:$result")
        }

        scope.launch(Dispatchers.Unconfined) {
            val one = getResult(20)
            val two = getResult(40)
            l.v("cccc", "one+two=${one + two}")
        }

        scope.launch {
            // async 并发执行任务，还可以对它的start入参设置成懒加载，在调用的时候再为它分配资源
            val one = async(start = CoroutineStart.LAZY) { getResult(20) }
            val two = async { getResult(40) }

            l.v("cccc", "one+two = ${one.await() + two.await()}")
        }

        lifecycleScope.launch {
            createFlow().onCompletion {

            }.catch {

            }.collect {
                l.i("ActEmpty", "$it")
            }
        }

    }

    private suspend fun getResult(num: Int): Int {
        delay(5000) // 也是suspend
        return num.times(num)
    }

    /**
     * withContext本身也是suspend，耗时操作
     */
    private suspend fun getResult2(num: Int):Int{
        return withContext(Dispatchers.IO) {
            num.times(num)
        }
    }

    private fun createFlow():Flow<Int> = flow {
        for (i in 1..10)emit(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        lifecycleScope.cancel()
    }
}