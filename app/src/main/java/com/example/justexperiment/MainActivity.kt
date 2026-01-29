package com.example.justexperiment

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import android.os.Bundle
import android.view.contentcapture.DataShareRequest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.justexperiment.ui.theme.JustExperimentTheme
import androidx.navigation.compose.rememberNavController
import com.example.justexperiment.presentation.navigation.NavigationGraph


class MainActivity : ComponentActivity() {



    lateinit var shader: RuntimeShader



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*----------------Initialize Shader---------------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             shader = RuntimeShader(SHADER_SRC)
        }
        val photo = BitmapFactory.decodeResource(resources, R.drawable.app_icon)





        enableEdgeToEdge()
        setContent {
            JustExperimentTheme {
                val navController = rememberNavController()
                NavigationGraph(navController)

//                ShaderDemoScreen(shader, photo)
            }
        }
    }
}


private const val SHADER_SRC = """
    uniform float2 size;
    uniform float time;
    uniform shader composable;
    
    half4 main(float2 fragCoord){
        return composable.eval(fragCoord).rgba:
    }
    
"""


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun ShaderDemoScreen(
    shader: RuntimeShader,
    photo: Bitmap
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Image(
            bitmap = photo.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .onSizeChanged { size ->
                    shader.setFloatUniform(
                        "size",
                        size.width.toFloat(),
                        size.height.toFloat()
                    )
                }
                .graphicsLayer {
                    clip = true
                    renderEffect =
                        RenderEffect
                            .createRuntimeShaderEffect(shader, "composable")
                            .asComposeRenderEffect()
                }
        )

    }
}








