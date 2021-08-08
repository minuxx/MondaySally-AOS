package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class GiftResult(
    @SerializedName(value = "gifts") val gifts: ArrayList<Gift>,
    @SerializedName(value = "totalCount") val totalCount: Int,
    @SerializedName(value = "thumnail") val thumnail: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "info") val info: String,
    @SerializedName(value = "rule") val rule: String,
    @SerializedName(value = "options") val options: ArrayList<GiftOption>,
    @SerializedName(value = "idx") val idx: Int,
)

data class GiftOption(
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "money") val money: Int,
)

data class GiftPostBody(
    @SerializedName(value = "giftIdx") val giftIdx: Int,
    @SerializedName(value = "usedClover") val usedClover: Int,
)
