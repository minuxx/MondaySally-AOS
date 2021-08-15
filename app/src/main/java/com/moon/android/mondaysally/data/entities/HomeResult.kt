package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class HomeResult(
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "companyIdx") val companyIdx: Int,
    @SerializedName(value = "logoImgUrl") val logoImgUrl: String = "",
    @SerializedName(value = "totalWorkTime") val totalWorkTime: String = "",
    @SerializedName(value = "status") val status: String = "",
    @SerializedName(value = "accumulatedClover") val accumulatedClover: Int,
    @SerializedName(value = "currentClover") val currentClover: Int,
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "giftHistory") val giftHistory: ArrayList<GiftHistory>,
    @SerializedName(value = "twinkleRank") val twinkleRank: ArrayList<TwinkleRank>,
    @SerializedName(value = "workingMemberlist") val workingMemberlist: ArrayList<Member>,
)

data class Member(
    @SerializedName(value = "status") val status: String = "",
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "department") val department: String = "",
    @SerializedName(value = "position") val position: String = "",
)
