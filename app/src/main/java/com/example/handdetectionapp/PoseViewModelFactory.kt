package com.example.handdetectionapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PoseViewModelFactory(
    private val repository: PoseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PoseViewModel::class.java)) {
            return PoseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}