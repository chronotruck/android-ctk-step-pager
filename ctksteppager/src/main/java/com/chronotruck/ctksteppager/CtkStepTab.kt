package com.chronotruck.ctksteppager

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

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

    val titleTv: TextView

    val isExpanded get() = isActivated

    init {
        inflate(context, R.layout.layout_ctk_step_tab, this)
        titleTv = findViewById(R.id.title_textview)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams.height = resources.getDimension(R.dimen.steptab_height).toInt()
        collapse()
    }

    fun toggle() {
        if (isExpanded) {
            collapse()
        } else {
            expand()
        }
    }

    fun expand() {
        isActivated = true

        ValueAnimator.ofInt(measuredWidth, Util.getDeviceScreenSize(context).x).apply {
            duration = 1000
            addUpdateListener {
                layoutParams.width = it.animatedValue as Int
                requestLayout()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    titleTv.visibility = VISIBLE

                    (layoutParams as LinearLayout.LayoutParams).apply {
                        width = LinearLayout.LayoutParams.MATCH_PARENT
                        weight = 1f
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                }
            })
        }.start()
    }

    fun collapse() {
        isActivated = false
        (layoutParams as LinearLayout.LayoutParams).apply {
            width = LinearLayout.LayoutParams.WRAP_CONTENT
            weight = 0f
        }
        setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        requestLayout()
        titleTv.visibility = GONE
    }
}