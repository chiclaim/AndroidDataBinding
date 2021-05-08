package com.mvvm.utils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtils {

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private RecyclerViewUtils() {
    }


    public static void setLinearManagerAndAdapter(RecyclerView recyclerView,
                                                  RecyclerView.Adapter adapter) {
        setLinearManagerAndAdapter(recyclerView, adapter, LinearLayoutManager.VERTICAL);
    }

    public static void setLinearManagerAndAdapter(RecyclerView recyclerView,
                                                  RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(orientation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    public static void setGridManagerAndAdapter(RecyclerView recyclerView,
                                                RecyclerView.Adapter adapter, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), spanCount);
        gridLayoutManager.setOrientation(VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
