package com.uou.khackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uou.khackathon.R;
import com.uou.khackathon.model.Building;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {
    private List<Building> buildingList;
    private Context mContext;

    public BuildingAdapter(List<Building> buildingList,Context context) {
        this.buildingList = buildingList;
        this.mContext = context;
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
        if (building != null) {
            bindData(holder, building);
        }
    }

    private void bindData(BuildingViewHolder holder, Building building) {
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
        return (buildingList != null) ? buildingList.size() : 0;
    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder {
        TextView buildingName, buildingRoomCount, buildingLivePeriod;
        TextView buildingDepositPrice, buildingMonthlyRentPrice, buildingManagementPrice, buildingFloorSpaceNum;
        TextView buildingAddr;

        public BuildingViewHolder(@NonNull View itemView) {
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
