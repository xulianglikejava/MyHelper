package com.cat.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 一些声明信息
 * Description:
 * date: 2022/9/2 15:10
 * author: 无境科技-KOL团队-许良
 */
public class ScreenUtils {
   /* public static int x;
    public static int y;
    public static int width;
    public static int height;*/
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //全屏截图
    public BufferedImage getFullScreenShot() {
        BufferedImage bfImage = null;
        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(screenSize));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return bfImage;

    }

    //从本地文件读取目标图片
    public BufferedImage getBfImageFromPath(String keyImagePath) {
        BufferedImage bfImage = null;
        try {
            bfImage = ImageIO.read(new File(keyImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bfImage;
    }

}
