package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class Code(
    @SerializedName(value = "code") val code: String = "",
)

data class Auth(
    @SerializedName(value = "jwt") val jwtToken: String?,
    @SerializedName(value = "fcmToken") val fcmToken: String?,
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "profileImg") val profileImg: String = "",
    @SerializedName(value = "isNotice") val isNotice: Int = 0,
    @SerializedName(value = "androidVersion") val androidVersion: String = "",
    @SerializedName(value = "version") val version: String = "",
    @SerializedName(value = "isAccessable") val isAccessable: Boolean = true,
    @SerializedName(value = "authenticNum") val authenticNum: Int = 0
)

