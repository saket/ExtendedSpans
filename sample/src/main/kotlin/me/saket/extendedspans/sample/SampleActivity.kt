package me.saket.extendedspans.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.RoundRectSpanPainter
import me.saket.extendedspans.SquigglyUnderlineSpanPainter
import me.saket.extendedspans.rememberSquigglyUnderlineAnimator

class SampleActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      AppTheme {
        Surface(Modifier.fillMaxSize()) {
          ExtendedSpansPreview()
        }
      }
    }
  }
}

@Preview
@Composable
fun ExtendedSpansPreview() {
  val style = MaterialTheme.typography.headlineLarge.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 40.sp,
    lineHeight = 56.sp,
  )

  ProvideTextStyle(style) {
    Column(Modifier.padding(20.dp)) {
      ExtendedSpansText(
        text = buildAnnotatedString {
          append("Give your ")
          withStyle(SpanStyle(background = MaterialTheme.colorScheme.primaryContainer)) {
            append("heart and soul")
          }
          append(" to me")
        }
      )
      ExtendedSpansText(
        modifier = Modifier.padding(top = 32.dp),
        text = buildAnnotatedString {
          append("And life will always be ")
          withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("la vie en rose")
          }
        }
      )
    }
  }
}

@Composable
fun ExtendedSpansText(
  text: AnnotatedString,
  modifier: Modifier = Modifier,
) {
  val underlineAnimator = rememberSquigglyUnderlineAnimator()
  val extendedSpans = remember {
    ExtendedSpans(
      RoundRectSpanPainter(
        cornerRadius = 8.dp,
        padding = PaddingValues(horizontal = 4.dp),
        topMargin = 4.dp,
        bottomMargin = 2.dp,
        stroke = RoundRectSpanPainter.Stroke(
          color = Color.White.copy(alpha = 0.2f)
        ),
      ),
      SquigglyUnderlineSpanPainter(
        color = Color.Yellow,
        width = 4.sp,
        wavePeriod = 20.sp,
        amplitude = 2.sp,
        baselineOffset = 2.sp,
        animator = underlineAnimator
      )
    )
  }

  Text(
    modifier = modifier.drawBehind {
      extendedSpans.draw(this)
    },
    text = remember(text) {
      extendedSpans.extend(text)
    },
    onTextLayout = { result ->
      extendedSpans.onTextLayout(result)
    }
  )
}
