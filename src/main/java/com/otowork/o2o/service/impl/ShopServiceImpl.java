package com.otowork.o2o.service.impl;

import com.otowork.o2o.dao.ShopDao;
import com.otowork.o2o.dto.ShopExecution;
import com.otowork.o2o.entity.Shop;
import com.otowork.o2o.enums.ShopSateEnum;
import com.otowork.o2o.exceptions.ShopOperationException;
import com.otowork.o2o.service.ShopService;
import com.otowork.o2o.utils.ImageUtil;
import com.otowork.o2o.utils.PageCalculator;
import com.otowork.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String filePath) {
        //1判断是否修改图片
        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopSateEnum.NULL_SHOP);
        }
        try {
            if (shopImgInputStream != null && filePath != null && !"".equals(filePath)) {
                Shop oraShop = shopDao.queryByShopId(shop.getShopId());
                if (oraShop.getShopImg() != null) {
                    ImageUtil.deleteFileOrPath(oraShop.getShopImg());
                }
                addShopImg(shop, shopImgInputStream, filePath);

            }
            //2维护商店信息
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.updateShop(shop);
            if (effectNum <= 0) {
                return new ShopExecution(ShopSateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExecution(ShopSateEnum.SUCCESS, shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopOperationException(e.getMessage());
        }

    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) {
        if(shop == null){
            return  new ShopExecution(ShopSateEnum.NULL_SHOP);
        }
        try{
            //给店铺信息赋值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <= 0){
                throw new ShopOperationException("店铺创建失败！");
            }else {
                if(shopImgInputStream != null){
                    //存储图片
                    try{
                        addShopImg(shop,shopImgInputStream,fileName);
                    } catch(Exception e){
                        e.printStackTrace();
                        throw new ShopOperationException("addShopImg error: " + e.getMessage());
                    }
                    //更新店铺信息
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopSateEnum.CHECK,shop);
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopSateEnum.INNER_ERROR.getState());
        }

        return se;
    }

    private void addShopImg(Shop shop, InputStream shopImg,String fileName) {
        // 获取shop的图片目录的相对路径
        String dest = PathUtil.getShopImgPath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
