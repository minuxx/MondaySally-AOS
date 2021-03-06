package com.moon.android.mondaysally.data.remote.twinkke

import com.moon.android.mondaysally.data.entities.CommentPostBody
import com.moon.android.mondaysally.data.entities.TwinklePatchBody
import com.moon.android.mondaysally.data.entities.TwinklePostBody
import retrofit2.Response
import retrofit2.http.*

interface TwinkleService {
    @GET("/prove")
    suspend fun getMyTwinkleList(@Query("page") page: Int): Response<TwinkleResponse>

    @GET("/twinkle")
    suspend fun getTwinkleList(@Query("page") page: Int): Response<TwinkleResponse>

    @GET("/twinkle/{idx}")
    suspend fun getTwinkleDetail(@Path("idx") idx: Int): Response<TwinkleResponse>

    @POST("/comment/{idx}")
    suspend fun postComment(@Path("idx") idx: Int, @Body commentPostBody: CommentPostBody): Response<TwinkleResponse>

    @PATCH("/comment/out/{idx}")
    suspend fun deleteComment(@Path("idx") idx: Int): Response<TwinkleResponse>

    @POST("/twinkle")
    suspend fun postTwinkle(@Body twinklePostBody: TwinklePostBody): Response<TwinkleResponse>

    @PATCH("/twinkle/{idx}")
    suspend fun patchTwinkle(@Body twinklePatchBody: TwinklePatchBody, @Path("idx") idx: Int): Response<TwinkleResponse>

    @POST("/like/{idx}")
    suspend fun postLike(@Path("idx") idx: Int): Response<TwinkleResponse>
}
