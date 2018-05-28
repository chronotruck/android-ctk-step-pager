package com.chronotruck.ctksteppager

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
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

    private val titleTv: TextView
    private val badgeTv: TextView

    val isExpanded get() = isActivated

    init {
        inflate(context, R.layout.layout_ctk_step_tab, this)
        titleTv = findViewById(R.id.title_textview)
        badgeTv = findViewById(R.id.badge_textview)
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
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                layoutParams.width = it.animatedValue as Int
                requestLayout()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    (layoutParams as LinearLayout.LayoutParams).apply {
                        width = LinearLayout.LayoutParams.MATCH_PARENT
                        weight = 1f
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                    hideLabels { showLabels() }
                    setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                }
            })
        }.start()
    }

    private fun hideLabels(onAnimationEnd: () -> Unit) {
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 0
            addUpdateListener {
                titleTv.alpha = it.animatedValue as Float
                badgeTv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    titleTv.visibility = View.GONE
                    badgeTv.visibility = View.GONE
                    onAnimationEnd.invoke()
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }
            })
        }.start()
    }

    private fun showLabels() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                titleTv.alpha = it.animatedValue as Float
                badgeTv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                    titleTv.visibility = View.VISIBLE
                    badgeTv.visibility = View.VISIBLE
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