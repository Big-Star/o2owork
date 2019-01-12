package com.otowork.o2o.dto;

import com.otowork.o2o.entity.Shop;
import com.otowork.o2o.enums.ShopSateEnum;

import java.util.List;

public class ShopExecution {
    //
    private int state;
    //
    private String stateInfo;

    private int count;

    private Shop shop;

    private List<Shop> shopList;

    public ShopExecution(){

    }
    //失败结果的构造器
    public ShopExecution(ShopSateEnum shopSateEnum){
        this.state = shopSateEnum.getState();
        this.stateInfo = shopSateEnum.getStateInfo();
    }
    //成功结果的构造器
    public ShopExecution(ShopSateEnum shopSateEnum,Shop shop){
        this.state = shopSateEnum.getState();
        this.stateInfo = shopSateEnum.getStateInfo();
        this.shop = shop;
    }

    //成功结果的构造器
    public ShopExecution(ShopSateEnum shopSateEnum,List<Shop> shop){
        this.state = shopSateEnum.getState();
        this.stateInfo = shopSateEnum.getStateInfo();
        this.shopList = shop;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
