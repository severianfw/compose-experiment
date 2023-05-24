package com.example.composeexperiment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composeexperiment.ui.theme.ComposeExperimentTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeExperimentTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          ColorfulBox()
//          RecompositionLoop()
        }
      }
    }
  }
}

@Composable
fun ColorfulBox() {
  val color by animateColorBetween(Color.Red, Color.Blue)
  Box(
    modifier = Modifier
      .wrapContentSize(Alignment.Center)
      .width(200.dp)
      .height(200.dp)
      .drawBehind {
        drawRect(color)
      }
  ) {}
}

@Composable
private fun animateColorBetween(
  initialValue: Color,
  targetValue: Color
): State<Color> {
  val infiniteTransition = rememberInfiniteTransition()
  return infiniteTransition.animateColor(
    initialValue = initialValue,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(
      animation = tween(2000),
      repeatMode = RepeatMode.Reverse
    )
  )
}

@Composable
private fun RecompositionLoop() {
  Box {
    var imageHeightPx by remember { mutableStateOf(0) }

    Image(
      painter = painterResource(R.drawable.ic_launcher_foreground),
      contentDescription = "I'm above the text",
      modifier = Modifier
        .fillMaxWidth()
        .onSizeChanged { size ->
          imageHeightPx = size.height
        }
    )

    Text(
      text = "I'm below the image",
      modifier = Modifier.padding(
        top = with(LocalDensity.current) { imageHeightPx.toDp() }
      )
    )
  }
}

@Preview(showBackground = true) @Composable fun DefaultPreview() {
  ComposeExperimentTheme {
    ColorfulBox()
  }
}