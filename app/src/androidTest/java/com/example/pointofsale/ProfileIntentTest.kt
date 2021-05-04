package com.example.pointofsale

import android.R
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.pointofsale.ImageViewHasDrawableMatcher.hasDrawable
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileIntentTest {
    var intentTestRule = IntentsTestRule(Profile::class.java, true, false)

    @Test
    fun openCamera() {
        intentTestRule.launchActivity(Intent(getTargetContext(), Profile::class.java))
        val activityResult = createImageCaptureStub()
        onView(withId(com.example.pointofsale.R.id.cameraBtn)).perform(click())
        intending(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(
            activityResult)
    }

    @Test
    fun checkTakenImage(){
//        intentTestRule.launchActivity(Intent(getTargetContext(), Profile::class.java))
        onView(withId(com.example.pointofsale.R.id.profilePic)).check(matches(
            not(hasDrawable())))
        onView(withId(com.example.pointofsale.R.id.cameraBtn)).perform(click())
        onView(withId(com.example.pointofsale.R.id.profilePic)).check(matches(
            hasDrawable()))
    }

    private fun createImageCaptureStub(): Instrumentation.ActivityResult {
        var bundle = Bundle()
        bundle.putParcelable("data",
            BitmapFactory.decodeResource(intentTestRule.activity.resources, R.drawable.ic_menu_camera))

        var result = Intent()
        result.putExtras(bundle)

        return Instrumentation.ActivityResult(Activity.RESULT_OK, result)
    }
}