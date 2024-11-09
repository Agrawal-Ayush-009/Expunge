package com.example.expunge.screens

import android.graphics.drawable.Drawable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.expunge.R


@Composable
fun FinalRedact(
) {
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
                    DocumentPreviewPreview()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BackButton()
                    NextButton(text = "REDACT NOW") {

                    }

                }
            }
        }
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
        text = "Review Redaction",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
    )
}

@Composable
fun Document(
    imageUrl: String?,
    modifier: Modifier = Modifier
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
        if (bitmap != null) {
            Image(
//                painter = painterResource(id = R.drawable.ayush_id),
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = "Document Preview",
                modifier = Modifier
                    .size(300.dp)
                    .padding(8.dp)
                    .rotate(90f)
            )
        } else {
            Text(text = "No document selected")
        }
    }
}

@Composable
fun BackButton() {
    Button(
        onClick = {},
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
fun DocumentPreviewPreview() {
    Document(imageUrl = "https://akm-img-a-in.tosshub.com/indiatoday/images/story/202310/tripura-bangladeshi-siblings-held-in-mohanpur-while-making-aadhaar-card-025908124-16x9.jpg?VersionId=Lbt3cCYr.8ybKGemdiEHOLjFvcNta9yo&size=690:388")
}

@Composable
@Preview
fun FinalRedactionPreview() {
    FinalRedact()
}
