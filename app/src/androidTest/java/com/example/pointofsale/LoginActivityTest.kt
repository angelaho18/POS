package com.example.pointofsale

import android.os.IBinder
import android.service.autofill.Validators.not
import android.view.WindowManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertEquals
import org.hamcrest.Description
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest{
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(Login::class.java)

    @Test
    fun showToastEmailEmpty(){
        onView(withId(R.id.logPass)).perform(typeText("Hellomida"))
        onView(withId(R.id.signinbutton)).perform(click())
        onView(withText("Please input your FullName")).inRoot(withDecorView(not(`is`(activityTestRule.activity.window.decorView)))).check(matches(isDisplayed()))
    }
}