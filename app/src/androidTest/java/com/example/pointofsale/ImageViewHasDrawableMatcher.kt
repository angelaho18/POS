package com.example.pointofsale

import android.view.View
import android.widget.ImageView
//import android.support.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

object ImageViewHasDrawableMatcher {
    fun hasDrawable(): BoundedMatcher<View, ImageView> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun matchesSafely(imageView: ImageView): Boolean {
                return imageView.drawable != null
            }

            override fun describeTo(description: Description) {
                description.appendText("has drawable")
            }
        }
    }
}