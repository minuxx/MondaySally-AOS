package com.moon.android.mondaysally.ui.main.shop.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moon.android.mondaysally.data.entities.Gift
import com.moon.android.mondaysally.data.remote.shop.GiftService
import java.io.IOException

class GiftShopPagingSource(
    private val service: GiftService
) : PagingSource<Int, Gift>() {
    // 데이터 로드
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gift> {
        return try {
            val nextPage = params.key ?: 1
            val response = service.getGiftList(nextPage).body()
            if (response!!.result != null) {
                val data = response.result?.gifts!!
                LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = if (data.isEmpty()) null else nextPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = nextPage - 1,
                    nextKey = null
                )
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            LoadResult.Error(e)
        }
    }

    // 데이터가 새로고침되거나 첫 로드 후 무효화되었을 때 키를 반환하여 load()로 전달
    override fun getRefreshKey(state: PagingState<Int, Gift>): Int? {
        return 1
    }

}