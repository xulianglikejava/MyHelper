package com.cat.controller;

import com.cat.utils.MouseUtils;
import org.junit.Test;

import java.util.ArrayList;

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
        System.out.println("开始启动小助手...");
        System.out.println("开始刷999咯...");
        int count = 0 ;
        /*设置每次打怪需要的最大时间*/
        long playTime = 35000;
        long waitBigTime = 3000;
        long waitSmallTime = 1000;
        long maxCount = 13;

        try {
            // 读取判断点信息
            ArrayList<int[]> 挑战的坐标 = mouseUtils.FileToArrayList("单人999坐标.txt");
            ArrayList<int[]> 单人结算999坐标 = mouseUtils.FileToArrayList("单人结算999坐标.txt");
            System.out.println("鼠标坐标读取完毕咯...");
            Thread.sleep((int) (Math.random() * waitBigTime + 300));

            // 开始刷本
            while (count <= maxCount) {
                // 遍历每个设置判断点
                for (int i = 0; i < 挑战的坐标.size(); i++) {
                    // 点击开始
                    System.out.println("点击开始...");
                    mouseUtils.MouseResponse(挑战的坐标.get(i));
                    System.out.println("等打完...");
                    Thread.sleep((int) ( playTime + Math.random() *2340));
                    /*再点击结算*/
                    System.out.println("再点击结算...");
                    mouseUtils.MouseResponse(单人结算999坐标.get(i));
                    System.out.println("再点击结算...");
                    Thread.sleep((int) (Math.random() * waitBigTime + 300));
                    mouseUtils.MouseResponse(单人结算999坐标.get(i));
                    // 结算完等待开始
                    Thread.sleep((int) (Math.random() * waitSmallTime + 300));
                    // 结算完等待开始
                    Thread.sleep((int) (Math.random() * waitBigTime + 300));
                }
                count++;
                System.out.println("刷了第" + count + "次了" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 设置单人999坐标() {
        System.out.println("开始启动小助手...");
        System.out.println("设置坐标...");
        try {
            mouseUtils.saveMouseInfo("单人999坐标.txt");
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
