package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class GiftHistory(
    @SerializedName(value = "imgUrl") val imgUrl: String = "",
    @SerializedName(value = "isAccepted") val isAccepted: String = "",
    @SerializedName(value = "isProved") val isProved: String = "",
    @SerializedName(value = "name") val name: String = "",
)
