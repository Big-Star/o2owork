package com.otowork.o2o.service.impl;

import com.otowork.o2o.dao.ProductCategoryDao;
import com.otowork.o2o.entity.ProductCategory;
import com.otowork.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategory() {
        return productCategoryDao.queryProductCategory();
    }
}
