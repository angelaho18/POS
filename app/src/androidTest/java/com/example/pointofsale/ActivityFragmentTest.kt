package com.example.pointofsale

import android.app.Instrumentation.ActivityResult
import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.pointofsale.fragments.fragment_list
import com.example.pointofsale.fragments.fragment_profile
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ActivityFragmentTest {
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(ActivityFragment::class.java)

    @Test
    fun checkRecyclerView() {
        activityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment_list()).addToBackStack(null).commit()
        onView(withId(R.id.productRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfDialogAppear(){
        activityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment_list()).addToBackStack(null).commit()
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click())
    }
}