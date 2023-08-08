package com.uou.khackathon.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uou.khackathon.R;
import com.uou.khackathon.deco.ViewPager2MarginDecoration;
import com.uou.khackathon.model.Building;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    private List<Building> buildings; // 임의로 Building 데이터 타입을 가정
    private ConstraintLayout category1;
    private ConstraintLayout category2;
    private ConstraintLayout category3;
    private ConstraintLayout category4;
    private ConstraintLayout category5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        category1 = view.findViewById(R.id.category_1);
        category2 = view.findViewById(R.id.category_2);
        category3 = view.findViewById(R.id.category_3);
        category4 = view.findViewById(R.id.category_4);
        category5 = view.findViewById(R.id.category_5);



        category1.setSelected(true);
        // 데이터 초기화
        initBuildingData();
        int pageMargin = getResources().getDimensionPixelOffset(R.dimen.page_margin);
        int pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
        // ViewPager2 초기화
        viewPager2 = view.findViewById(R.id.view_pager2_cardview);
        viewPager2.setOffscreenPageLimit(3);  // 한 번에 3개의 아이템을 로드
        viewPager2.setPadding(pageOffset, 0, pageOffset, 0);

        viewPager2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                // Margin 변환
                float marginTransformValue = -pageMargin * position;
                page.setTranslationX(marginTransformValue);

                // Scale 변환
                //float scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f;
                //page.setScaleY(scaleFactor);
            }
        });
        viewPager2.setAdapter(new BuildingAdapter(buildings));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 카테고리 선택
        List<ConstraintLayout> allCategories = Arrays.asList(category1, category2, category3, category4, category5);
        // 각 항목에 클릭 리스너 설정
        for (ConstraintLayout category : allCategories) {
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭된 항목을 선택 상태로 설정
                    v.setSelected(true);

                    // 다른 모든 항목의 선택 상태 초기화
                    for (ConstraintLayout otherCategory : allCategories) {
                        if (otherCategory != v) {
                            otherCategory.setSelected(false);
                        }
                    }
                }
            });
        }
    }

    private void initBuildingData() {
        buildings = new ArrayList<>();
        // 예시 데이터
        for (int i = 0; i < 10; i++) {
            buildings.add(new Building(
                    "라임 빌라 ",
                    "원룸",
                    "3개월",
                    "100만",
                    "40만",
                    "10만원",
                    13,
                    "울산 남구 무거동 대학로 23번길 12"));
        }
    }

    // Adapter
    private class BuildingAdapter extends RecyclerView.Adapter<BuildingViewHolder> {
        private List<Building> buildingList;

        BuildingAdapter(List<Building> buildingList) {
            this.buildingList = buildingList;
        }

        @NonNull
        @Override
        public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_viewpager2_item, parent, false);
            return new BuildingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BuildingViewHolder holder, int position) {
            Building building = buildingList.get(position);
            // 데이터 바인딩
            holder.buildingName.setText(building.getName());
            holder.buildingRoomCount.setText(building.getRoomCount());
            holder.buildingLivePeriod.setText(building.getLivePeriod());
            holder.buildingDepositPrice.setText(building.getDeposit());
            holder.buildingMonthlyRentPrice.setText(building.getMonthlyRent());
            holder.buildingManagementPrice.setText(building.getManagementAmount());
            holder.buildingFloorSpaceNum.setText(String.valueOf(building.getFloorSpace()));
            holder.buildingAddr.setText(building.getAddress());

        }

        @Override
        public int getItemCount() {
            return buildingList.size();
        }
    }

    // ViewHolder
    private class BuildingViewHolder extends RecyclerView.ViewHolder {
        TextView buildingName,buildingRoomCount,buildingLivePeriod;
        TextView buildingDepositPrice, buildingMonthlyRentPrice, buildingManagementPrice, buildingFloorSpaceNum;
        TextView buildingAddr;

        BuildingViewHolder(@NonNull View itemView) {
            super(itemView);
            buildingName = itemView.findViewById(R.id.building_name);
            buildingRoomCount = itemView.findViewById(R.id.building_room_count);
            buildingLivePeriod = itemView.findViewById(R.id.live_period);
            buildingDepositPrice = itemView.findViewById(R.id.deposit_price);
            buildingMonthlyRentPrice = itemView.findViewById(R.id.monthly_rent_price);
            buildingManagementPrice = itemView.findViewById(R.id.management_amount_price);
            buildingFloorSpaceNum = itemView.findViewById(R.id.floor_space_number);
            buildingAddr = itemView.findViewById(R.id.building_addr);

        }
    }
}