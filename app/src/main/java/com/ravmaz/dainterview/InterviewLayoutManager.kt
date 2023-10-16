package com.ravmaz.dainterview

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil
import kotlin.math.max

class InterviewLayoutManager(private val rows: Int, private val cols: Int, private val rtl: Boolean = false) : RecyclerView.LayoutManager() {

    private var totalWidth = 0
    var horizontalOffset = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val totalItems = itemCount
        val itemsOnPage = rows * cols

        // Determines the total width required for all items.
        // Calculate the number of pages with round up - ensuring even partially filled pages are counted.
        val pagesRoundUp = ceil(totalItems.toDouble() / itemsOnPage).toInt()
        // Multiply the number of pages by the width of a single page.
        // Ensure this total width is at least as wide as a single page.
        totalWidth = max(width * pagesRoundUp, width)

        var curRow: Int
        var curCol: Int

        for (index in 0 until totalItems) {
            val page = index / itemsOnPage

            val view = recycler.getViewForPosition(index)
            addView(view)

            measureChildWithMargins(view, 0, 0)

            val itemWidth = width / cols
            val itemHeight = height / rows

            val absolutePositionOnPage = index % itemsOnPage
            curRow = absolutePositionOnPage / cols
            curCol = absolutePositionOnPage % cols

            val left: Int
            val right: Int

            if (rtl) {
                val startPositionForColumn = width - curCol * itemWidth
                // Page offset
                right = startPositionForColumn - page * width
                left = right - itemWidth
            } else {
                val startPositionForColumn = curCol * itemWidth
                // Page offset
                left = startPositionForColumn + page * width
                right = left + itemWidth
            }

            val top = curRow * itemHeight
            val bottom = top + itemHeight

            layoutDecoratedWithMargins(view, left, top, right, bottom)
        }


        // Set the initial scroll position to the end for RTL layouts.
        if (rtl && horizontalOffset == 0) {
            horizontalOffset = (totalWidth - width).coerceAtLeast(0)
        }
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val delta = determineAmountToScroll(dx)
        // Negating delta to move the children in the opposite direction of the user scroll gesture.
        offsetChildrenHorizontal(-delta)
        horizontalOffset += delta
        return delta
    }

    private fun determineAmountToScroll(dx: Int): Int {
        val newOffset = horizontalOffset + dx

        // The maximum allowable horizontal offset
        val boundaryValue = totalWidth - width

        // Determines the valid scroll amount to prevent scrolling beyond content boundaries.
        return if (rtl) {
            when {
                newOffset > boundaryValue -> boundaryValue - horizontalOffset
                newOffset < 0 -> -horizontalOffset
                else -> dx
            }
        } else {
            when {
                newOffset < 0 -> -horizontalOffset
                newOffset > boundaryValue -> boundaryValue - horizontalOffset
                else -> dx
            }
        }
    }
}
