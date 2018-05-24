package com.chronotruck.ctksteppager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switch_button.setOnClickListener {
            switchStepBarState(!ctkStepTab.isActivated)
        }
    }

    private fun switchStepBarState(activated: Boolean) {
        ctkStepTab.isActivated = activated
    }
}
