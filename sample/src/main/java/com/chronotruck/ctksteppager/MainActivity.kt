package com.chronotruck.ctksteppager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tab1.collapse()

        switch_button.setOnClickListener {
            tab1.apply {
                if (isExpanded) {
                    collapse()
                } else {
                    expand(Util.getDeviceScreenSize(context).x)
                }
            }
        }
    }
}
