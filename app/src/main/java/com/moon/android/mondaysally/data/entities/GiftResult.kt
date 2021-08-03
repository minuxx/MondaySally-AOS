package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class GiftResult(
    @SerializedName(value = "gifts") val gifts: ArrayList<Gift>,
    @SerializedName(value = "totalCount") val totalCount: Int,

)
