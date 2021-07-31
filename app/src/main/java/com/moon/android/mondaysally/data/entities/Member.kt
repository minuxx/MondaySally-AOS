package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName(value = "status") val status: String = "",
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "department") val department: String = "",
    @SerializedName(value = "position") val position: String = "",
)
