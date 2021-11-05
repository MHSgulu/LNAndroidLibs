package com.lnkj.libs.utils.ext.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideSoftInput(){
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if(view == null){
       view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideSoftInput(){
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity?.currentFocus
    if(view == null){
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}