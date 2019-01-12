package com.otowork.o2o.dao;

import com.otowork.o2o.BaseTest;
import com.otowork.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lizxr on 2018/12/23.
 */
public class AreaDaoTest extends BaseTest{
    @Autowired
    private AreaDao areaDao;


    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        System.out.println(areaList.size());
        for(Area area :areaList){
            System.out.println(area);

        }
    }

}
