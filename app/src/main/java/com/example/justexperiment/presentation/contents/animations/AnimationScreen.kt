package com.example.justexperiment.presentation.contents.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.boswelja.markdown.material3.MarkdownDocument
import com.example.justexperiment.R
import com.example.justexperiment.presentation.common.ContentView
import com.example.justexperiment.presentation.common.JustCard
import com.example.justexperiment.presentation.contents.animations.util.Circle
import com.example.justexperiment.presentation.contents.animations.util.Rectangle
import com.example.justexperiment.presentation.contents.animations.util.Shapes
import com.example.justexperiment.presentation.contents.animations.util.Square
import com.example.justexperiment.presentation.contents.animations.util.Triangle
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.clickableNoRipple
import com.example.justexperiment.presentation.utils.colors
import kotlin.collections.listOf

val animation = Content(
    875346,
    "Animations",
    "Cool color, size, and shape animations",
    Icons.Default.Category,
    route = Route.AnimationScreen(875346)
)
@Composable
fun AnimationScreen(
    id: Int,
    navController: NavController
) {

    ContentView(
        id = id,
        onNavBack = { navController.popBackStack() }
    ) {
        AnimationScreenContent()
    }

}

@Composable
private fun AnimationScreenContent() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        JustAnimatedVisibility()
        JustAnimateColor()
        JustAnimateSize()
        JustCrossfade()
        JustMarkdown()
        JustInfiniteRotation()
        JustBlur()

    }

}

@Composable
private fun JustAnimatedVisibility() {
    var isVisible by remember { mutableStateOf(false) }
    val enterTransitions =
        listOf(
            fadeIn(),
            slideInHorizontally(),
            slideInVertically(),
            expandIn(),
            expandHorizontally(),
            expandVertically(),
            scaleIn(),
        )

    val exitTransitions =
        listOf(
            fadeOut(),
            slideOutHorizontally(),
            slideOutVertically(),
            scaleOut(),
            shrinkVertically()
        )

    JustCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    isVisible = !isVisible
                }
            ) { Text("Animate visibility") }
            Spacer(Modifier.size(16.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = enterTransitions.random(),
                exit = exitTransitions.random()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center
                ) { Text("I'm animated visibility") }
            }

        }
    }

}

@Composable
private fun JustAnimateColor() {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Transparent
    )
    var animate by remember { mutableStateOf(colors.random()) }
    val animateColor by animateColorAsState(
        targetValue = animate,
        animationSpec = tween(300, easing = EaseIn),
        label = "animate_color"
    )

    JustCard {
        Card(
            shape = RoundedCornerShape(16.dp),
            onClick = {
                animate = colors.random()
            }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(animateColor),
                contentAlignment = Alignment.Center
            ) { Text("Animate my color") }
        }
    }
}

@Composable
private fun JustAnimateSize() {

    var width by remember { mutableStateOf(50.dp) }
    val animateWidth by animateDpAsState(
        targetValue = width,
        animationSpec = tween(300, easing = EaseIn),
    )

    var size by remember { mutableStateOf(100.dp) }
    val animateSize by animateDpAsState(
        targetValue = size,
        animationSpec = tween(300, easing = EaseIn),
    )


    JustCard(Modifier.padding(16.dp)) {
        Row(Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .width(animateWidth)
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable { width = if (width == 50.dp) 100.dp else 50.dp },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = if(width == 50.dp) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
                    contentDescription = "Play/Pause"
                )
            }
            Spacer(Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(animateSize)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable { size = if (size == 100.dp) 200.dp else 100.dp },
                contentAlignment = Alignment.Center
            ) { Text("Animate my size 0_<") }
        }
    }

}

@Composable
private fun JustCrossfade() {
    var shapeState by remember { mutableStateOf(Shapes.CIRCLE) }

    JustCard {
        Crossfade(
            targetState = shapeState
        ) { state ->
            Box() {

            }
            when (state) {
                Shapes.CIRCLE -> Circle(
                    text = "It feels good to be a circle",
                    onClick = { shapeState = Shapes.values().random() }
                )

                Shapes.SQUARE -> Square(
                    text = "Wow! you're doing great!",
                    onClick = { shapeState = Shapes.values().random() }
                )

                Shapes.RECTANGLE -> Rectangle(
                    text = "Oh my god, what a crossfade",
                    onClick = { shapeState = Shapes.values().random() }
                )

                Shapes.TRIANGLE -> Triangle(
                    text = "You made me triangle, thank youuu!",
                    onClick = { shapeState = Shapes.values().random() }
                )
            }

        }
    }
}


@Composable
private fun JustMarkdown() {
    var isMdText by remember { mutableStateOf(false) }

    JustCard(Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { isMdText = !isMdText }
            ) { Text(if (isMdText) "Hide MD" else "Show MD") }

            Crossfade(
                targetState = isMdText,
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
            ) { state ->
                when (state) {
                    true -> {
                        MarkdownDocument(
                            markdown = "* Hi!\nI am ~Markdown~ `text`,\nI can be **bold** or *italic* or ```Code formatted``` or \n# BIG text or \n> this is a block quote \bor ~~strikethrough~~",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    false -> {
                        MarkdownDocument(
                            markdown = "Hi!\nI am normal text",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

            }
        }
    }

}

@Composable
private fun JustInfiniteRotation() {


    val infiniteTransition = rememberInfiniteTransition()

    // Square
    val rotateSquare by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseIn),
            repeatMode = RepeatMode.Reverse
        )
    )


    //Rectangle
    val infiniteTransition2 = rememberInfiniteTransition()
    val rotateRecWithKeyframes by infiniteTransition2.animateFloat(
        initialValue = 0f,
        targetValue = 359f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
            }
        )
    )


    // Circle-1
    var startColor by remember { mutableStateOf(colors.random()) }
    var endColor by remember { mutableStateOf(colors.random()) }
    val infiniteColorTransition by infiniteTransition.animateColor(
        initialValue = startColor,
        targetValue = endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    LaunchedEffect(infiniteColorTransition) {
        if (infiniteColorTransition == endColor) {
            startColor = endColor
            endColor = colors.random()
        }
    }

    //Circle-2




    JustCard(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Square(
                modifier = Modifier
                    .rotate(rotateSquare)
            ) { }

            Spacer(Modifier.size(50.dp))

            Rectangle(
                modifier = Modifier
                    .rotate(rotateRecWithKeyframes)
            ) { }

            Spacer(Modifier.size(50.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Circle(color = infiniteColorTransition) { }
                Circle(
                    modifier = Modifier.rotate(rotateSquare),
                    brushColor = Brush.sweepGradient(colors)
                ) { }
            }

        }
    }
}


@Composable
private fun JustBlur() {

    var hasClicked by remember { mutableStateOf(false) }
    val animateBlur by animateDpAsState(
        targetValue = if (hasClicked) 4.dp else 0.dp,
        animationSpec = tween(1000, delayMillis = 100)
    )

    JustCard(
        cardElevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickableNoRipple(onClick = { hasClicked = !hasClicked }),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = "blurred-image",
                modifier = Modifier
                    .size(150.dp)
                    .blur(animateBlur),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "I'm blurred text\non Android 12 and above",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .blur(2.dp, 4.dp)
        )
    }
}


