package com.example.handdetectionapp

import android.util.Log
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.lang.Float.min

class PoseRepository {
    private lateinit var poseDetector: PoseDetector
    init {
        val options = PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
        poseDetector = PoseDetection.getClient(options)
    }
    fun detectPose(
        inputImage: InputImage,
        imageProxy: ImageProxy,
        onResult: (Boolean) -> Unit
    ) {
        poseDetector.process(inputImage)
            .addOnSuccessListener { pose ->
                val status = handRaisedCheck(pose)
                onResult(status)
            }
            .addOnFailureListener {
                Log.e("POSE", "Detection failed: ${it.message}")
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    fun handRaisedCheck(pose: Pose): Boolean{
        var handIsUp = false
        val right_Shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val right_Index = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val left_Shoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val left_Index = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)

        if(right_Shoulder != null && right_Index != null && left_Shoulder != null && left_Index != null){
            val highestShoulder = min(right_Shoulder.position.y, left_Shoulder.position.y)
            val highestIndex = min(right_Index.position.y, left_Index.position.y)
            handIsUp = highestIndex < highestShoulder
        }
        return handIsUp
    }
}