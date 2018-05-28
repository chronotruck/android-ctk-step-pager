package com.chronotruck.ctksteppager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expandedWidth = Util.getDeviceScreenSize(this).x - resources.getDimension(R.dimen.steptab_width_collapsed).toInt()

        init()

        switch_button.setOnClickListener {
            tab1.apply {
                if (isExpanded) {
                    collapse()
                    tab2.expand(expandedWidth)
                } else {
                    expand(expandedWidth)
                    tab2.collapse()
                }
            }
        }
    }

    private fun init() {
        tab1.apply {
            isExpanded = true
            title = "One"
            stepNumber = 1
        }

        tab2.apply {
            isExpanded = false
            title = "Two"
            stepNumber = 2
        }
    }
}
