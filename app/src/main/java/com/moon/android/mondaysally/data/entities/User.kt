package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(value = "id") val id: String = "",
    @SerializedName(value = "password") val pw: String = "",
)
