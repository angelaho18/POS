package com.example.pointofsale
import androidx.test.ext.junit.runners.AndroidJUnit4
import  androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.action.ViewActions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class testOne{
    @Rule @JvmField
    var activitytestrule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun signin() {
        onView(withId(R.id.logMail)).perform(ViewActions
            .typeText("hello world"))
        onView(withId(R.id.logPass)).perform(ViewActions
            .typeText("hello"))
        onView(withId(R.id.signinbutton))
            .perform(ViewActions.click())
    }

}