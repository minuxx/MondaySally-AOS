package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class Gift(
    @SerializedName(value = "idx") val idx: Int,
    @SerializedName(value = "imgUrl") val imgUrl: String = "",
    @SerializedName(value = "name") val name: String = "",
)
