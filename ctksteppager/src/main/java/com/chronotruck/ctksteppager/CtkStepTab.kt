package com.chronotruck.ctksteppager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
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
        (layoutParams as LinearLayout.LayoutParams).apply {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            weight = 1f
        }
        setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
        requestLayout()
        titleTv.visibility = View.VISIBLE
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