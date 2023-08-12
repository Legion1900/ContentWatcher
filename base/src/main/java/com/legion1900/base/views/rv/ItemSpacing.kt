package com.legion1900.base.views.rv

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.legion1900.base.R

/**
 * @property margin used to specify outmost spacing between child view and its recycler view.
 * While [itemSpacing] is used for spacing between items, this is used as a spacing
 * mechanism for elements which have a common edge with their RecyclerView.
 *
 * @property selector used for selecting items which must be affected by this decoration. Set __null__
 * so that decoration would affect any items in recycler.
 */
class ItemSpacing(
    private val spanCount: Int,
    private val itemSpacing: Spacing,
    private val margin: Margin?,
    private val selector: Selector?
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val shouldAffectThisItem = selector?.shouldAffectItem(position) ?: true
        if (shouldAffectThisItem) {
            outRect.left = getLeftOffset(position)
            outRect.top = getTopOffset(position)
            outRect.right = getRightOffset(position)
            outRect.bottom = getBotOffset(position, parent)
        }
    }

    @Dimension
    private fun getLeftOffset(position: Int): Int {
        val column = position % spanCount
        return if (column == 0) {
            margin?.left ?: 0
        } else {
            itemSpacing.horizontal
        }
    }

    @Dimension
    private fun getTopOffset(position: Int): Int {
        return if (position < spanCount) {
            margin?.top ?: 0
        } else {
            itemSpacing.vertical
        }
    }

    @Dimension
    private fun getRightOffset(position: Int): Int {
        val column = position % spanCount
        return if (column == spanCount - 1) margin?.right ?: 0 else 0
    }

    @Dimension
    private fun getBotOffset(
        position: Int,
        recyclerView: RecyclerView
    ): Int {
        val itemCount = recyclerView.adapter?.itemCount ?: 0
        // How many items are in last row.
        val lastRowItemCount = itemCount % spanCount
        val offset = if (lastRowItemCount != 0) lastRowItemCount else spanCount
        // Index of first item in the last row.
        val firstItemInRow = itemCount - offset
        return if (position >= firstItemInRow) margin?.bot ?: 0 else 0
    }

    data class Spacing(
        @Dimension val horizontal: Int,
        @Dimension val vertical: Int
    )

    data class Margin(
        @Dimension val left: Int,
        @Dimension val top: Int,
        @Dimension val right: Int,
        @Dimension val bot: Int
    )

    fun interface Selector {

        fun shouldAffectItem(position: Int): Boolean
    }

    class BuilderParams(
        var spanCount: Int = 1,
        @DimenRes var verticalItemSpacing: Int = R.dimen.zero,
        @DimenRes var horizontalItemSpacing: Int = R.dimen.zero,
        @DimenRes var leftMargin: Int = R.dimen.zero,
        @DimenRes var topMargin: Int = R.dimen.zero,
        @DimenRes var rightMargin: Int = R.dimen.zero,
        @DimenRes var botMargin: Int = R.dimen.zero,
        var selector: Selector? = null
    )

    companion object {

        fun build(
            context: Context,
            configure: BuilderParams.() -> Unit
        ): ItemSpacing {
            fun dimension(id: Int) = context.resources.getDimensionPixelOffset(id)
            val params = BuilderParams()
            params.configure()
            val spacing = Spacing(
                horizontal = dimension(params.horizontalItemSpacing), vertical = dimension(params.verticalItemSpacing)
            )
            val margin = Margin(
                left = dimension(params.leftMargin),
                top = dimension(params.topMargin),
                right = dimension(params.rightMargin),
                bot = dimension(params.botMargin)
            )

            return ItemSpacing(
                params.spanCount, spacing, margin, params.selector
            )
        }
    }
}
