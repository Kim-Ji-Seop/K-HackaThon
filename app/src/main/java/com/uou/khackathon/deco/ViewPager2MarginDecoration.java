package com.uou.khackathon.deco;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ViewPager2MarginDecoration extends RecyclerView.ItemDecoration {
    private final int margin;

    public ViewPager2MarginDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = margin;
        outRect.left = margin;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = margin;
        }
        if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
            outRect.right = margin;
        }
    }
}
