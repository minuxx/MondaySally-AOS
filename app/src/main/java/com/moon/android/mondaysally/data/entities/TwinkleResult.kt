package com.moon.android.mondaysally.data.entities

import com.google.gson.annotations.SerializedName

data class MyTwinkleResult(
    @SerializedName(value = "giftLogs") val giftLogs: ArrayList<MyTwinkle>,
)

data class MyTwinkle(
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "isProved") val isProved: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "imgUrl") val imgUrl: String,
    @SerializedName(value = "idx") val idx: Int,
)

data class Twinkle(
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "money") val money: Int,

//    "idx": 2, // 트윙클 아이디
//    "imgUrl": "https://firebasestorage.googleapis.com/v0/b/modaysally.appspot.com/o/test%2Fprofile%2F8510FE91-1352-4562-B52A-293BA7DD1AD9?alt=media&token=3fa626c9-29c3-4ca1-ae90-3308d1a0a596",
//"nickname": "룰루",
//"name": "가족에게 쏜다",
//"usedClover": 20,
//"twinkleImg": "https://firebasestorage.googleapis.com/v0/b/modaysally.appspot.com/o/test%2Fprofile%2F1C6F67[…]?alt=media&token=0721671e-e344-488c-a526-5f0450368db3",
//"isHearted": "Y",  // 현재 사용자가 좋아요를 눌렀는지 N/Y
//"date": "2021.07.18",
//"content": "그동안 열심히 한 보람이 있네요! 드디어 쌓아왔던 포인트로 가족들에게 쐈습니다 ㅎㅎ 덕분에 가족들에게 좋은 소리들었네요! 다들 포인트 잘 활용해보세요~!",
//"likenum": 2,
//"commentnum": 3
)
//
//data class GiftPostBody(
//    @SerializedName(value = "giftIdx") val giftIdx: Int,
//    @SerializedName(value = "usedClover") val usedClover: Int,
//)
