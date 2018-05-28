package com.chronotruck.ctksteppager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.LinearLayout
import java.util.*

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

    val tabs = LinkedList<CtkStepTab>()

    private lateinit var selectedTab: CtkStepTab

    val tabCount: Int?
        get() = viewPager?.adapter?.count

    @Suppress("PrivatePropertyName")
    private val EXPANDED_TAB_WIDTH: Int
        get() = (Util.getDeviceScreenSize(context!!).x) - (tabCount!! - 1) * (resources.getDimension(R.dimen.steptab_width_collapsed).toInt())

    init {
        orientation = LinearLayout.HORIZONTAL
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        init()
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