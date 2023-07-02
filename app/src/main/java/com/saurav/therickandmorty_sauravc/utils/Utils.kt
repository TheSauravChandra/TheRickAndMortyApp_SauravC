package com.saurav.therickandmorty_sauravc.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes

class Utils {

    companion object {

        @JvmStatic
        fun Context.toast(msg: String = "") {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun Context.toast(@StringRes resId: Int) {
            Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun hideKeyboard(context: Context, view: View?) {
            if (view != null) {
                val imm: InputMethodManager =
                    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)!!
                if (imm.isAcceptingText) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }

        @JvmStatic
        fun showKeyboard(context: Context, editText: EditText?) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        @JvmStatic
        fun Context.checkInternet(): Boolean {
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

    }
}