package com.example.justexperiment.presentation.contents.pathline

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PointF
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AllInclusive
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.justexperiment.presentation.common.ContentView
import com.example.justexperiment.presentation.common.JustCard
import com.example.justexperiment.presentation.common.JustSlider
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.justexperiment.presentation.contents.pathline.util.SampleAudioViewModel
import com.example.justexperiment.presentation.utils.showToast

val pathLine = Content(
    id = 697345,
    title = "Path line",
    description = "Path in jetpack compose, drawbehind, canvas, arc, cube, triangle.",
    icon = Icons.Rounded.AllInclusive,
    route = Route.PathLineScreen(697345)
)

@Composable
fun PathLineScreen(
    id: Int,
    navController: NavController
) {
    ContentView(id, onNavBack = { navController.popBackStack() }) {
        PathLineContent()

    }

}

@Composable
private fun PathLineContent(

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JustRandom()
        JustAudioVisualizer()
        JustCube()
        JustFlowerPath()
        JustGridGraph()
        JustBeizerCurve()

    }
}

@Composable
private fun JustCube() {
    JustCard(modifier = Modifier.padding(12.dp)) {
        Box(
            modifier = Modifier
                .size(250.dp, 200.dp)
                .drawBehind {

                    val edgeColor = Color.Blue
                    val strokeWidth = 20f

                    val center = this.center
                    val width = size.width
                    val height = size.height

                    val path = Path().apply {
                        moveTo(width / 2, 0f)
                        lineTo(0f, height / 3)
                        lineTo(0f, (height * 2) / 3)
                        lineTo(width / 2, height)
                        lineTo(width, (height * 2) / 3)
                        lineTo(width, height / 3)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = edgeColor,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    //from center
                    val downCenter = Offset(width / 2, (height / 2) + 30f)
                    drawLine(
                        start = downCenter,
                        end = Offset(0f, height / 3),
                        color = edgeColor,
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        start = downCenter,
                        end = Offset(width, height / 3),
                        color = edgeColor,
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        start = downCenter,
                        end = Offset(width / 2, height),
                        color = edgeColor,
                        strokeWidth = strokeWidth
                    )
                }
        )
    }
}

@Composable
private fun JustGridGraph() {

    val df by remember { mutableStateOf("#.##") }
    var gridSpace by remember { mutableFloatStateOf(50f) }

    JustCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(df.format(gridSpace))


                Spacer(Modifier.size(12.dp))
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .drawBehind {
                            val width = size.width
                            val height = size.height

                            val color = Color.DarkGray

                            val numOfGridLines = (width / gridSpace).roundToInt()
                            //horizontal line
                            for (i in 0..numOfGridLines) {
                                drawLine(
                                    start = Offset(gridSpace * i, 0f),
                                    end = Offset(gridSpace * i, height),
                                    color = color
                                )

                                drawLine(
                                    start = Offset(0f, gridSpace * i),
                                    end = Offset(width, gridSpace * i),
                                    color = color
                                )
                            }

                        }
                )
            }
            JustSlider(
                label = "Grid space",
                value = gridSpace,
                onValueChange = { gridSpace = it },
                range = 10f..100f
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun JustBeizerCurve() {

    val canvasSize by remember { mutableStateOf(200.dp) }

    var x1 by remember { mutableFloatStateOf((canvasSize / 2).value) }
    var y1 by remember { mutableFloatStateOf((canvasSize).value) }

    var x2 by remember { mutableFloatStateOf((canvasSize / 2).value) }
    var y2 by remember { mutableFloatStateOf(0f) }

    JustCard {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(canvasSize)
                    .drawBehind {

                        val w = size.width
                        val h = size.height

                        val start = PointF(0f, h)
                        val end = PointF(w, 0f)


                        val path = Path().apply {
                            moveTo(start.x, start.y)
                            cubicTo(
                                x1, y1,
                                x2, y2,
                                end.x, end.y
                            )

                        }


                        drawPath(
                            path = path,
                            color = Color.Green,
                            style = Stroke(
                                width = 4f,
                            )
                        )

                        drawCircle(
                            color = Color.Blue,
                            center = Offset(start.x, start.y),
                            radius = 5f
                        ) // Start
                        drawCircle(
                            color = Color.Red,
                            center = Offset(x1, y1),
                            radius = 5f
                        ) // Control point
                        drawCircle(
                            color = Color.Red,
                            center = Offset(x2, y2),
                            radius = 5f
                        ) // Control point
                        drawCircle(
                            color = Color.Blue,
                            center = Offset(end.x, end.y),
                            radius = 5f
                        ) // End
                    }
            )

            //            (x1,y1) ------(control point)
            JustSlider(
                label = "X1",
                value = x1,
                onValueChange = { x1 = it },
                range = 0f..400f
            )
            JustSlider(
                label = "Y1",
                value = y1,
                onValueChange = { y1 = it },
                range = 0f..400f
            )

            //      (x2,y2)------(control point)
            JustSlider(
                label = "X2",
                value = x2,
                onValueChange = { x2 = it },
                range = 0f..400f
            )
            JustSlider(
                label = "Y2",
                value = y2,
                onValueChange = { y2 = it },
                range = 0f..400f
            )


        }
    }
}


