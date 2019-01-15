package com.otowork.o2o.dao;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.otowork.o2o.BaseTest;
import com.otowork.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testBatchInsert(){
        long shopId =  28;
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        ProductCategory p1 = new ProductCategory();
        p1.setCreateTime(new Date());
        p1.setPriority(1);
        p1.setProductCategoryName("饼干");
        p1.setShopId(shopId);
        list.add(p1);
        ProductCategory p2 = new ProductCategory();
        p2.setShopId(shopId);
        p2.setCreateTime(new Date());
        p2.setProductCategoryName("牛奶");
        list.add(p2);
        productCategoryDao.batchInsertProductCategory(list);
    }

}
