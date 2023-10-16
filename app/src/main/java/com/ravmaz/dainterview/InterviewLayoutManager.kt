package com.ravmaz.dainterview

import androidx.recyclerview.widget.RecyclerView

class InterviewLayoutManager(private val rows: Int, private val cols: Int, private val rtl: Boolean = false) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val totalItems = itemCount
        var curRow = 0
        var curCol = 0

        for (index in 0 until totalItems) {
            val i = if (rtl) totalItems - index - 1 else index

            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChildWithMargins(view, 0, 0)

            val width = width / cols
            val height = height / rows

            val left = curCol * width
            val top = curRow * height
            val right = left + width
            val bottom = top + height

            layoutDecorated(view, left, top, right, bottom)

            curCol++
            if (curCol == cols) {
                curCol = 0
                curRow++
            }
        }
    }
}
