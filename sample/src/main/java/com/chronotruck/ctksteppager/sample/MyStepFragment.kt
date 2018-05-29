package com.chronotruck.ctksteppager.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_step.*

/**
 * @Author McGalanes
 * @Email melwin.magalhaes@gmail.com
 * @Project android-ctk-step-pager
 */
class MyStepFragment : Fragment() {

    companion object {
        private const val ARG_STEP_NUMBER = "arg:step_number"

        fun newInstance(stepNumber: Int) = MyStepFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_STEP_NUMBER, stepNumber)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview.text = arguments?.getInt(ARG_STEP_NUMBER).toString()
    }
}