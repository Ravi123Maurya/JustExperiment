package com.example.justexperiment.presentation.contents.ktor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.justexperiment.presentation.common.NavBackScaffold
import com.example.justexperiment.presentation.utils.contentList
import kotlinx.coroutines.launch

@Composable
fun CensoredTextScreen(
    contentId: Int,
    navController: NavController,
    networkRepository: NetworkDataRepositoryImpl,
){

    val content = remember { contentList.find { it.id == contentId } }

    NavBackScaffold(
        title = content?.title,
        onNavBack = { navController.popBackStack() }
    ){
        CensoredTextContent(
            Modifier.padding(it),
            onButtonClick = { text ->
                networkRepository.censorWords(text)
            }
        )
    }


}

@Composable
fun CensoredTextContent(
    modifier: Modifier = Modifier,
    onButtonClick: suspend (String) -> String
) {
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var censoredText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top)
    ) {

        Spacer(modifier = Modifier.height(48.dp))


        Text(
            text = "If you enter bad words, it will be censored.",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        censoredText?.let{
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.Blue.copy(alpha = 0.1f))
                    .padding(20.dp),
                text = it, color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }


        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = { Text(text = "Enter some text") }
        )

        Button(
            onClick = {
                // Ktor client api call here
                scope.launch {
                    isLoading = true
                    censoredText = null
                    if (text.isEmpty()){
                        censoredText = "Please enter some text"
                        isLoading = false
                        return@launch


                    }
                    val response = onButtonClick(text)
                    censoredText = response
                    isLoading = false

                }

            }
        ) {
            if (isLoading){
                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
            }else{
                Text(text = "Let's See")
            }
        }
    }
}