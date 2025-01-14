package com.example.secondhomework

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.decode.GifDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: CatViewModel = viewModel()
            CatScreen(viewModel)
        }
    }
}

@Composable
fun CatScreen(viewModel: CatViewModel) {

    val cat = viewModel.catData
    val loadingIndexOfImage = viewModel.loadingIndex
    val errorMessageOfImage = viewModel.errorMessage

    Column {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            itemsIndexed(cat) { index, catId ->

                if (index == loadingIndexOfImage.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(350.dp, 350.dp)
                            .background(color = Color.White.copy(alpha = 0f))
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {

                    if (catId != null) {

                        if (!catId.isGiphy) {

                            CatImage(
                                url = catId.catResponse!!.url,
                                width = catId.catResponse.width,
                                height = catId.catResponse.height,
                                description = "Random Cat"
                            )
                        } else {
                            CatImage(
                                url = catId.giphyResponse!!.data.images.original.url,
                                width = (catId.giphyResponse.data.images.original.width).toInt(),
                                height = (catId.giphyResponse.data.images.original.height).toInt(),

                                description = "Funny cat GIF"
                            )
                        }

                    }
                }
            }
        }

        if (errorMessageOfImage.value != null) {
            Text(
                text = "Error ${errorMessageOfImage.value}",
                color = Color.Red,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                colors = ButtonColors(Color.Gray, Color.White, Color.Black, Color.White),
                onClick = { viewModel.fetchRandomCat() }
            ) {
                if (errorMessageOfImage.value != null) {
                    Text("Try again")
                } else {
                    Text("Get Cat")
                }
            }

            Button(
                colors = ButtonColors(Color.DarkGray, Color.White, Color.Black, Color.White),
                onClick = { viewModel.fetchRandomGif() }
            ) {
                if (errorMessageOfImage.value != null) {
                    Text("Try again")
                } else {
                    Text("Get GIF Cat")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CatImage(
    url: String,
    width: Int = 350,
    height: Int = 600,
    description: String
) {
    val maxWidth = width.dp
    val maxHeight = height.dp
    val aspectRatio = width.toFloat() / height.toFloat()
    val displayWidth: Dp
    val displayHeight: Dp

    if (aspectRatio > 1) {
        // Изображение шире, чем выше
        displayWidth = maxWidth
        displayHeight = maxWidth / aspectRatio
    } else {
        // Изображение выше, чем шире
        displayHeight = maxHeight
        displayWidth = maxHeight * aspectRatio
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(GifDecoder.Factory())
            .crossfade(true)
            .build(),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(displayWidth, displayHeight)
            .clip(RoundedCornerShape(16.dp))
            .clickable {

            }
    )
}


@Preview(showSystemUi = true)
@Composable
fun Prew() {
    val viewModel: CatViewModel = viewModel()
    CatScreen(viewModel)
}


