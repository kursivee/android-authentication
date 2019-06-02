package com.kursivee.authentication.view.login

import android.widget.ProgressBar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kursivee.util.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class ProgressBarJobTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val testRule = CoroutinesTestRule()

    private lateinit var progressBar: ProgressBar

    @Before
    fun setup() {
        progressBar = ProgressBar(getApplicationContext(),
            Robolectric.buildAttributeSet()
                .addAttribute(android.R.attr.max, "200")
                .addAttribute(android.R.attr.indeterminate, "false")
                .addAttribute(android.R.attr.indeterminateOnly, "false")
                .build())
    }

    @Test
    fun `when starting progress bar it should increment the progress bar`() =
        runBlockingTest {
            val job = ProgressBarJob(progressBar)
            job.start()
            assertThat(progressBar.progress)
                .isNotEqualTo(0)
            job.stop()
        }

    @Test
    fun `when stopping progress bar it should reset progress to 0`() =
        runBlockingTest {
            val job = ProgressBarJob(progressBar)
            job.start()
            job.stop()
            assertThat(progressBar.progress)
                .isEqualTo(0)
        }
}