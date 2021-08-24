package com.monk.androidtest.applike

import com.luojilab.component.componentlib.applicationlike.IApplicationLike
import com.luojilab.component.componentlib.router.ui.UIRouter

class AndroidTestApplike :IApplicationLike{

    val uiRouter = UIRouter.getInstance()

    override fun onCreate() {
        uiRouter.registerUI("androidtest")
    }

    override fun onStop() {
        uiRouter.unregisterUI("androidtest")
    }
}