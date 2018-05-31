package com.chronotruck.ctksteppager

import android.content.Context
import android.support.v4.content.ContextCompat
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

    var activeTabColorBackground: Int = ContextCompat.getColor(context!!, R.color.default_steptab_background_open)

    var inactiveTabColorBackground: Int = ContextCompat.getColor(context!!, R.color.default_steptab_background_close)

    var doneTabColorBackground: Int = ContextCompat.getColor(context!!, R.color.default_steptab_background_done)

    var activeTabTextColor: Int = ContextCompat.getColor(context!!, R.color.default_steptab_text_open)

    var inactiveTabTextColor: Int = ContextCompat.getColor(context!!, R.color.default_steptab_text_close)

    var doneTabIconColor: Int = ContextCompat.getColor(context!!, R.color.default_steptab_background_done)

    var adapter: PagerAdapter? = null
        set(value) {
            field = value
            initStepTabLayout()
        }

    private fun initStepTabLayout() {
        viewPager.adapter = adapter
        stepTabLayout.apply {
            this.activeTabColorBackground = this@CtkStepPager.activeTabColorBackground
            this.inactiveTabColorBackground = this@CtkStepPager.inactiveTabColorBackground
            this.doneTabColorBackground = this@CtkStepPager.doneTabColorBackground
            this.activeTabTextColor = this@CtkStepPager.activeTabTextColor
            this.inactiveTabTextColor = this@CtkStepPager.inactiveTabTextColor
            this.doneTabIconColor = this@CtkStepPager.doneTabIconColor
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

        resolveAttrs(attrs)
    }

    private fun resolveAttrs(attrs: AttributeSet?) {
        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.CtkStepPager).apply {
                activeTabColorBackground = getColor(R.styleable.CtkStepPager_sp_activeTabBackgroundColor, activeTabColorBackground)
                inactiveTabColorBackground = getColor(R.styleable.CtkStepPager_sp_inactiveTabBackgroundColor, inactiveTabColorBackground)
                doneTabColorBackground = getColor(R.styleable.CtkStepPager_sp_doneTabBackgroundColor, doneTabColorBackground)
                activeTabTextColor = getColor(R.styleable.CtkStepPager_sp_activeTabTextColor, activeTabTextColor)
                inactiveTabTextColor = getColor(R.styleable.CtkStepPager_sp_inactiveTabTextColor, inactiveTabTextColor)
                doneTabIconColor = getColor(R.styleable.CtkStepPager_sp_doneTabIconColor, doneTabIconColor)
                recycle()
            }
        }
    }
}