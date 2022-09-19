package com.cat.controller;

import com.cat.utils.MouseUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * 一些声明信息
 * Description:
 * date: 2022/8/25 14:05
 * author: 无境科技-KOL团队-许良
 */

public class 单人999 {
    MouseUtils mouseUtils = new MouseUtils();

    @Test
    public void 开始999() {

        /*设置每次打怪需要的最大时间*/
        long playTime = 35000;
        long waitBigTime = 3000;
        long waitSmallTime = 1000;
        long maxCount = 13;


        System.out.println("开始启动小助手...");
        System.out.println("开始刷999咯...");


        try {
            // 读取判断点信息
            ArrayList<int[]> 挑战的坐标 = mouseUtils.FileToArrayList("单人999挑战坐标.txt");
            ArrayList<int[]> 单人结算999坐标 = mouseUtils.FileToArrayList("单人结算999坐标.txt");
            System.out.println("鼠标坐标读取完毕咯...");
            Thread.sleep((int) (Math.random() * waitBigTime + 4000));

            for (int i = 0; i < maxCount; i++) {



                // 点击开始
                System.out.println("-----点击开始第" + i+1 + "次-----");
                mouseUtils.MouseResponse(挑战的坐标.get(0));
                System.out.println("-----等打完-----");
                Thread.sleep((int) ( playTime + Math.random() *2340));
                System.out.println("----点击结算----");
                /*结算的坐标暂时设为10个 每次都随机取出一个 */
                Random r = new Random();
                int data = r.nextInt(10);
                mouseUtils.MouseResponse(单人结算999坐标.get(data));
                System.out.println("----再点击结算----");
                Thread.sleep((int) (Math.random() * waitSmallTime + 300));
                mouseUtils.MouseResponse(单人结算999坐标.get(data));
                System.out.println("----等待跳转到开始挑战页面----");
                Thread.sleep((int) (Math.random() * waitSmallTime + 300));

                System.out.println("----刷了第" + i+1 + "次了----" );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 设置单人999挑战坐标() {
        System.out.println("开始启动小助手...");
        System.out.println("设置坐标...");
        try {
            mouseUtils.saveMouseInfo("单人999挑战坐标.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 设置结算坐标() {
        System.out.println("开始启动小助手...");
        System.out.println("设置坐标...");
        try {
            mouseUtils.saveMouseInfo("单人结算999坐标.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
