package com.example.pointofsale

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterIntentTest {
    @Rule
    @JvmField
    var intentTestRule = IntentsTestRule(Register::class.java)

    @Test
    fun onRegisterSuccess(){
        onView(withId(R.id.fullName))
            .perform(ViewActions.typeText("Hellomida"))
        onView(withId(R.id.emailAddress))
            .perform(ViewActions.typeText("Hellomida@gmail.com"))
        onView(withId(R.id.password))
            .perform(ViewActions.typeText("Hellomida"))
        onView(withId(R.id.signup)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(Profile::class.java.name))
    }
}