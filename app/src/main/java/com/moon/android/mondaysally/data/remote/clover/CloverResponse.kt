package com.moon.android.mondaysally.data.remote.auth

import com.google.gson.annotations.SerializedName
import com.moon.android.mondaysally.data.entities.CloverResult

data class CloverResponse(
    @SerializedName(value = "isSuccess") val isSuccess : Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : CloverResult?
)
