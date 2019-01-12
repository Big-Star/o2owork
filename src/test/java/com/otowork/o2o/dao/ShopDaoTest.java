package com.otowork.o2o.dao;

import com.otowork.o2o.BaseTest;
import com.otowork.o2o.entity.Area;
import com.otowork.o2o.entity.PersonInfo;
import com.otowork.o2o.entity.Shop;
import com.otowork.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest  extends BaseTest {
    @Autowired
    private ShopDao shopDao;



    @Test
    public void testQueryShop(){
        Shop shopCondition = new Shop();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1l);
        shopCondition.setOwner(personInfo);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,0,5);
        System.out.println("店铺列表的数量" + shopList.size());
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("符合店铺的总数"+count);
    }


    @Test
    public void testQueryShopList(){
        long id = 28;
        Shop shop = new Shop();
        shop.setShopId(id);
        Shop list = shopDao.queryByShopId(id);
    }


    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        System.out.println(effectedNum);
    }
    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("张三的店铺");
        shop.setPhone("13334455");
        shop.setLastEditTime(new Date());
        int effectNum = shopDao.updateShop(shop);
        System.out.println(effectNum);

    }
}
