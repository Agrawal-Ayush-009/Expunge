package com.example.expunge

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ExpungeRepository @Inject constructor(private val apiService: API) {
    private val _receivedImage = MutableStateFlow<NetworkResult<Bitmap>>(NetworkResult.Start())
    val receivedImage: StateFlow<NetworkResult<Bitmap>> get() = _receivedImage

    suspend fun uploadImage(bitmap: Bitmap, context: Context) {
//        viewModelScope.launch {
        try {
            _receivedImage.value = NetworkResult.Loading()
            val imagePart = prepareImageFile(bitmap, context)
            val integer = 1 // Example integer
            val levelBody = RequestBody.create(MediaType.get("text/plain"), integer.toString())
            val levelPart = MultipartBody.Part.createFormData("level", levelBody.toString())
            val response = apiService.uploadImage(imagePart, levelPart)

            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                inputStream?.let {
                    val receivedBitmap = BitmapFactory.decodeStream(it)
                    _receivedImage.value = NetworkResult.Success(receivedBitmap)
                    // Do something with the received image
                }
            } else if (response.errorBody() != null) {
//                    Log.d("ERROR", response.errorBody().toString())
//                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _receivedImage.value = NetworkResult.Error("This Document can't be Redacted")
            } else {
                _receivedImage.value = NetworkResult.Error("This Document can't be Redacted")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        }
    }

    fun prepareImageFile(bitmap: Bitmap, context: Context): MultipartBody.Part {
        // Convert Bitmap to File
        val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        val requestFile = RequestBody.create(MediaType.get("image/jpeg"), file)
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    fun refreshRedaction() {
        _receivedImage.value = NetworkResult.Start()
    }
}