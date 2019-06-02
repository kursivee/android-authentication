package com.kursivee.authentication.view.main

import android.widget.ProgressBar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kursivee.authentication.R
import com.kursivee.authentication.view.progressbar.ProgressBarJob
import com.kursivee.authentication.view.progressbar.ProgressBarJobFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric

@RunWith(AndroidJUnit4::class)
class MainProgressBarTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var activity: MainActivity
    private lateinit var clLoading: ConstraintLayout
    private lateinit var pbLoading: ProgressBar
    private lateinit var progressBar: MainProgressBarComponent

    private val progressBarJobFactory = mockk<ProgressBarJobFactory>()

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        clLoading = activity.findViewById(R.id.cl_loading)
        pbLoading = clLoading.findViewById(R.id.pb_loading)
        every {
            progressBarJobFactory.getProgressBarJob(any())
        } answers {
            mockk(relaxUnitFun = true)
        }
        progressBar = MainProgressBarComponent(clLoading, progressBarJobFactory)
    }

    @Test
    fun `when starting the progress bar it should show the view`() {
        progressBar.show()
        assertThat(clLoading.isVisible).isTrue()
        assertThat(pbLoading.isVisible).isTrue()
    }

    @Test
    fun `when starting the progress bar it should start the progress`() {
        progressBar.show()
        verify(exactly = 1) { progressBar.getProgressBarJob().start() }
    }

    @Test
    fun `when stopping the progress bar it should dismiss the view`() {
        progressBar.show()
        progressBar.dismiss()
        assertThat(clLoading.isVisible).isFalse()
        assertThat(pbLoading.isVisible).isFalse()
    }
}