package com.otowork.o2o.service;

import com.otowork.o2o.BaseTest;
import com.otowork.o2o.dto.ShopExecution;
import com.otowork.o2o.entity.Area;
import com.otowork.o2o.entity.PersonInfo;
import com.otowork.o2o.entity.Shop;
import com.otowork.o2o.entity.ShopCategory;
import com.otowork.o2o.enums.ShopSateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;


    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1l);
        shopCondition.setOwner(personInfo);
       ShopExecution shopList = shopService.getShopList(shopCondition,0,5);
        System.out.println("店铺列表的数量" + shopList.getShopList().size());
        System.out.println("符合店铺的总数"+ shopList.getCount());
    }
    @Test
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(28L);
        shop.setShopName("改成张三的店铺");
        File file = new File("/Users/apple/Downloads/dabai.jpeg");
        InputStream is = new FileInputStream(file);
        shopService.modifyShop(shop,is,"dabai.jpeg");

    }

    @Test
    public void testAddShop() throws FileNotFoundException {
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
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setEnableStatus(ShopSateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File file = new File("/Users/apple/Downloads/xiaohuangya.jpeg");
        FileInputStream is = new FileInputStream(file);
        ShopExecution execution = shopService.addShop(shop, is ,file.getName());
        System.out.println(execution.getState());
        System.out.println(execution.getShop().getShopName());

    }
}
