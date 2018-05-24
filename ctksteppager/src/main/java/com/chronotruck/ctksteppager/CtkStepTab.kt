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

    private val constraintSetActive = ConstraintSet()
    private val constraintSetInactive = ConstraintSet()
    private var constraintLayout: ConstraintLayout = inflate(context, R.layout.layout_ctk_step_tab_active, this) as ConstraintLayout

    init {
        initConstraintLayout()
        switchLayout(isActivated)
    }

    override fun setActivated(activated: Boolean) {
        super.setActivated(activated)
        switchLayout(activated)
    }

    private fun initConstraintLayout() {
        constraintSetActive.clone(constraintLayout)
        constraintSetInactive.clone(context, R.layout.layout_ctk_step_tab_inactive)
    }

    private fun switchLayout(activated: Boolean) {
        TransitionManager.beginDelayedTransition(constraintLayout)
        if (activated) {
            constraintSetActive.applyTo(constraintLayout)
        } else {
            constraintSetInactive.applyTo(constraintLayout)
        }
    }
}