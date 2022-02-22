package com.example.demo;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;


/**
 * 图片沿y轴和x轴翻转
 * 目前该注释使用中文 若以后真正项目使用英文注释
 */
public class ImageTest {


    /**
     * 翻转Y轴和X轴
     *
     * @param imageUrl 图片下载url
     * @param saveName 保存名称
     * @param suffix   图片类型 如jpg png
     * @throws Exception
     */
    protected void spin(String imageUrl, String saveName, String suffix) throws Exception {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(imageUrl);

        HttpResponse response = client.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();

        BufferedImage bi = ImageIO.read(is);

        //翻转Y轴
        bi = flipVerticalJ2D(bi);
        //翻转X轴
        bi = flipHorizontalJ2D(bi);
        //写入图片
        File sf = new File(saveName + "." + suffix);
        ImageIO.write(bi, suffix, sf);
    }



    /**
     * 图像沿Y轴翻转。
     *
     * @param bufferedImage 原图像。
     * @return 返回沿Y轴翻转后的图像。
     */
    public static BufferedImage flipVerticalJ2D(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage dstImage = new BufferedImage(width, height, bufferedImage.getType());

        AffineTransform affineTransform = new AffineTransform(1, 0, 0, -1, 0, height);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return affineTransformOp.filter(bufferedImage, dstImage);
    }

    /**
     * 图像沿x轴翻转。
     *
     * @param bufferedImage 原图像。
     * @return 返回沿x轴翻转后的图像。
     */
    public static BufferedImage flipHorizontalJ2D(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage dstImage = new BufferedImage(width, height, bufferedImage.getType());

        AffineTransform affineTransform = new AffineTransform(-1, 0, 0, 1, width, 0);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return affineTransformOp.filter(bufferedImage, dstImage);
    }


    public static void main(String[] args) throws Exception {
        ImageTest imageTest = new ImageTest();
        imageTest.spin("https://images.unsplash.com/photo-1631086459990-06bc4d7ad6cf","flip1","jpg");
        //对于该图片 经测试下载很慢 不知是否是网络原因 请耐心等待
        //imageTest.spin("https://effigis.com/wp-content/uploads/2015/02/Airbus_Pleiades_50cm_8bit_RGB_Yogyakarta.jpg","flip2","jpg");
    }


}
