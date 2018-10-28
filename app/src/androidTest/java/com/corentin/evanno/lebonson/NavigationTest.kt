package com.corentin.evanno.lebonson

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.corentin.evanno.lebonson.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTest {

    @get:Rule
    val taskActivity = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)


    @Test
    fun testNavigation() {
        taskActivity.launchActivity(null)

        onView(withText("Album 1")).perform(click())
        onView(withText("Album #1")).check(matches(isDisplayed()))

        back()
        onView(withText("LeBonSon")).check(matches(isDisplayed()))

        onView(withText("Album 2")).perform(click())
        onView(withText("Album #2")).check(matches(isDisplayed()))

        back()
        onView(withText("LeBonSon")).check(matches(isDisplayed()))
    }

    private fun back() {
        val activity = taskActivity.activity
        activity.runOnUiThread {
            activity.onBackPressed()
        }
    }
}