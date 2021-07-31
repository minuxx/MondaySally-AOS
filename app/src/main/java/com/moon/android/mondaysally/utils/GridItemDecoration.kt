package com.moon.android.mondaysally.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class GridItemDecoration(context: Context) : ItemDecoration() {
    private val size15: Int
    private val size10: Int
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        //상하 설정
        if (position == 0 || position == 1 || position == 2) {
            // 첫번 째 줄 아이템
        } else {
            outRect.top = size15
        }
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        outRect.right = size10
    }

    // dp -> pixel 단위로 변경
    private fun dpToPx(context: Context, dp: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()

    init {
        size15 = dpToPx(context, 15)
        size10 = dpToPx(context, 10)
    }
}