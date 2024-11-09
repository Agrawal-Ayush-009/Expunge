    package com.example.expunge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DocumentRedactionScreen(
) {
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