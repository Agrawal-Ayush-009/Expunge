package com.example.expunge.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expunge.R

@Composable
@Preview
fun DocumentRedactionScreen(

) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
//    val viewModel: RedactViewModel = hiltViewmodel()
//    val receivedImage by viewModel.receivedImage.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                imageBitmap = it
            }
        }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
            .background(Color.White)
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        RedactionProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            RedactionText()
            DocumentOptions()
            OrText()
            PhotoOptions{
                launcher.launch()
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
//                        viewModel.refreshRedaction()
                        imageBitmap = null
                    }
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
                    text = "Refresh",
                    color = colorResource(id = R.color.go_back_button_text),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            NextButton(text = "REDACT"){
                imageBitmap?.let {
//                    viewModel.uploadImage(imageBitmap!!, context)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NextButton(text: String, nextOnClick: () -> Unit) {
    Button(
        onClick = {
            nextOnClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(start = 8.dp, end = 8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(8.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(colorResource(id = R.color.button_colour_1).toArgb()),
                        Color(colorResource(id = R.color.button_colour_2).toArgb())
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun TakePhotoOption(text: String, onTakePhotoClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable {
                onTakePhotoClick()
            }
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.background_gray)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Spacer(modifier = Modifier.padding(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.camera),
                contentDescription = null,
                modifier = Modifier.size(92.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(64.dp)
                    .background(colorResource(id = R.color.line_gray))
            )
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.text_gray),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp),
            )
        }
        Spacer(modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun PhotoOptions(onTakePhotoClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TakePhotoOption(
            text = "Take a Photo"
        ) {
            onTakePhotoClick()
        }

    }
}

@Composable
fun RedactionProgressIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIndicator(
            isSelected = true,
            label = "Upload document",
            modifier = Modifier.weight(1f)
        )
        CircleIndicator(
            isSelected = false,
            label = "Select Redaction Level",
            modifier = Modifier.weight(1f)
        )
        CircleIndicator(isSelected = false, label = "Review", modifier = Modifier.weight(1f))
    }
}

@Composable
fun CircleIndicator(isSelected: Boolean, label: String, modifier: Modifier = Modifier) {
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
fun RedactionText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Text(
            text = "Enter Document to be Redacted",
            color = Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun DocumentOptions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        DocumentUploadOption(
            text1 = "Upload Document",
            text2 = "(PDF,.word,.xlsx,.jpeg)"
        )
    }
}

@Composable
fun DocumentUploadOption(text1: String, text2: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .drawBehind {
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 10f), 10f)
                val paint = Paint().apply {
                    color = Color.LightGray
                    style = PaintingStyle.Stroke
                    this.pathEffect = pathEffect
                    strokeWidth = 4.dp.toPx()
                }

                drawRoundRect(
                    color = Color.LightGray,
                    topLeft = Offset.Zero,
                    size = Size(size.width, size.height),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        pathEffect = pathEffect
                    )
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.background_gray)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.upload),
                contentDescription = null,
                modifier = Modifier.size(92.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(80.dp)
                    .background(colorResource(id = R.color.line_gray))
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = text1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.text_gray),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = text2,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.text_gray),
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
fun OrText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(1.dp)
                .weight(0.3f)
                .padding(start = 4.dp)
                .background(Color.LightGray)
        )
        Text(
            text = "OR",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .height(1.dp)
                .weight(0.3f)
                .padding(end = 4.dp)
                .background(Color.LightGray)
        )
    }
}