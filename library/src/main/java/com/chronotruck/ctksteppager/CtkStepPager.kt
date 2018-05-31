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

    var activeTabColorBackground: Int? = null

    var inactiveTabColorBackground: Int? = null

    var doneTabColorBackground: Int? = null

    var activeTabTextColor: Int? = null

    var inactiveTabTextColor: Int? = null

    var doneTabIconColor: Int? = null

    var adapter: PagerAdapter? = null
        set(value) {
            field = value
            initStepTabLayout()
        }

    private fun initStepTabLayout() {
        viewPager.adapter = adapter
        stepTabLayout.apply {
            this@CtkStepPager.activeTabColorBackground?.let { this.activeTabColorBackground = it }
            this@CtkStepPager.inactiveTabColorBackground?.let { this.inactiveTabColorBackground = it }
            this@CtkStepPager.doneTabColorBackground?.let { this.doneTabColorBackground = it }
            this@CtkStepPager.activeTabTextColor?.let { this.activeTabTextColor = it }
            this@CtkStepPager.inactiveTabTextColor?.let { this.inactiveTabTextColor = it }
            this@CtkStepPager.doneTabIconColor?.let { this.doneTabIconColor = it }
            setupWithViewPager(viewPager)
        }
    }

    fun doneCurrentStepTab() {
        stepTabLayout.doneCurrentStepTab()
    }

    fun doneStepTabUntil(endPositionInclusive: Int) {
        stepTabLayout.doneStepTabUntil(endPositionInclusive)
    }

    fun resetAllStepTabs() {
        stepTabLayout.resetAllStepTabs()
    }

    init {
        inflate(context, R.layout.view_ctk_step_pager, this)
        orientation = VERTICAL

        viewPager = findViewById(R.id.viewpager)
        stepTabLayout = findViewById(R.id.tablayout)
    }
}