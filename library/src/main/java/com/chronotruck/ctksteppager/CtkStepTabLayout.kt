package com.chronotruck.ctksteppager

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
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

    private var viewPager: ViewPager? = null

    private lateinit var selectedTab: CtkStepTab

    private val triangleSeparatorWidth: Int = resources.getDimension(R.dimen.steptablayout_triangle_separator_width).toInt()

    private val stepTabWidthCollapsed: Int = resources.getDimension(R.dimen.steptab_width_collapsed).toInt()

    private val EXPANDED_TAB_WIDTH: Int
        get() = (Util.getDeviceScreenSize(context!!).x) - (tabCount - 1) * (stepTabWidthCollapsed + triangleSeparatorWidth)

    private val EQUITABLE_TAB_WIDTH: Int
        get() = ((Util.getDeviceScreenSize(context!!).x) - (tabCount - 1) * triangleSeparatorWidth) / tabCount

    val tabs = LinkedList<CtkStepTab>()

    val tabCount: Int
        get() = viewPager!!.adapter!!.count

    init {
        orientation = LinearLayout.HORIZONTAL
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
            if (endPositionInclusive == tabCount - 1) {
                tabs[it].expand(EQUITABLE_TAB_WIDTH)
            }
        }
    }

    fun resetAllStepTabs() {
        for (tab in tabs) {
            tab.undone()
        }
    }

    private fun init() {
        viewPager!!.adapter?.let {
            var tab: CtkStepTab
            for (i in 0 until it.count) {
                tab = CtkStepTab(context).apply {
                    title = it.getPageTitle(i).toString()
                    stepNumber = i + 1
                }
                tab.isExpanded = false
                tabs.add(tab)
                addView(tab)

                if (i != it.count - 1) {
                    val separator = ImageView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                                WRAP_CONTENT,
                                MATCH_PARENT,
                                0f
                        )
                        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow))
                    }
                    addView(separator)
                }
            }

            selectedTab = tabs[this.viewPager!!.currentItem]
            selectedTab.isExpanded = true
        }

        viewPager!!.addOnPageChangeListener(this)
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
    }
}