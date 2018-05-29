package com.chronotruck.ctksteppager

import android.content.Context
import android.graphics.Point
import android.view.WindowManager


/**
 * @Author McGalanes
 * @Email melwin.magalhaes@gmail.com
 * @Project android-ctk-step-pager
 */
class Util {
    companion object {
        fun getDeviceScreenSize(context: Context): Point {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size
        }
    }
}