package com.chronotruck.ctksteppager

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import java.util.*

@Suppress("PrivatePropertyName")
/**
 * @Author McGalanes
 * @Email melwin.magalhaes@gmail.com
 * @Project android-ctk-step-pager
 */
class CtkStepTabLayout @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private val triangleSeparatorWidth: Int = resources.getDimension(R.dimen.steptablayout_triangle_separator_width).toInt()

    private val stepTabWidthCollapsed: Int = resources.getDimension(R.dimen.steptab_width_collapsed).toInt()

    val EXPANDED_TAB_WIDTH: Int
        get() = (Util.getDeviceScreenSize(context!!).x) - (tabCount - 1) * (stepTabWidthCollapsed + triangleSeparatorWidth)

    var activeTabColorBackground = ContextCompat.getColor(context!!, R.color.default_steptab_background_open)

    var inactiveTabColorBackground = ContextCompat.getColor(context!!, R.color.default_steptab_background_close)

    var doneTabColorBackground = ContextCompat.getColor(context!!, R.color.default_steptab_background_done)

    var activeTabTextColor = ContextCompat.getColor(context!!, R.color.default_steptab_text_open)

    var inactiveTabTextColor = ContextCompat.getColor(context!!, R.color.default_steptab_background_close)

    var doneTabIconColor = ContextCompat.getColor(context!!, R.color.default_steptab_background_done)

    private var viewPager: ViewPager? = null
    private lateinit var selectedTab: CtkStepTab

    val tabs = LinkedList<CtkStepTab>()
    val separators = LinkedList<ImageView>()

    val tabCount: Int
        get() = viewPager!!.adapter!!.count

    init {
        orientation = LinearLayout.HORIZONTAL
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        tabs.forEachIndexed { i, tab ->
            if (i == 0) {
                tab.expand(EXPANDED_TAB_WIDTH)
            } else {
                tab.collapse()
            }
        }
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        init()
    }

    fun doneCurrentStepTab() {
        doneStepTabUntil(viewPager!!.currentItem)
    }

    fun doneStepTabUntil(endPositionInclusive: Int) {
        IntRange(0, endPositionInclusive).forEach {
            tabs[it].done()
        }
        invalidateSeparators()
    }

    fun resetAllStepTabs() {
        for (tab in tabs) {
            tab.undone()
        }
        invalidateSeparators()
    }

    private fun init() {
        viewPager!!.adapter?.let {
            var tab: CtkStepTab
            for (i in 0 until it.count) {
                tab = createTab(it.getPageTitle(i), i + 1)
                tabs.add(tab)
                addView(tab)

                if (i != it.count - 1) {
                    val separator = createSeparator()
                    separators.add(separator)
                    addView(separator)
                }
            }

            val currentItemPosition = this.viewPager!!.currentItem
            selectedTab = tabs[currentItemPosition]
            selectedTab.expand(EXPANDED_TAB_WIDTH)

            invalidateSeparator(
                    separators[currentItemPosition],
                    selectedTab.settings.activeTabColorBackground,
                    selectedTab.settings.inactiveTabColorBackground
            )
        }

        viewPager!!.addOnPageChangeListener(this)
    }

    private fun createTab(title: CharSequence?, stepNumber: Int): CtkStepTab {
        return CtkStepTab(context).apply {
            this.title = title.toString()
            this.stepNumber = stepNumber

            settings.let {
                it.activeTabColorBackground = this@CtkStepTabLayout.activeTabColorBackground
                it.inactiveTabColorBackground = this@CtkStepTabLayout.inactiveTabColorBackground
                it.doneTabColorBackground = this@CtkStepTabLayout.doneTabColorBackground
                it.activeTextColor = this@CtkStepTabLayout.activeTabTextColor
                it.inactiveTextColor = this@CtkStepTabLayout.inactiveTabTextColor
                it.doneIconColor = this@CtkStepTabLayout.doneTabIconColor
            }
        }
    }

    private fun createSeparator(): ImageView {
        return ImageView(context).apply {
            this.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0f
            )

            invalidateSeparator(
                    this,
                    inactiveTabColorBackground,
                    inactiveTabColorBackground
            )
        }
    }

    private fun invalidateSeparator(separator: ImageView, leftItemBackgroundColor: Int, rightItemBackgroundColor: Int) {
        val drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.ic_arrow)!!)
        drawable.mutate()
        DrawableCompat.setTint(drawable, leftItemBackgroundColor)

        separator.apply {
            this.setImageDrawable(drawable)
            this.setBackgroundColor(rightItemBackgroundColor)
        }
    }

    private fun invalidateSeparators() {
        for (i in 0 until tabs.size - 1) {
            invalidateSeparator(
                    separators[i],
                    (tabs[i].background as ColorDrawable).color,
                    (tabs[i + 1].background as ColorDrawable).color
            )
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (tabs[position] == selectedTab) {
            return
        }

        selectedTab.collapse()
        selectedTab = tabs[position]
        selectedTab.expand(EXPANDED_TAB_WIDTH)

        invalidateSeparators()
    }
}