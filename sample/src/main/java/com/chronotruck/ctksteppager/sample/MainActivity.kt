package com.chronotruck.ctksteppager.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        steppager.adapter = MyPagerAdapter(supportFragmentManager)

        done_button.setOnClickListener(this)
        reset_button.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.done_button -> steppager.doneCurrentStepTab()
            R.id.reset_button -> steppager.resetAllStepTabs()
        }
    }
}
