package com.example.expunge

import android.net.Uri
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.graphics.Bitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SharedViewmodel: ViewModel() {
    var imageBitmap = mutableStateOf<Bitmap?>(null)

    fun updateImageBitmap(bitmap: Bitmap?){
        imageBitmap.value = bitmap
    }
}