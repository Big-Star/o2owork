package com.otowork.o2o.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    private static final  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final Random random = new Random();

    /**
     *   将 commonsfile  转换成file
     * @param file
     * @return
     * @throws IOException
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile file) throws IOException {
        File newFile = new File(file.getOriginalFilename());
        file.transferTo(newFile);
        return newFile;

    }

    /**
     * 处理缩略图，并生成相对值路径
     * @param
     * @param targetAddr
     * @return
     */
    public static  String generateThumbnail(InputStream fileInputStream,String fileName, String targetAddr){
        //生成随机文件名称
        String realFileName = getRandomFileName();
        //获取文件名称后缀
        String extension = getFileExtension(fileName);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" +relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(fileInputStream).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/timg.jpeg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径涉及到的路径
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File (realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件后缀名
     * @param
     * @return
     */

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成文件随机名，当前年月日小十分钟秒钟+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        int sum = random.nextInt(89999) + 10000;
        String nowTimeStr = simpleDateFormat.format(new Date());
        return nowTimeStr+sum;

    }

    /**
     * 删除图片或目录
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File file = new File(PathUtil.getImgBasePath() + storePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                file1.delete();
            }

        }
        file.delete();
    }

    public static void main(String[] args) throws IOException {

        Thumbnails.of(new File("/Users/apple/Downloads/xiaohuangya.jpeg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/timg.jpeg")), 0.25f)
                .outputQuality(0.8f).toFile("/Users/apple/Downloads/xiaohuangyanew.jpeg");
    }
}
