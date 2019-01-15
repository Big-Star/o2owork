package com.otowork.o2o.service;

import com.otowork.o2o.entity.ProductCategory;
import com.otowork.o2o.enums.ProductCategoryExecution;
import com.otowork.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategory> getProductCategory();
    /**
     * 查询指定某个店铺下的所有商品类别信息
     *
     * @param long
     *            shopId
     * @return List<ProductCategory>
     */
    List<ProductCategory> getProductCategoryList(long shopId);
    /**
     *
     * @param productCategory
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;
}
