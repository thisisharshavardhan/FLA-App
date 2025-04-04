package com.example.fla

import android.content.Context
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun PermissionScreen(modifier: Modifier = Modifier) {
    val permissions = listOf(Manifest.permission.CAMERA)
    val isGranted = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            isGranted.value = result[Manifest.permission.CAMERA] == true
        }
    )

    // 🔥 Auto-launch permission dialog on first load
    LaunchedEffect(Unit) {
        launcher.launch(permissions.toTypedArray())
    }

    if (isGranted.value) {
        CameraScreen()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Requesting Camera Permission...")
        }
    }
}

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView: PreviewView = remember { PreviewView(context) }
    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    val preview = Preview.Builder().build()
    val imageCapture = remember { ImageCapture.Builder().build() }

    // Bind CameraX lifecycle
    LaunchedEffect(Unit) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

        // Capture Button - Bottom Center
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                onClick = {
                    capturePhoto(context, imageCapture) { filePath ->
                        val intent = Intent(context, ResultActivity::class.java)
                        intent.putExtra("imagePath", filePath)
                        context.startActivity(intent)
                    }

                },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .size(70.dp)
                    .background(Color.White, CircleShape)
                    .padding(8.dp)
                    .background(Color.Red, CircleShape)
            ) {
                // You can add an icon or text if needed
            }
        }
    }
}

fun capturePhoto(context: Context, imageCapture: ImageCapture, onImageSaved: (String) -> Unit) {
    val photoFile = File(
        context.cacheDir,
        "captured_image_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageSaved(photoFile.absolutePath)
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        }
    )
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
    cameraProviderFuture.addListener({
        continuation.resume(cameraProviderFuture.get())
    }, ContextCompat.getMainExecutor(this))
}


