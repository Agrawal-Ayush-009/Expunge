package com.example.expunge

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpungeViewModel @Inject constructor(private val repository: ExpungeRepository): ViewModel() {
    val receivedImage: StateFlow<NetworkResult<Bitmap>> get() = repository.receivedImage

    fun uploadImage(bitmap: Bitmap, context: Context){
        viewModelScope.launch {
            repository.uploadImage(bitmap, context)
        }
    }

    fun refreshRedaction(){
        repository.refreshRedaction()
    }
}