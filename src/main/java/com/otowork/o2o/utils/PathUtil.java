package com.otowork.o2o.utils;

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/images/";

        }else{
            basePath = "/Users/apple/project/images";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    public static String getShopImgPath(long shopId){
        String imagePath = "/upload/shop" + shopId + "/";
        return imagePath.replaceAll("/",separator);
    }
}
