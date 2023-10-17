package com.ravmaz.dainterview
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class InterviewSnapHelper : SnapHelper() {

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView?.layoutManager !is InterviewLayoutManager) {
            throw IllegalArgumentException("InterviewSnapHelper can only be used with InterviewLayoutManager")
        }
        super.attachToRecyclerView(recyclerView)
    }

    override fun findSnapView(lm: RecyclerView.LayoutManager): View? {
        val layoutManager = lm as InterviewLayoutManager
        return if (layoutManager.rtl) {
            lm.getChildAt(lm.childCount - 1)
        } else {
            lm.getChildAt(0)
        }
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        // Ignore velocity for now
        return 0
    }

    override fun calculateDistanceToFinalSnap(lm: RecyclerView.LayoutManager, targetView: View): IntArray {
        val layoutManager = lm as InterviewLayoutManager
        val out = IntArray(2)

        // Calculate the current page that's majorly visible
        val currentPage = layoutManager.horizontalOffset / layoutManager.width

        // Calculate the projected page after scroll
        val projectedPage = if (layoutManager.horizontalOffset % layoutManager.width > layoutManager.width / 2) {
            currentPage + 1
        } else {
            currentPage
        }

        // Calculate the offset to snap to the projected page
        out[0] = projectedPage * layoutManager.width - layoutManager.horizontalOffset

        return out
    }
}
