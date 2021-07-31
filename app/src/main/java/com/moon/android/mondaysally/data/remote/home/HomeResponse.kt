package com.moon.android.mondaysally.data.remote.home

import com.google.gson.annotations.SerializedName
import com.moon.android.mondaysally.data.entities.Home

data class HomeResponse(
    @SerializedName(value = "isSuccess") val isSuccess : Boolean,
    @SerializedName(value = "code") val code : Int,
    @SerializedName(value = "message") val message : String,
    @SerializedName(value = "result") val result : Home?
)
