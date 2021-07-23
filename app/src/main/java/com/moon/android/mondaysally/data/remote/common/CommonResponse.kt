package com.moon.android.mondaysally.data.remote.common

import com.google.gson.annotations.SerializedName
import com.moon.android.mondaysally.data.entities.Auth

data class CommonResponse(
    @SerializedName(value = "isSuccess") val isSuccess : Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val auth : Auth?
)
