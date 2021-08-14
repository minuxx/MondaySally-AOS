package com.moon.android.mondaysally.ui.main.twinkle.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService

class TwinklePagingRepository(private val twinkleService: TwinkleService) {
    fun getTwinkleList(userId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            // 사용할 메소드 선언
            pagingSourceFactory = { TwinklePagingSource(twinkleService) }
        ).liveData
}