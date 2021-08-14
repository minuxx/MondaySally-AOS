package com.moon.android.mondaysally.data.remote.twinkke

import com.google.gson.annotations.SerializedName
import com.moon.android.mondaysally.data.entities.TwinkleResult

data class TwinkleResponse(
    @SerializedName(value = "isSuccess") val isSuccess: Boolean,
    @SerializedName(value = "code") val code: Int,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "result") val result: TwinkleResult?
)
