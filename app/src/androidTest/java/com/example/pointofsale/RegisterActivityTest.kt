package com.example.pointofsale

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest{
    @Rule @JvmField
    var activityTestRule = ActivityTestRule(Register::class.java)

    @Test
    fun showErrorNameEmpty(){
        onView(withId(R.id.emailAddress)).perform(typeText("Hellomida@gmail.com"))
        onView(withId(R.id.password)).perform(typeText("Hellomida"))
        onView(withId(R.id.signup)).perform(click())
        onView(withId(R.id.fullName)).check(matches(hasErrorText("Please input your FullName")))
    }

    @Test
    fun showErrorEmailEmpty(){
        onView(withId(R.id.fullName)).perform(typeText("Hellomida"))
        onView(withId(R.id.password)).perform(typeText("Hellomida"))
        onView(withId(R.id.signup)).perform(click())
        onView(withId(R.id.emailAddress)).check(matches(hasErrorText("Please input your Email Address")))
    }

    @Test
    fun showErrorPassEmpty(){
        onView(withId(R.id.fullName)).perform(typeText("Hellomida"))
        onView(withId(R.id.emailAddress)).perform(typeText("Hellomida@gmail.com"))
        onView(withId(R.id.signup)).perform(click())
        onView(withId(R.id.password)).check(matches(hasErrorText("Please input your Password")))
    }

    @Test
    fun checkValidEmail(){
        onView(withId(R.id.emailAddress)).perform(typeText("Hellomida"))
        onView(withId(R.id.emailAddress)).check(matches(hasErrorText("Please Enter Valid Email Address")))
        onView(withId(R.id.emailAddress)).perform(ViewActions.clearText(), typeText("Hellomida@gmail.com"))
        onView(withId(R.id.emailAddress)).check(matches(not(hasErrorText("Please Enter Valid Email Address"))))
    }
}