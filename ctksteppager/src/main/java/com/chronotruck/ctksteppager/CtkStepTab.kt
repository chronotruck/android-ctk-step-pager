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

    var isExpanded
        get() = isActivated
        set(value) {
            isActivated = value
            if (value) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                (layoutParams as LinearLayout.LayoutParams).apply {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                    weight = 1f
                }
            } else {
                (layoutParams as LinearLayout.LayoutParams).apply {
                    width = LinearLayout.LayoutParams.WRAP_CONTENT
                    weight = 0f
                }
                setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
            }
        }

    init {
        inflate(context, R.layout.layout_ctk_step_tab, this)
        titleTv = findViewById(R.id.title_textview)
        badgeTv = findViewById(R.id.badge_textview)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams.height = resources.getDimension(R.dimen.steptab_height).toInt()
    }

    fun expand(finalWidth: Int) {
        isActivated = true
        getWidthAnimator(
                from = measuredWidth,
                to = finalWidth,
                onAnimationStart = {
                    hideLabels { showLabels() }
                    setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                },
                onAnimationEnd = {
                    (layoutParams as LinearLayout.LayoutParams).apply {
                        width = LinearLayout.LayoutParams.MATCH_PARENT
                        weight = 1f
                    }
                }
        ).start()
    }


    fun collapse() {
        isActivated = false
        getWidthAnimator(
                from = measuredWidth,
                to = resources.getDimension(R.dimen.steptab_width_collapsed).toInt(),
                onAnimationStart = {
                    (layoutParams as LinearLayout.LayoutParams).apply {
                        width = LinearLayout.LayoutParams.WRAP_CONTENT
                        weight = 0f
                    }
                    hideLabels { showBadge() }
                    setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
                },
                onAnimationEnd = {
                }
        ).start()
    }

    private fun getWidthAnimator(from: Int, to: Int, onAnimationStart: () -> Unit = {}, onAnimationEnd: () -> Unit = {}): ValueAnimator {
        return ValueAnimator.ofInt(from, to).apply {
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                layoutParams.width = it.animatedValue as Int
                requestLayout()
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                    onAnimationEnd.invoke()
                }

                override fun onAnimationCancel(animator: Animator?) {
                }

                override fun onAnimationStart(animator: Animator?) {
                    onAnimationStart.invoke()
                }
            })
        }
    }

    private fun hideLabels(onAnimationEnd: () -> Unit) {
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 0
            addUpdateListener {
                titleTv.alpha = it.animatedValue as Float
                badgeTv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                    titleTv.visibility = View.GONE
                    badgeTv.visibility = View.GONE
                    onAnimationEnd.invoke()
                }

                override fun onAnimationCancel(animator: Animator?) {
                }

                override fun onAnimationStart(animator: Animator?) {
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
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                }

                override fun onAnimationCancel(animator: Animator?) {
                }

                override fun onAnimationStart(animator: Animator?) {
                    titleTv.visibility = View.VISIBLE
                    badgeTv.visibility = View.VISIBLE
                }
            })
        }.start()
    }

    private fun showBadge() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                badgeTv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                }

                override fun onAnimationCancel(animator: Animator?) {
                }

                override fun onAnimationStart(animator: Animator?) {
                    badgeTv.visibility = View.VISIBLE
                }
            })
        }.start()
    }
}