package com.example.animationshw.ui.second

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.animationshw.R
import com.example.animationshw.registerOnBackPressedCallback

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SecondViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            registerOnBackPressedCallback(object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                   onBackPressed()
                }
            })
            findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
                onBackPressed()
            }

            val btnBounce = findViewById<Button>(R.id.btnRotate)
            btnBounce.setOnClickListener {
                btnBounce.animate().rotationY(180f).setListener(object : Animator.AnimatorListener{
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        btnBounce.animate().rotationY(0f)
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }

            val btnAvd = findViewById<ImageView>(R.id.btnAvd)
            btnAvd.setOnClickListener {
                when (val drawable = btnAvd.drawable) {
                    is AnimatedVectorDrawableCompat -> {
                        drawable.start()
                    }
                    is AnimatedVectorDrawable -> {
                        drawable.start()
                    }
                }
            }
        }
    }

    private fun onBackPressed(){
        parentFragmentManager.popBackStack()
    }
}