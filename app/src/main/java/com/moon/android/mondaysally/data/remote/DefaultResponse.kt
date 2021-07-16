package com.moon.android.mondaysally.data.remote

import com.google.gson.annotations.SerializedName
import com.moon.android.mondaysally.data.entities.Auth

data class DefaultResponse(
    @SerializedName(value = "isSuccess") val isSuccess : Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
)
