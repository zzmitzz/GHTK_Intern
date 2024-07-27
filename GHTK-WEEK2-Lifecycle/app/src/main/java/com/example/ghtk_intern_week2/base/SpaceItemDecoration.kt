package com.example.ghtk_intern_week2.base

import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: android.view.View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = space
        outRect.top = space
        outRect.left = space
        outRect.right = space
    }
}