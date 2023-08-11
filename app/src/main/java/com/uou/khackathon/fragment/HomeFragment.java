package com.uou.khackathon.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uou.khackathon.R;
import com.uou.khackathon.activity.MainActivity;
import com.uou.khackathon.adapter.BuildingAdapter;
import com.uou.khackathon.model.Building;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    private List<Building> buildings; // 임의로 Building 데이터 타입을 가정
    private List<ConstraintLayout> allCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        initializeViewPager();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCategorySelection();
    }

    private void initViews(View view) {
        viewPager2 = view.findViewById(R.id.view_pager2_cardview);
        allCategories = Arrays.asList(
                view.findViewById(R.id.category_1),
                view.findViewById(R.id.category_2),
                view.findViewById(R.id.category_3),
                view.findViewById(R.id.category_4),
                view.findViewById(R.id.category_5)
        );
        allCategories.get(0).setSelected(true);
        initBuildingData();
    }

    private void initializeViewPager() {
        int pageMargin = getResources().getDimensionPixelOffset(R.dimen.page_margin);
        int pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setPadding(pageOffset, 0, pageOffset, 0);
        viewPager2.setPageTransformer((page, position) ->
                page.setTranslationX(-pageMargin * position));

        if (getActivity() instanceof MainActivity) {
            viewPager2.setAdapter(new BuildingAdapter(buildings, getActivity()));
        }
    }

    private void setupCategorySelection() {
        for (ConstraintLayout category : allCategories) {
            category.setOnClickListener(v -> {
                v.setSelected(true);
                for (ConstraintLayout otherCategory : allCategories) {
                    if (otherCategory != v) {
                        otherCategory.setSelected(false);
                    }
                }
            });
        }
    }

    private void initBuildingData() {
        buildings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            buildings.add(new Building(
                    "라임 빌라 ", "원룸", "3개월", "100만", "40만", "10만원", 13,
                    "울산 남구 무거동 대학로 23번길 12"));
        }
    }
}