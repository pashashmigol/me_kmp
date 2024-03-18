package com.me.diary

import App
import MainContent
import ProvideComponentContext
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.arkivanov.decompose.defaultComponentContext
import data.di.di
import org.kodein.di.direct

class MainActivity : ComponentActivity() {
    private lateinit var jankStats: JankStats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponentContext = defaultComponentContext()

        setContent {
            MaterialTheme {
                Surface {
                    ProvideComponentContext(rootComponentContext) {
                        MainContent(di.direct)
                    }
                }
            }
        }

        window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0)?.let {
                val metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(it)
                jankStats = JankStats.createAndTrack(window, jankFrameListener)

                // add activity name as state
                metricsStateHolder.state?.putState("Activity", javaClass.simpleName)
            }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

private val jankFrameListener = JankStats.OnFrameListener { frameData ->
    // A real app could do something more interesting, like writing the info to local storage and later on report it.
//    Log.v("###", frameData.toString())
}
