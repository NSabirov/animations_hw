package com.example.animationshw

import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.navigateTo(newInstance: Class<out Fragment>) {
    try {
        this.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .addToBackStack(newInstance.canonicalName)
            .replace(R.id.container, newInstance, bundleOf())
            .commit()
    }catch (e: Exception){
        e.printStackTrace()
    }
}

fun Fragment.registerOnBackPressedCallback(
    onBackPressed: OnBackPressedCallback
) {
    requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressed)
}