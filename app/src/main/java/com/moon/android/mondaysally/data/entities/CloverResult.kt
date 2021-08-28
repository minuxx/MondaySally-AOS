package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class CloverResult(
    @SerializedName(value = "ranks") val ranks: ArrayList<TwinkleRanking>?,
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "currentClover") val currentClover: Int,
    @SerializedName(value = "accumulatedClover") val accumulatedClover: Int,

    @SerializedName(value = "gifts") val gifts: ArrayList<Gift>?,
    @SerializedName(value = "clovers") val clovers: ArrayList<CloverHistory>?,
)

data class TwinkleRanking(
    @SerializedName(value = "ranking") val ranking: Int,
    @SerializedName(value = "imgUrl") val imgUrl: String,
    @SerializedName(value = "nickname") val nickname: String,
    @SerializedName(value = "currentClover") val currentClover: Int,
    @SerializedName(value = "accumulatedClover") val accumulatedClover: Int,
    @SerializedName(value = "totalworktime") val totalworktime: String,
)

data class CloverHistory(
    @SerializedName(value = "idx") val idx: Int,
    @SerializedName(value = "time") val time: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "worktime") val worktime: Int,
    @SerializedName(value = "clover") val clover: Int,

)