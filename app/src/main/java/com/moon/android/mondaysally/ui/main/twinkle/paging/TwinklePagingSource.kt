package com.moon.android.mondaysally.ui.main.twinkle.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.moon.android.mondaysally.data.entities.Twinkle
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService
import java.io.IOException

class TwinklePagingSource(
    private val service: TwinkleService
) : PagingSource<Int, Twinkle>() {
    // 데이터 로드
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Twinkle> {
        // LoadParams : 로드할 키와 항목 수 , LoadResult : 로드 작업의 결과
        return try {
            // 키 값이 없을 경우 기본값을 사용함
            val page = params.key ?: 1

            // 데이터를 제공하는 인스턴스의 메소드 사용
            val response = service.getMyTwinkleList(
                page = page
            )
            val twinkles = response.body()?.result?.twinkles

            /* 로드에 성공 시 LoadResult.Page 반환
            data : 전송되는 데이터
            prevKey : 이전 값 (위 스크롤 방향)
            nextKey : 다음 값 (아래 스크롤 방향)
             */
            LoadResult.Page(
                data = twinkles!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (twinkles.size == 20) page + 1 else null
            )

            // 로드에 실패 시 LoadResult.Error 반환
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    // 데이터가 새로고침되거나 첫 로드 후 무효화되었을 때 키를 반환하여 load()로 전달
    override fun getRefreshKey(state: PagingState<Int, Twinkle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}