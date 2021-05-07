package com.monk.modulefragment.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.monk.commonutils.LogUtil

/**
 * @author monk
 * @date 2019-01-09
 */
class DFDemo : DialogFragment() {
    private val TAG ="TestDialogFragment"
    private var alertDialog: AlertDialog? = null

    companion object {
        fun newInstance(): DFDemo {
            return DFDemo()
        }
    }

    override fun dismiss() {
        super.dismiss()
        LogUtil.e(TAG, "dismiss")
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
        LogUtil.e(TAG, "dismissAllowingStateLoss")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtil.i(TAG, "dismissAllowingStateLoss")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.e(TAG, "onDetach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        LogUtil.v(TAG, "onCancel")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.v(TAG, "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.v(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.v(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e(TAG, "onDestroy")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val textView = TextView(activity)
        textView.text = "Hello"
        LogUtil.i(TAG, "onCreateView")
        return textView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        alertDialog = AlertDialog.Builder(activity)
                .setTitle("Test")
                .setMessage("Message")
                .create()
        LogUtil.i(TAG, "onCreateDialog")
        return alertDialog!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.e(TAG, "onDestroyView")
    }


}