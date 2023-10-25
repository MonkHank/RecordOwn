package com.monk.modulefragment.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.monk.modulefragment.service.IntentServices

/**
 * @author monk
 * @date 2018-12-21
 */
class IntentServices : IntentService("MyIntentService") {

    companion object {
        private const val ACTION_FOO = "com.monk.aidldemo.action.FOO"
        private const val ACTION_BAZ = "com.monk.aidldemo.action.BAZ"
        private const val EXTRA_PARAM1 = "com.monk.aidldemo.extra.PARAM1"
        private const val EXTRA_PARAM2 = "com.monk.aidldemo.extra.PARAM2"

        fun startActionFoo(context: Context, param1: String?, param2: String?) {
            val intent = Intent(context, IntentServices::class.java)
            intent.action = ACTION_FOO
            intent.putExtra(EXTRA_PARAM1, param1)
            intent.putExtra(EXTRA_PARAM2, param2)
            context.startService(intent)
        }

        fun startActionBaz(context: Context, param1: String?, param2: String?) {
            val intent = Intent(context, IntentServices::class.java)
            intent.action = ACTION_BAZ
            intent.putExtra(EXTRA_PARAM1, param1)
            intent.putExtra(EXTRA_PARAM2, param2)
            context.startService(intent)
        }

        fun startAction(context: Context,params1:String?) = Intent(context, IntentServices::class.java).also {
            it.putExtra(EXTRA_PARAM1, params1)
            context.startService(it)
        }

        fun startAction2(context: Context,params1:String?) = Intent(context, IntentServices::class.java).let {
            it.putExtra(EXTRA_PARAM1, params1)
            context.startService(it)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_FOO == action) {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                if (param1 != null) {
                    if (param2 != null) {
                        handleActionFoo(param1, param2)
                    }
                }
            } else if (ACTION_BAZ == action) {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                if (param1 != null) {
                    if (param2 != null) {
                        handleActionBaz(param1, param2)
                    }
                }
            }
        }
    }

    private fun handleActionFoo(param1: String, param2: String) {
        // TODO: Handle action Foo
        throw UnsupportedOperationException("Not yet implemented")
    }

    private fun handleActionBaz(param1: String, param2: String) {
        // TODO: Handle action Baz
        throw UnsupportedOperationException("Not yet implemented")
    }


}