@Composable
private fun JustFlowerPath() {

    val animate = remember { Animatable(0f) }
    var k by remember { mutableIntStateOf(12) }

    LaunchedEffect(k) {
        animate.animateTo(0f)
        animate.animateTo(
            1f,
            animationSpec = tween(4000)
        )
    }

    JustCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .drawBehind {
                        val centerX = center.x
                        val centerY = center.y
                        val scale = size.minDimension / 2.5f

                        val path = Path()

                        var theta = 0.0
                        val step = 0.01

                        val r0 = cos(k * theta)
                        val x0 = (r0 * cos(theta) * scale + centerX).toFloat()
                        val y0 = (r0 * sin(theta) * scale + centerY).toFloat()

                        path.moveTo(x0, y0)

                        while (theta < 2 * PI) {
                            val r = cos(k * theta)
                            val x = (r * cos(theta) * scale + centerX).toFloat()
                            val y = (r * sin(theta) * scale + centerY).toFloat()
                            path.lineTo(x, y)
                            theta += step
                        }

                        val pathMeasure = android.graphics.PathMeasure()
                        pathMeasure.setPath(path.asAndroidPath(), false)
                        val pm = PathMeasure()
                        pm.setPath(path, false)

                        val animatingPathLength = pathMeasure.length * animate.value
                        drawPath(
                            path = path,
                            color = Color.Magenta,
                            style = Stroke(
                                width = 5f,
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(
                                        animatingPathLength,
                                        pathMeasure.length
                                    )
                                )
                            )
                        )

                        val pos = pm.getPosition(animatingPathLength)
                        drawCircle(
                            radius = 5f,
                            center = pos,
                            color = Color.Blue
                        )

                    },
            )
            JustSlider(
                label = "Petals($k)",
                value = k.toFloat(),
                onValueChange = { k = it.toInt() },
                range = 0f..50f
            )
        }

    }
}


// SierpinskiCarpetBitmap
@Composable
private fun JustRandom(
    sizeDp: Dp = 300.dp,
    pointRadiusPx: Float = 2f,
    delayMs: Long = 15L
) {
    val density = LocalDensity.current
    val sizePx = with(density) { sizeDp.toPx().toInt() }

    // Android bitmap + canvas buffer
    val bitmap = remember {
        createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    }


    val androidCanvas = remember { android.graphics.Canvas(bitmap) }
    val paint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            isAntiAlias = false
            style = android.graphics.Paint.Style.FILL
        }
    }

    // Compose image wrapper
    var frameTick by remember { mutableStateOf(0) }

    // Initial chosen point
    val chosenPoint = remember { Offset(20f, 20f) }

    // Mutable state for animation toggle
    var isAnimating by remember { mutableStateOf(false) }

    // Current point (not stored in Compose list)
    var currentPoint by remember { mutableStateOf<Offset?>(null) }

    // Precompute anchor points in pixel space
    val anchors = remember(sizePx) {
        listOf(
            "A" to Offset(0f, 0f),
            "B" to Offset(sizePx.toFloat(), 0f),
            "C" to Offset(sizePx.toFloat(), sizePx.toFloat()),
            "D" to Offset(0f, sizePx.toFloat()),
            "M" to Offset(sizePx / 2f, 0f),
            "N" to Offset(sizePx.toFloat(), sizePx / 2f),
            "O" to Offset(sizePx / 2f, sizePx.toFloat()),
            "P" to Offset(0f, sizePx / 2f)
        )
    }

    // Clear bitmap once at start
    LaunchedEffect(Unit) {
        androidCanvas.drawColor(Color.White.toArgb())
        // Draw initial chosen point
        androidCanvas.drawCircle(chosenPoint.x, chosenPoint.y, pointRadiusPx, paint)
        frameTick++ // trigger initial display
    }

    // Animation loop—mutates bitmap only, minimal Compose state
    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            val (label, target) = anchors.random()

            val next = if (currentPoint != null) {
                newPointRatio(currentPoint!!, target, t = 2f / 3f) // 2:1 from p2 perspective
            } else {
                newPointRatio(chosenPoint, target, t = 2f / 3f)
            }

            // Draw into bitmap buffer
            androidCanvas.drawCircle(next.x, next.y, pointRadiusPx, paint)

            // Update current point and trigger a lightweight repaint
            currentPoint = next
            frameTick++

            delay(delayMs)
        }
    }

    JustCard {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Display the bitmap—Compose only paints the image, no per-point work
            androidx.compose.foundation.Canvas(modifier = Modifier.size(sizeDp)) {
                // Wrap Android bitmap as Compose ImageBitmap
                val imageBitmap = bitmap.asImageBitmap()
                drawImage(imageBitmap)
            }

            Button(onClick = { isAnimating = !isAnimating }) {
                Text(if (isAnimating) "Stop $frameTick" else "Start $frameTick")
            }
        }
    }
}

