package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycity.ui.MyCityApp
import com.example.mycity.ui.theme.MyCityTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MyCityTheme {
                Surface {
                    val windowSize = calculateWindowSizeClass(this)
                    MyCityApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCityAppCompactPreview() {
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun MyCityAppMediumPreview() {
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Medium)
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun MyCityAppExpandedPreview() {
    MyCityTheme {
        Surface {
            MyCityApp(windowSize = WindowWidthSizeClass.Expanded)
        }
    }
}
