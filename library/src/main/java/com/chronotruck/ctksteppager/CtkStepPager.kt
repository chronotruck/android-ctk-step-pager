package com.chronotruck.ctksteppager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * @Author McGalanes
 * @Email melwin.magalhaes@gmail.com
 * @Project android-ctk-step-pager
 */
class CtkStepPager @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val viewPager: ViewPager
    val stepTabLayout: CtkStepTabLayout

    var adapter: PagerAdapter? = null
        get() = viewPager.adapter
        set(value) {
            field = value
            viewPager.adapter = value
            stepTabLayout.setupWithViewPager(viewPager)
        }

    fun doneCurrentStepTab() {
        stepTabLayout.doneCurrentStepTab()
    }

    fun doneStepTabUntil(endPositionInclusive: Int) {
        stepTabLayout.doneStepTabUntil(endPositionInclusive)
    }

    fun resetAllStepTabs(){
        stepTabLayout.resetAllStepTabs()
    }

    init {
        inflate(context, R.layout.view_ctk_step_pager, this)
        orientation = VERTICAL

        viewPager = findViewById(R.id.viewpager)
        stepTabLayout = findViewById(R.id.tablayout)
    }
}