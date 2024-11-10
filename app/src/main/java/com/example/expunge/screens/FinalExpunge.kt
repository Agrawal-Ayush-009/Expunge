package com.example.expunge.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.expunge.Expunge
import com.example.expunge.ExpungeViewModel
import com.example.expunge.NetworkResult
import com.example.expunge.R
import com.example.expunge.SharedViewmodel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.random.Random


@Composable
fun FinalRedact(
    sharedViewmodel: SharedViewmodel,
    onBackclick: () -> Unit,
) {
    val expungeViewmodel: ExpungeViewModel = hiltViewModel()
    val receivedImage by expungeViewmodel.receivedImage.collectAsState()
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isRedacted by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(28.dp))
        RedactionProgress()

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ReviewText()
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(imageBitmap != null){
                            Image(
                                modifier = Modifier
                                    .size(300.dp)
                                    .padding(8.dp)
                                    .rotate(90f),
                                painter = rememberAsyncImagePainter(model = imageBitmap),
                                contentDescription = "teamlogo",
                                contentScale = ContentScale.Crop,
                            )
                        }else{
                            if (sharedViewmodel.imageBitmap.value != null) {
                                Image(
                                    modifier = Modifier
                                        .size(300.dp)
                                        .padding(8.dp)
                                        .rotate(90f),
                                    painter = rememberAsyncImagePainter(model = sharedViewmodel.imageBitmap.value),
                                    contentDescription = "teamlogo",
                                    contentScale = ContentScale.Crop,
                                )
                            } else {
                                Text(text = "No document selected")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (receivedImage) {
                        is NetworkResult.Error -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 20.dp, 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center

                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .size(18.dp),
                                    painter = painterResource(id = R.drawable.lucide_info),
                                    contentDescription = "trophy",
                                    alignment = Alignment.Center,
                                    colorFilter = ColorFilter.tint(color = Color(0xFFFB442C))
                                )

                                Text(
                                    text = receivedImage.msg.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFB442C)
                                )
                            }
                        }

                        is NetworkResult.Loading -> {
                            Row(
                                modifier = Modifier
                                    .padding(24.dp, 42.dp, 24.dp, 42.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color(0xFFAC0000)
                                )
                            }
                        }

                        is NetworkResult.Start -> {}
                        is NetworkResult.Success -> {
                            imageBitmap = receivedImage.data
                            isRedacted = true
                        }
                    }

                    BackButton(
                        onBackclick = onBackclick
                    )
                    if (!isRedacted) {
                        NextButton(text = "UPLOAD") {
                            expungeViewmodel.uploadImage(
                                sharedViewmodel.imageBitmap.value!!,
                                context
                            )
                        }
                    } else {
                        NextButton(text = "SAVE PHOTO") {
                            imageBitmap?.let { bitmap ->
                                saveBitmapToStorage(context, bitmap, "RedactedImage")
                            }
                        }
                    }
                }
            }
        }
    }
}


fun saveBitmapToStorage(context: Context, bitmap: Bitmap, filename: String): Boolean {
    // Define the directory path and create it if it doesn’t exist
    val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Expunge")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    // Create the file in the specified directory
    val file = File(directory, "$filename.jpg")
    var outputStream: OutputStream? = null

    return try {
        // Open an output stream to the file and compress the bitmap into it
        outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        true // Indicate success
    } catch (e: Exception) {
        e.printStackTrace()
        false // Indicate failure
    } finally {
        // Close the output stream
        outputStream?.close()
    }
}

@Composable
fun RedactionProgress() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Circle(
            isSelected = true,
            label = "Upload document",
            modifier = Modifier.weight(1f)
        )
        Circle(
            isSelected = true,
            label = "Select Redaction Level",
            modifier = Modifier.weight(1f)
        )
        Circle(isSelected = true, label = "Review", modifier = Modifier.weight(1f))
    }
}

@Composable
fun Circle(isSelected: Boolean, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isSelected) Color.Gray else Color.LightGray,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 8.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ReviewText() {
    Text(
        text = "Review",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
    )
}

@Composable
fun Document(
    imageBitmap: ImageBitmap,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    sharedViewmodel: SharedViewmodel
) {
    var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(imageUrl) {
        imageUrl?.let {
            Glide.with(context)
                .asBitmap()
                .load(it)
                .into(object : CustomTarget<android.graphics.Bitmap>() {
                    override fun onResourceReady(
                        resource: android.graphics.Bitmap,
                        transition: Transition<in android.graphics.Bitmap>?
                    ) {
                        bitmap = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle cleanup if necessary
                    }
                })
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("image1", "$bitmap")
        if (sharedViewmodel.imageBitmap.value != null) {
            Image(
                modifier = Modifier
                    .size(300.dp)
                    .padding(8.dp)
                    .rotate(90f),
                painter = rememberAsyncImagePainter(model = sharedViewmodel.imageBitmap.value),
                contentDescription = "teamlogo",
                contentScale = ContentScale.Crop,
            )
        } else {
            Text(text = "No document selected")
        }
    }
}

@Composable
fun BackButton(
    onBackclick: () -> Unit
) {
    Button(
        onClick = {
            onBackclick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(start = 8.dp, end = 8.dp)
            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.background_gray)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            text = "GO BACK",
            color = colorResource(id = R.color.go_back_button_text),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DocumentPreviewPreview(
    imageBitmap: ImageBitmap,
    sharedViewmodel: SharedViewmodel
) {
    Document(
        imageUrl = "https://akm-img-a-in.tosshub.com/indiatoday/images/story/202310/tripura-bangladeshi-siblings-held-in-mohanpur-while-making-aadhaar-card-025908124-16x9.jpg?VersionId=Lbt3cCYr.8ybKGemdiEHOLjFvcNta9yo&size=690:388",
        sharedViewmodel = sharedViewmodel,
        imageBitmap = imageBitmap
    )
}

