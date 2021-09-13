package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class Code(
    @SerializedName(value = "code") val code: String = "",
)

data class AuthResult(
    @SerializedName(value = "jwt") val jwtToken: String?,
    @SerializedName(value = "fcmToken") val fcmToken: String?,
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "profileImg") val profileImg: String = "",
    @SerializedName(value = "isNotice") val isNotice: Int = 0,
    @SerializedName(value = "androidVersion") val androidVersion: String = "",
    @SerializedName(value = "version") val version: String = "",
    @SerializedName(value = "isAccessable") val isAccessable: Boolean = true,
    @SerializedName(value = "authenticNum") val authenticNum: Int = 0,

    //마이페이지
    @SerializedName(value = "email") val email: String = "",
    @SerializedName(value = "imgUrl") val imgUrl: String = "",
    @SerializedName(value = "department") val department: String = "",
    @SerializedName(value = "position") val position: String,
    @SerializedName(value = "gender") val gender: String,
    @SerializedName(value = "bankAccount") val bankAccount: String,
    @SerializedName(value = "phoneNumber") val phoneNumber: String,
    @SerializedName(value = "workingYear") val workingYear: Int = 0,
    @SerializedName(value = "companyName") val companyName: String,
)

data class ProfileBody(
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "imgUrl") val imgUrl: String = "",
    @SerializedName(value = "phoneNumber") val phoneNumber: String = "",
    @SerializedName(value = "bankAccount") val bankAccount: String = "",
    @SerializedName(value = "email") val email: String = "",
)

