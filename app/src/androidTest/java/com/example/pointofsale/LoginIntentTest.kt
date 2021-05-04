package com.example.pointofsale

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginIntentTest {
    @Rule @JvmField
    var intentTestRule = IntentsTestRule(Login::class.java)

    @Test
    fun onLoginSuccess(){
        Espresso.onView(ViewMatchers.withId(R.id.logMail))
            .perform(ViewActions.typeText("Hellomida@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.logPass))
            .perform(ViewActions.typeText("Hellomida"))
        Espresso.onView(ViewMatchers.withId(R.id.signinbutton)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(ActivityFragment::class.java.name))
    }

    @Test
    fun goRegister(){
        Espresso.onView(ViewMatchers.withId(R.id.reg)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(Register::class.java.name))
    }
}