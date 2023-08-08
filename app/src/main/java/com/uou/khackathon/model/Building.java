package com.uou.khackathon.model;

// 예시로 만든 Building 데이터 모델
public class Building {
    private String name; // 건물 이름
    private String roomCount; // 원룸/투룸/쓰리룸
    private String livePeriod; // 몇개월
    private String deposit; // 보증금
    private String monthlyRent; // 월세
    private String managementAmount; // 관리금
    private int floorSpace; // 평 수
    private String address; // 주소

    public Building(String name, String roomCount, String livePeriod, String deposit, String monthlyRent, String managementAmount, int floorSpace, String address) {
        this.name = name;
        this.roomCount = roomCount;
        this.livePeriod = livePeriod;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.managementAmount = managementAmount;
        this.floorSpace = floorSpace;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(String roomCount) {
        this.roomCount = roomCount;
    }

    public String getLivePeriod() {
        return livePeriod;
    }

    public void setLivePeriod(String livePeriod) {
        this.livePeriod = livePeriod;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(String monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getManagementAmount() {
        return managementAmount;
    }

    public void setManagementAmount(String managementAmount) {
        this.managementAmount = managementAmount;
    }

    public int getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(int floorSpace) {
        this.floorSpace = floorSpace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
