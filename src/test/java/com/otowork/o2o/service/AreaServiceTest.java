package com.otowork.o2o.service;

import com.otowork.o2o.BaseTest;
import com.otowork.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lizxr on 2018/12/24.
 */
public class AreaServiceTest extends BaseTest{
    @Autowired
    private AreaService areaService;
    @Test
    public void getGetAreaList(){
        List<Area> areaList =  areaService.getAreaList();
        for(Area area:areaList){
            System.out.println(area);
        }
    }


}
