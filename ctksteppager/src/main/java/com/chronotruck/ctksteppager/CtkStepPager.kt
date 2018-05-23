package com.chronotruck.ctksteppager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

/**
 * @Author McGalanes
 * @Email melwin.magalhaes@gmail.com
 * @Project android-ctk-step-pager
 */
class CtkStepPager @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.layout_ctk_step_pager, this)
    }
}