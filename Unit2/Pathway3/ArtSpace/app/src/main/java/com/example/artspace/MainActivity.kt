package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

data class Artwork(
    val drawableResId: Int,
    val title: String,
    val artist: String,
    val year: String
)

private val artworks = listOf(
    Artwork(R.drawable.van_gogh_starry_night, "The Starry Night", "Vincent van Gogh", "1889"),
    Artwork(R.drawable.van_gogh_landscape, "Green Wheat Field with Cypress", "Vincent van Gogh", "1889"),
    Artwork(R.drawable.van_gogh_cottages, "Wheat Field with Cottages", "Vincent van Gogh", "1890")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentIndex by remember { mutableIntStateOf(0) }
    val artwork = artworks[currentIndex]

    fun goToPrevious() {
        currentIndex = if (currentIndex == 0) artworks.lastIndex else currentIndex - 1
    }

    fun goToNext() {
        currentIndex = if (currentIndex == artworks.lastIndex) 0 else currentIndex + 1
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth > maxHeight

        if (isWideScreen) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtworkWall(artwork = artwork, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(32.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ArtworkDescriptor(artwork = artwork)
                    Spacer(modifier = Modifier.height(24.dp))
                    DisplayController(onPreviousClick = ::goToPrevious, onNextClick = ::goToNext)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ArtworkWall(artwork = artwork)
                Spacer(modifier = Modifier.height(32.dp))
                ArtworkDescriptor(artwork = artwork)
                Spacer(modifier = Modifier.height(32.dp))
                DisplayController(onPreviousClick = ::goToPrevious, onNextClick = ::goToNext)
            }
        }
    }
}

@Composable
fun ArtworkWall(artwork: Artwork, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Image(
            painter = painterResource(artwork.drawableResId),
            contentDescription = stringResource(R.string.artwork_content_description, artwork.title),
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(artwork: Artwork, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = artwork.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${artwork.artist} (${artwork.year})",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(onClick = onPreviousClick, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.previous))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = onNextClick, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 400)
@Composable
fun ArtSpaceAppLandscapePreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}
