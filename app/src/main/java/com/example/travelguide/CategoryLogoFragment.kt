package com.example.travelguide

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class CategoryLogoFragment(private val logoResId: Int) : Fragment(R.layout.fragment_category_logo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.logo).setImageResource(logoResId)
    }
}
