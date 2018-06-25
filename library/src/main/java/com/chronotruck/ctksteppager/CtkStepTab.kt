package com.chronotruck.ctksteppager

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
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

    val COLLAPSED_TAB_WIDTH = resources.getDimension(R.dimen.steptab_width_collapsed).toInt()

    private val titleTv: TextView
    private val badgeTv: TextView
    private val doneIv: ImageView

    var settings = Settings(this)
        set(value) {
            field = value
            update()
        }

    var isExpanded: Boolean = isActivated
        get() = isActivated
        private set

    var isDone: Boolean = false
        private set

    var title: String
        set(value) {
            titleTv.text = value
        }
        get() = titleTv.text.toString()

    var stepNumber: Int
        set(value) {
            badgeTv.text = value.toString()
        }
        get() = badgeTv.text.toString().toInt()


    init {
        inflate(context, R.layout.view_ctk_step_tab, this)

        titleTv = findViewById(R.id.title_textview)
        badgeTv = findViewById(R.id.badge_textview)
        doneIv = findViewById(R.id.done_imageview)

        update()
    }

    fun update() {
        val textColorStateList = ColorStateList(
                arrayOf(
                        intArrayOf(android.R.attr.state_activated),
                        intArrayOf(-android.R.attr.state_activated)
                ),
                intArrayOf(
                        settings.activeTextColor,
                        settings.inactiveTextColor
                )
        )
        titleTv.setTextColor(textColorStateList)
        badgeTv.setTextColor(textColorStateList)
        DrawableCompat.setTint(doneIv.drawable, settings.doneIconColor)

        setBackgroundColor(if (isActivated) {
            settings.activeTabColorBackground
        } else {
            settings.inactiveTabColorBackground
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams.height = resources.getDimension(R.dimen.steptab_height).toInt()
        if (isActivated) return
        collapse()
    }

    fun expand(finalWidth: Int) {
        isActivated = true
        getWidthAnimator(
                from = measuredWidth,
                to = finalWidth,
                onAnimationStart = {
                    hideLabels(false) { showLabels(showBadge = !isDone) }
                    showExpandedBackground()
                },
                onAnimationEnd = {
                    (layoutParams as LinearLayout.LayoutParams).width = finalWidth
                }
        ).start()
    }

    fun collapse() {
        isActivated = false
        getWidthAnimator(
                from = measuredWidth,
                to = resources.getDimension(R.dimen.steptab_width_collapsed).toInt(),
                onAnimationStart = {
                    (layoutParams as LinearLayout.LayoutParams).width = COLLAPSED_TAB_WIDTH
                    if (!isDone) {
                        showCollapsedBackground()
                    }
                    hideLabels(true) { if (!isDone) showBadge() }
                }
        ).start()
    }

    fun done() {
        if (isDone) return
        isDone = true
        setBackgroundColor(settings.doneTabColorBackground)
        hideLabels(false) { showDoneLabel() }
    }

    fun undone() {
        isDone = false
        hideDoneLabel()
        if (isExpanded) {
            expand(measuredWidth)
        } else {
            collapse()
        }
    }

    private fun showExpandedBackground() {
        if (isDone) return
        setBackgroundColor(settings.activeTabColorBackground)
    }

    private fun showCollapsedBackground() {
        if (isDone) return
        setBackgroundColor(settings.inactiveTabColorBackground)
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

    private fun hideLabels(hideTitle: Boolean, onAnimationEnd: () -> Unit) {
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 0
            addUpdateListener {
                if (hideTitle) {
                    titleTv.alpha = it.animatedValue as Float
                }
                badgeTv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                    if (hideTitle) {
                        titleTv.visibility = View.GONE
                    }
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

    private fun showDoneLabel() {
        ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                doneIv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                }

                override fun onAnimationCancel(animator: Animator?) {
                }

                override fun onAnimationStart(animator: Animator?) {
                    doneIv.visibility = View.VISIBLE
                }
            })
        }.start()
    }

    private fun hideDoneLabel() {
        ValueAnimator.ofFloat(1f, 0f).apply {
            addUpdateListener {
                doneIv.alpha = it.animatedValue as Float
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animator: Animator?) {
                }

                override fun onAnimationEnd(animator: Animator?) {
                    doneIv.visibility = View.GONE
                }

                override fun onAnimationCancel(animator: Animator?) {
                }

                override fun onAnimationStart(animator: Animator?) {
                }
            })
        }.start()
    }

    private fun showLabels(showBadge: Boolean) {
        ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                titleTv.alpha = it.animatedValue as Float
                if (showBadge) {
                    badgeTv.alpha = it.animatedValue as Float
                }
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
                    if (showBadge) {
                        badgeTv.visibility = View.VISIBLE
                    }
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

    class Settings(private val view: CtkStepTab) {
        var activeTabColorBackground: Int = ContextCompat.getColor(view.context, R.color.default_steptab_background_open)
            set(value) {
                field = value
                view.update()
            }
        var inactiveTabColorBackground: Int = ContextCompat.getColor(view.context, R.color.default_steptab_background_close)
            set(value) {
                field = value
                view.update()
            }
        var doneTabColorBackground: Int = ContextCompat.getColor(view.context, R.color.default_steptab_background_done)
            set(value) {
                field = value
                view.update()
            }
        var activeTextColor: Int = ContextCompat.getColor(view.context, R.color.default_steptab_text_open)
            set(value) {
                field = value
                view.update()
            }
        var inactiveTextColor: Int = ContextCompat.getColor(view.context, R.color.default_steptab_background_close)
            set(value) {
                field = value
                view.update()
            }
        var doneIconColor: Int = ContextCompat.getColor(view.context, R.color.default_steptab_background_done)
            set(value) {
                field = value
                view.update()
            }
    }
}