// Linear interpolation helper: p = (1 - t) * p1 + t * p2
private fun newPointRatio(p1: Offset, p2: Offset, t: Float): Offset {
    val x = (1f - t) * p1.x + t * p2.x
    val y = (1f - t) * p1.y + t * p2.y
    return Offset(x, y)
}

@Composable
private fun JustAudioVisualizer() {

    val density = LocalDensity.current
    val sidePx = with(density) { 200.dp.toPx().toInt() }
    val bitmap = remember {
        createBitmap(
            width = sidePx,
            height = sidePx,
            config = Bitmap.Config.ARGB_8888
        )
    }

    val androidCanvas = remember { android.graphics.Canvas(bitmap) }
    var imageBitmap by remember { mutableStateOf(bitmap.asImageBitmap()) }

    val paint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.BLUE
            isAntiAlias = false
            style = android.graphics.Paint.Style.FILL
        }
    }

    val sampleAudioViewModel: SampleAudioViewModel = viewModel()

    val context = LocalContext.current
    var shouldRecord by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                context.showToast("Permission denied")
            } else {
                context.showToast("Permission granted")
                shouldRecord = true
            }
        }
    )


    val audioBuffer by sampleAudioViewModel.sampleBufferState.collectAsState()
    val rms by sampleAudioViewModel.rms.collectAsState()
    var rmsRange by remember { mutableStateOf(0f) } //0..1f

    val gradientBG = remember {
        Brush.linearGradient(
            listOf(
                Color.Green, Color.Yellow, Color.Red
            )
        )
    }

    LaunchedEffect(audioBuffer) {

        androidCanvas.drawColor(Color.White.toArgb())
        audioBuffer.forEachIndexed { i, sample ->
            val x = 400f * i / audioBuffer.size
            val y = 400f * (sample.toFloat() / Short.MAX_VALUE)
            val normalized = rms?.let { it / Short.MAX_VALUE } ?: 0.0
            rmsRange = normalized.toFloat()
            androidCanvas.drawCircle(
                x, y + (400 / 2f),
                2f,
                paint
            )
        }
        imageBitmap = bitmap.asImageBitmap()
    }

    LaunchedEffect(shouldRecord) {
        if (shouldRecord) {
            sampleAudioViewModel.shouldStop.value = true
            sampleAudioViewModel.startRecording(context)
        } else {
            sampleAudioViewModel.shouldStop.value = false
        }
    }



    JustCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sample Visualizer
            val rmsColor = when (rmsRange) {
                0f -> Color.Gray to 0f
                in 0f..0.009f -> Color.Green to 10f * rmsRange
                in 0.0091f..0.0999999f -> Color.Yellow to 10f * rmsRange
                else -> Color.Red to 1f
            }

            Log.d("RMS", "rms: $rms -- rmsRange: $rmsRange")
            Box(
                Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth(rmsColor.second)
                    .height(8.dp)
                    .background(
                        if (rmsColor.second == 0f) Brush.linearGradient(listOf(Color.Gray)) else gradientBG
                    )
            )

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .drawBehind {
                        drawImage(imageBitmap)
                    }
            )

            Canvas(
                modifier = Modifier
                    .size(90.dp)
            ){
                drawCircle(
                    brush = gradientBG,
                    radius = (rmsRange * size.minDimension / 2f)+(size.minDimension / 2f),
                    center = center
                )
            }

            Row {
                Button(onClick = {
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        shouldRecord = !shouldRecord
                    } else {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }) {
                    Text(if (!shouldRecord) "Start" else "Stop")
                }
            }

        }
    }
}
