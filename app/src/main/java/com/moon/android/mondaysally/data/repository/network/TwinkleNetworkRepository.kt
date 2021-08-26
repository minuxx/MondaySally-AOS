package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.entities.CommentPostBody
import com.moon.android.mondaysally.data.entities.TwinklePostBody
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleResponse
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class TwinkleNetworkRepository(val twinkleService: TwinkleService) :
    BaseNetworkRepository() {

    suspend fun getMyTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { twinkleService.getMyTwinkleList(page) }
    }

    suspend fun getTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { twinkleService.getTwinkleList(page) }
    }

    suspend fun getTwinkleDetail(index: Int): TwinkleResponse {
        return apiRequest { twinkleService.getTwinkleDetail(index) }
    }

    suspend fun postComment(index: Int, commentPostBody: CommentPostBody): TwinkleResponse {
        return apiRequest { twinkleService.postComment(index, commentPostBody) }
    }

    suspend fun postTwinkle(twinklePostBody: TwinklePostBody): TwinkleResponse {
        return apiRequest { twinkleService.postTwinkle(twinklePostBody) }
    }

    suspend fun postLike(index: Int): TwinkleResponse {
        return apiRequest { twinkleService.postLike(index) }
    }
}
