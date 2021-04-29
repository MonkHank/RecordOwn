package com.monk.modulefragment.applike

import com.luojilab.component.componentlib.applicationlike.IApplicationLike
import com.luojilab.component.componentlib.router.ui.UIRouter

class FragmentApplike :IApplicationLike{
    val uiRouter = UIRouter.getInstance()
    override fun onCreate() {
        uiRouter.registerUI("modulefragment")
    }

    override fun onStop() {
        uiRouter.unregisterUI("modulefragment")
    }
}