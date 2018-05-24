package com.chronotruck.ctksteppager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.transition.TransitionManager
import android.util.AttributeSet

/**
 * @Author McGalanes
 * @Email melwin.magalhaes@gmail.com
 * @Project android-ctk-step-pager
 */
class CtkStepTab @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val constraintSetOpen = ConstraintSet()
    private val constraintSetClose = ConstraintSet()
    private lateinit var parentLayout: ConstraintLayout

    var isOpen: Boolean
        get() = isActivated
    set(value) {
        isActivated = value
        switchLayout(value)
    }

    init {
        inflate(context, R.layout.layout_ctk_step_tab_close, this)
        initConstraintLayout()
        switchLayout(isOpen)
    }

    private fun initConstraintLayout() {
        parentLayout = findViewById(R.id.cl)
        constraintSetClose.clone(parentLayout)
        constraintSetOpen.clone(context, R.layout.layout_ctk_step_tab_open)
    }

    private fun switchLayout(open: Boolean) {
        if (open) {
            constraintSetOpen
        } else {
            constraintSetClose
        }.applyTo(parentLayout)
        TransitionManager.beginDelayedTransition(parentLayout)
    }
}