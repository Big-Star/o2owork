package com.otowork.o2o.dao;

import com.otowork.o2o.BaseTest;
import com.otowork.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory(){
        List<ShopCategory> query = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println(query.size());

        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(1l);
        shopCategory.setParent(parent);
        query = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(query.size());

    }
}
