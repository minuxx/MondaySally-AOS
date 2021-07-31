package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class TwinkleRank(
    @SerializedName(value = "ranking") val ranking: Int,
    @SerializedName(value = "imgUrl") val imgUrl: String = "",
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "currentClover") val currentClover: Int,
)
