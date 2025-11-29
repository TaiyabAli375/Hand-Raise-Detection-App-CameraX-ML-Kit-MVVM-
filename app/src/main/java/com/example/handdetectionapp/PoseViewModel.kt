package com.example.handdetectionapp

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
class PoseViewModel(private val repository: PoseRepository): ViewModel() {

    private val _isHandRaise = MutableLiveData<Boolean>()
    val isHandRaise: LiveData<Boolean> = _isHandRaise

    fun processImage(inputImage: InputImage, imageProxy: ImageProxy) {
        repository.detectPose(inputImage, imageProxy) { result ->
            _isHandRaise.postValue(result)
        }
    }
}
