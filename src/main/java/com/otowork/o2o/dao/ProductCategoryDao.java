package com.otowork.o2o.dao;

import com.otowork.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    public List<ProductCategory>  queryProductCategory();


    /**
     * 通过shop id 查询店铺商品类别
     *
     * @param long  shopId
     * @return List<ProductCategory>
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
