package com.example.rafaelanastacioalves.moby

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rafaelanastacioalves.moby.newslisting.NewsListingActivity
import com.example.rafaelanastacioalves.moby.util.HelperMethods
import com.example.rafaelanastacioalves.moby.util.RestServiceTestHelper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class NewsListingActivityTest {

    @get:Rule
    var mainActivityActivityTestRule = ActivityTestRule(NewsListingActivity::class.java, true, false)
    private val fileNameNewsListOKResponse = "news_ok_response.json"
    private var server: MockWebServer? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        server = MockWebServer()
        server!!.start(1234)
        InstrumentationRegistry.registerInstance(InstrumentationRegistry.getInstrumentation(), Bundle())
        server!!.url("/").toString()


    }

    private val testedNewString = "Article Number 2 -> ID: 22980676"

    @Test
    @Throws(IOException::class)
    fun shouldNewsListSuccess() {
        server!!.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(
                        InstrumentationRegistry.getInstrumentation().context, fileNameNewsListOKResponse)
                )
        )

        val intent = Intent()

        mainActivityActivityTestRule.launchActivity(intent)

        val testedPosition = 1
        onView(withId(R.id.news_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testedPosition))
        onView(withId(R.id.news_list)).check(matches(HelperMethods.showNewItemWithTitle(testedNewString, testedPosition)))
    }


    @After
    @Throws(Exception::class)
    fun tearDown() {
        server!!.shutdown()
    }
}
