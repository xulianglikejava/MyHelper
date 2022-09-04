package com.cat.start;

import com.cat.utils.ScreenUtils;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * 一些声明信息
 * Description:
 * date: 2022/9/2 15:14
 * author: 无境科技-KOL团队-许良
 */
public class 魂土 {
    ScreenUtils screenUtils = new ScreenUtils();

    @Test
    public void 开始魂土() {
        System.out.println("开始启动小助手...");
        System.out.println("开始刷业原火咯...");
        int count = 0 ;
        /*设置每次打怪需要的最大时间*/
        long playTime = 60000;
        long waitTime = 2000;
        long maxCount = 30;
        try {
            BufferedImage fullScreenShot = screenUtils.getFullScreenShot();
            BufferedImage bfImageFromPath = screenUtils.getBfImageFromPath("/img/sx_qr.bmp");

            // 开始刷本
            while (count <= maxCount) {
                
                count++;
                System.out.println("刷了第" + count + "次了" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
