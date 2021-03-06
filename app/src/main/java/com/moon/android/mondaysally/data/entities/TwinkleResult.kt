package com.moon.android.mondaysally.data.entities

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TwinkleResult (
    @SerializedName(value = "giftLogs") val giftLogs: ArrayList<MyTwinkle>?,
    @SerializedName(value = "twinkles") val twinkles: ArrayList<Twinkle>?,

    //TwinkleDetail
    @SerializedName(value = "isWriter") val isWriter: String,
    @SerializedName(value = "writerName") val writerName: String,
    @SerializedName(value = "twinkleCreatedAt") val twinkleCreatedAt: String,
    @SerializedName(value = "twinkleImglists") val twinkleImglists: ArrayList<String>,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "giftName") val giftName: String,
    @SerializedName(value = "option") val option: Int,
    @SerializedName(value = "isAccepted") val isAccepted: String,
    @SerializedName(value = "receiptImgUrl") val receiptImgUrl: String,
    @SerializedName(value = "likeNum") var likenum: Int,
    @SerializedName(value = "isHearted") var isHearted: String,
    @SerializedName(value = "commentNum") val commentNum: Int,
    @SerializedName(value = "commentLists") val commentLists: ArrayList<TwinkleComment>,
    @SerializedName(value = "isPrivated") val isPrivated: String,
) : Serializable

data class MyTwinkle(
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "isProved") val isProved: String,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "imgUrl") val imgUrl: String,
    @SerializedName(value = "idx") val idx: Int,
): Serializable

data class Twinkle(
    @SerializedName(value = "usedClover") val usedClover: Int,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "nickname") val nickname: String,
    @SerializedName(value = "imgUrl") val imgUrl: String,
    @SerializedName(value = "idx") val idx: Int,
    @SerializedName(value = "twinkleImg") val twinkleImg: String,
    @SerializedName(value = "isHearted") var isHearted: String,
    @SerializedName(value = "date") val date: String,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "likenum") var likenum: Int,
    @SerializedName(value = "commentnum") val commentnum: Int,

//    "idx": 2, // ????????? ?????????
//    "imgUrl": "https://firebasestorage.googleapis.com/v0/b/modaysally.appspot.com/o/test%2Fprofile%2F8510FE91-1352-4562-B52A-293BA7DD1AD9?alt=media&token=3fa626c9-29c3-4ca1-ae90-3308d1a0a596",
//"nickname": "??????",
//"name": "???????????? ??????",
//"usedClover": 20,
//"twinkleImg": "https://firebasestorage.googleapis.com/v0/b/modaysally.appspot.com/o/test%2Fprofile%2F1C6F67[???]?alt=media&token=0721671e-e344-488c-a526-5f0450368db3",
//"isHearted": "Y",  // ?????? ???????????? ???????????? ???????????? N/Y
//"date": "2021.07.18",
//"content": "????????? ????????? ??? ????????? ?????????! ????????? ???????????? ???????????? ??????????????? ???????????? ?????? ????????? ??????????????? ?????? ??????????????????! ?????? ????????? ??? ??????????????????~!",
//"likenum": 2,
//"commentnum": 3
): Serializable

data class TwinkleRank(
    @SerializedName(value = "ranking") val ranking: Int,
    @SerializedName(value = "imgUrl") val imgUrl: String = "",
    @SerializedName(value = "nickname") val nickname: String = "",
    @SerializedName(value = "currentClover") val currentClover: Int,
)

data class TwinkleComment(
    @SerializedName(value = "idx") val idx: Int,
    @SerializedName(value = "commentWriterName") val commentWriterName: String = "",
    @SerializedName(value = "commentWriterImg") val commentWriterImg: String = "",
    @SerializedName(value = "commentContent") val commentContent: String,
    @SerializedName(value = "commentCreatedAt") val commentCreatedAt: String,
    @SerializedName(value = "isCommentWrited") val isCommentWrited: String,
): Serializable

data class CommentPostBody(
    @SerializedName(value = "content") val content: String,
)

data class UploadDonePhoto(
    val uri: Uri,
    val index: Int,
)

data class TwinklePostBody  (
    @SerializedName(value = "giftLogIdx") val giftLogIdx: Int,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "receiptImgUrl") val receiptImgUrl: String,
    @SerializedName(value = "twinkleImgList") val twinkleImgList: ArrayList<String>,
)

data class TwinkleImageUpload  (
    var uploaded: Boolean,
    var firebaseUploaded: Boolean,
)


data class TwinklePatchBody  (
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "receiptImgUrl") val receiptImgUrl: String,
    @SerializedName(value = "updateTwinkleImgList") val twinkleImgList: ArrayList<String>,
)