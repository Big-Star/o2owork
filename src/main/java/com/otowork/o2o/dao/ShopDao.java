package com.otowork.o2o.dao;

import com.otowork.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    /**
     * 分页查询店铺，可输入的条件有：店铺名；店铺状态；店铺类别；区域ID，owener
     * @param shopCondition
     * @param rowIndex 第几行开始取数据
     *
     * @param pageSize
     * @return
     */

    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);


    Shop queryByShopId(Long shopId);


}
