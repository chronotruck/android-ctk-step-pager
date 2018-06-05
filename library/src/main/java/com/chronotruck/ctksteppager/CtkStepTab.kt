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

    private val titleTv: TextView
    private val badgeTv: TextView
    private val doneIv: ImageView

    var settings = Settings(this)
        set(value) {
            field = value
            update()
        }

    var isExpanded: Boolean
        get() = isActivated
        set(value) {
            isActivated = value
            if (layoutParams == null) {
                layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        resources.getDimension(R.dimen.steptab_height).toInt(),
                        0f
                )
            }
            if (value) {
                (layoutParams as LinearLayout.LayoutParams).apply {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                    weight = 1f
                }
                showExpandedBackground()
                showLabels()
            } else {
                (layoutParams as LinearLayout.LayoutParams).width = LinearLayout.LayoutParams.WRAP_CONTENT
                showCollapsedBackground()
            }
        }

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
    }

    fun expand(finalWidth: Int) {
        isActivated = true
        getWidthAnimator(
                from = measuredWidth,
                to = finalWidth,
                onAnimationStart = {
                    if (!isDone) {
                        hideLabels { showLabels() }
                    }
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
                    (layoutParams as LinearLayout.LayoutParams).width = LinearLayout.LayoutParams.WRAP_CONTENT
                    if (!isDone) {
                        hideLabels { showBadge() }
                        showCollapsedBackground()
                    }
                }
        ).start()
    }

    fun done() {
        if (isDone) return
        isDone = true
        setBackgroundColor(settings.doneTabColorBackground)
        hideLabels { showDoneLabel() }
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