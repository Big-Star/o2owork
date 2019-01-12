package com.otowork.o2o.service;

import com.otowork.o2o.dto.ShopExecution;
import com.otowork.o2o.entity.Shop;
import com.otowork.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    Shop getByShopId(long shopId);

    /**
     * 修改电偶信息，包括对文件的处理
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     */
    ShopExecution modifyShop(Shop shop , InputStream shopImgInputStream,String fileName) throws ShopOperationException;


    /**
     * 注册店铺信息，包括文件处理
     * @param shop
     * @param shopImg
     * @param filePath
     * @return
     */
    ShopExecution addShop(Shop shop, InputStream shopImg,String filePath) throws ShopOperationException;

    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
}
