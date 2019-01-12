package com.otowork.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otowork.o2o.dto.ShopExecution;
import com.otowork.o2o.entity.Area;
import com.otowork.o2o.entity.PersonInfo;
import com.otowork.o2o.entity.Shop;
import com.otowork.o2o.entity.ShopCategory;
import com.otowork.o2o.enums.ShopSateEnum;
import com.otowork.o2o.service.AreaService;
import com.otowork.o2o.service.ShopCategoryService;
import com.otowork.o2o.service.ShopService;
import com.otowork.o2o.utils.CodeUtil;
import com.otowork.o2o.utils.HttpServletRequestUtil;
import com.otowork.o2o.utils.ImageUtil;
import com.otowork.o2o.utils.PathUtil;
import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;


    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/getshopmanagentinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagentInfo(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2owork/shop/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopList(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        request.getSession().setAttribute("user",personInfo);
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        long employeeId = user.getUserId();
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String , Object> modelMap = new HashMap<String ,Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String ,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //1.
        String shopStr =  HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = new Shop();
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg","上传图片不能为空！");
            return modelMap;
        }

        //2。注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);
           /* File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                //e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }*/
//            try {
////                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//
//            } catch (IOException e) {
////                e.printStackTrace();
//                modelMap.put("success", false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (se.getState() == ShopSateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<>();
                    }
                    shopList.add(shop);
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg","请输入店铺信息！");
            return modelMap;
        }

    }
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String ,Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }
        //1.
        String shopStr =  HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = new Shop();
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        //2。修改店铺
        if (shop != null && shop.getShopId()!=null) {

           /* File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                //e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }*/
//            try {
////                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//
//            } catch (IOException e) {
////                e.printStackTrace();
//                modelMap.put("success", false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
            ShopExecution se = null;
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop,null,null);

                }else {
                    se = shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());

                }
                if (se.getState() == ShopSateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return modelMap;
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg","请输入店铺信息！");
            return modelMap;
        }

    }

    /*private static void inputStreamToFile(InputStream is, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = is.read(buffer)) != -1){
                os.write(buffer,0,bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用INPUTSTREAMTOFILE失败异常：" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                throw new RuntimeException("inputstreamFile 关闭失败异常：" + e.getMessage());
            }
        }
    }*/

    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo(){
        Map<String , Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success" , false);
            modelMap.put("errMsg",e.getMessage());
        }

        return modelMap;
    }
}
