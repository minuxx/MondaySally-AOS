package com.moon.android.mondaysally.utils

import android.animation.TimeInterpolator

class GlobalConstant {
    companion object{
        //common
        const val FIREBASE_STORAGE_URL = "gs://example.com/"
        const val DEBUG_TAG = "DEBUG://"
        const val FLAG_NETWORK_ERROR = 404
        const val NOTIFICATION_ID = 1

        const val TAG = "MOBILE_TEMPLATE_APP"

        //SharedPref Key
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
        const val NICKNAME = "NICKNAME"
        const val USER = "USER"
        const val FIRST_LAUNCH = "FIRST-LAUNCH"
        const val NO_MORE_TUTORIAL = "NO-MORE-TUTORIAL"
        const val NOTIFICATION_TWINKLE_IDX = "NOTIFICATION_TWINKLE_IDX"
        const val NOTIFICATION_PERMISSION = "NOTIFICATION_PERMISSION"
        const val NOTIFICATION_CATEGORY = "NOTIFICATION_CATEGORY"
        const val WORK_STATUS = "WORK_STATUS"

        //Animation
        const val FREQ = 1.5f
        const val DECAY = 1f
        val decayingSineWave = TimeInterpolator { input ->
            val raw = Math.sin(FREQ * input * 2 * Math.PI)
            (raw * Math.exp((-input * DECAY).toDouble())).toFloat()
        }

        //FirebaseStorage Folder
        const val TWINKLE_FOLDER = "test/twinkle"
//        const val TWINKLE_FOLDER = "prod/twinkle"
        const val RECEIPT_FOLDER = "test/receipt"
//        const val TWINKLE_FOLDER = "prod/receipt"
        const val PROFILE_FOLDER = "test/profile"
//        const val TWINKLE_FOLDER = "prod/receipt"

        const val TWINKLE_IMAGE_MODE = 1
        const val RECEIPT_IMAGE_MODE = 2

        const val VALIDATE_TWINKLE_PHOTO = 1
        const val VALIDATE_RECEIPT_PHOTO = 2
        const val VALIDATE_TWINKLE_CONTENT = 3

        const val WORK_STATUS_ON = "ON"
        const val WORK_STATUS_OFF = "OFF"
        const val EDIT_MODE = "EDIT"
    }

}