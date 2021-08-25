package com.moon.android.mondaysally.data.repository

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import okhttp3.HttpUrl.Companion.toHttpUrl

class FirebaseImageUploadRepository() {

    val storage = Firebase.storage
    var storageRef = storage.reference

    @SuppressLint("SimpleDateFormat")
    fun uploadImage(uri: Uri, folder: String): UploadTask {

        val riversRef = storageRef.child("${folder}/${uri.lastPathSegment}")
        val uploadTask = riversRef.putFile(uri)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            Log.d("네트워크", "addOnSuccessListener")
        }
//        //다운로드 URL 가져오기
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Log.d("네트워크", it.toString())
                    throw it
                }
            }
            riversRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result.toString().toHttpUrl().toString()
//                Log.d("네트워크", downloadUrl)
                onUploadDoneListener?.let { done ->
                    done(downloadUrl)
                }
            } else {
                onUploadDoneListener?.let { done ->
                    done("Fail")
                }
            }
        }
////
////        //업로드 진행률 모니터링
//        uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
//            val progress = (100.0 * bytesTransferred) / totalByteCount
//            Log.d("네트워크", "Upload is $progress% done")
//        }.addOnPausedListener {
//            Log.d("네트워크", "Upload is paused")
//        }
//
        return uploadTask
    }

    private var onUploadDoneListener: ((String) -> Unit)? = null

    fun setonUploadDoneListener(listener: (String) -> Unit) {
        onUploadDoneListener = listener

    }
}
