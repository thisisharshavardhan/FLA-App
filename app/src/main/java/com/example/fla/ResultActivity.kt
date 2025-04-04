package com.example.fla

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fla.api.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imagePath = intent.getStringExtra("imagePath")
        val imageFile = imagePath?.let { File(it) }

        setContent {
            var result by remember { mutableStateOf("Uploading...") }
            var isLoading by remember { mutableStateOf(true) }
            var success by remember { mutableStateOf<Boolean?>(null) }
            var ingredients by remember { mutableStateOf<List<String>>(emptyList()) }
            var errorMessage by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                imageFile?.let {
                    val response = uploadImage(it)
                    result = response

                    // ✅ Parse outside Compose block
                    try {
                        val jsonObject = JSONObject(response)
                        success = jsonObject.getBoolean("success")

                        if (success == true) {
                            TODO("Extract data from response ............................................................................................................")
                        }
                    } catch (e: Exception) {
                        errorMessage = "No ingredients detected please properly take the image."
                    }
                    isLoading = false
                } ?: run {
                    result = "No Image Found"
                    isLoading = false
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Uploading...", color = Color.White)
                } else {
                    val activity = LocalActivity()
                    errorMessage?.let {
                        ErrorCard(
                            errorMessage = errorMessage.toString(),
                            onGoBack = { activity?.finish() }  // this finishes the activity and takes the user back
                        )
                    } ?: run {
                        if (success == true) {
                            TODO("success UI result....................................................................................................................")
                        } else {
                            Text("Unable to process.", color = Color.Red, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }

    @Composable
    @ReadOnlyComposable
    fun LocalActivity(): Activity? {
        var context = LocalContext.current
        while (context is android.content.ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        return null
    }

    @Composable
    fun ErrorCard(errorMessage: String, onGoBack: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            androidx.compose.material3.Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = Color(0xFF2C2C2C),
                ),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$errorMessage",
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Go Back Button
                    androidx.compose.material3.Button(
                        onClick = { onGoBack() }
                    ) {
                        Text(text = "Go Back")
                    }
                }
            }
        }
    }



    private suspend fun uploadImage(imageFile: File): String {
        return try {
            val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            val response = RetrofitInstance.api.uploadImage(body)

            if (response.isSuccessful) {
                response.body()?.string() ?: "Empty Response"
            } else {
                "Error: ${response.code()}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Exception: ${e.message}"
        }
    }
}
