package com.moon.android.mondaysally.data.entities

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class CloverResult(
    @SerializedName(value = "ranks") val ranks: ArrayList<TwinkleRanking>?,
)

data class TwinkleRanking(
    @SerializedName(value = "ranking") val ranking: Int,
    @SerializedName(value = "imgUrl") val imgUrl: String,
    @SerializedName(value = "nickname") val nickname: String,
    @SerializedName(value = "currentClover") val currentClover: Int,
    @SerializedName(value = "accumulatedClover") val accumulatedClover: Int,
    @SerializedName(value = "totalworktime") val totalworktime: String,
)
