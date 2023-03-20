package com.example.animationshw.ui.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.animationshw.R
import com.example.animationshw.navigateTo
import com.example.animationshw.ui.second.SecondFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var width = 0
    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val display: Display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            val nsv = findViewById<NestedScrollView>(R.id.nsv)
            val btnNext = findViewById<Button>(R.id.bntNext)
            btnNext.setOnClickListener {
                ValueAnimator.ofInt(0, (width - it.measuredWidth)).apply {
                    duration = 1000
                    interpolator = AccelerateInterpolator(2f)
                    addUpdateListener {
                        val lp = btnNext.layoutParams as LinearLayout.LayoutParams
                        lp.marginStart = this.animatedValue as Int
                        btnNext.layoutParams = lp
                    }
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {}

                        override fun onAnimationEnd(animation: Animator) {
                            parentFragmentManager.navigateTo(SecondFragment.newInstance()::class.java)
                            val lp = btnNext.layoutParams as LinearLayout.LayoutParams
                            lp.marginStart = 0
                            btnNext.layoutParams = lp
                        }

                        override fun onAnimationCancel(animation: Animator) {}

                        override fun onAnimationRepeat(animation: Animator) {}

                    })
                    start()
                }
            }

            val btnBounce = findViewById<Button>(R.id.btnBounce)
            btnBounce.setOnClickListener {
                val bouncer =
                    ValueAnimator.ofInt(0, (nsv.measuredHeight - (2 * it.measuredHeight))).apply {
                        duration = 1000
                        interpolator = BounceInterpolator()
                        addUpdateListener {
                            val lp = btnBounce.layoutParams as LinearLayout.LayoutParams
                            lp.topMargin = this.animatedValue as Int
                            btnBounce.layoutParams = lp
                        }
                    }
                val alpha = ObjectAnimator.ofFloat(it, "alpha", 1f, 0f).apply {
                    duration = 1000
                }

                AnimatorSet().apply {
                    play(bouncer).before(alpha)
                    addListener(object : Animator.AnimatorListener{
                        override fun onAnimationStart(animation: Animator) {}

                        override fun onAnimationEnd(animation: Animator) {
                            val lp = btnBounce.layoutParams as LinearLayout.LayoutParams
                            lp.topMargin = 0
                            btnBounce.layoutParams = lp
                            btnBounce.alpha = 1f
                        }

                        override fun onAnimationCancel(animation: Animator) {}

                        override fun onAnimationRepeat(animation: Animator) {}

                    })
                    start()
                }
            }
        }
    }

}