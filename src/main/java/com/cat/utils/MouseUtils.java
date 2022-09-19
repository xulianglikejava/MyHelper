package com.cat.utils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些声明信息
 * Description:
 * date: 2022/8/29 10:58
 * author: 无境科技-KOL团队-许良
 */
public class MouseUtils {

    /**
     *@Title
     *@Descriptiom 获取鼠标的坐标信息
     *@Date: 2022/8/29
     *@Author: liangXu  xx1271328330@163.com
     *@Params:[]
     *@Return int[]
     */
    public int[] setPoint() throws Exception {

        Robot robot = new Robot();
        robot.delay(3000);
        // 获取鼠标坐标
        PointerInfo pinfo = MouseInfo.getPointerInfo();
        Point p = pinfo.getLocation();
        int mouseX = (int) p.getX();
        int mouseY = (int) p.getY();
        // 获取鼠标坐标颜色
        Color mouseRGB = robot.getPixelColor(mouseX, mouseY);
        int R = mouseRGB.getRed();
        int G = mouseRGB.getGreen();
        int B = mouseRGB.getBlue();
        // 返回鼠标的坐标值
        int[] array = new int[] { mouseX, mouseY, R, G, B };
        return array;
    }

    public void saveMouseInfo(String fileName) throws Exception{
        // 将数据保存到磁盘中，可以持久保存数据
        System.out.println("请准备移动坐标");
        Thread.sleep(2000);
        int[] Point = setPoint();
        FileOutputStream file = new FileOutputStream(fileName, true);
        String pointMessage = "{" + Point[0] + "," + Point[1] + "," + Point[2] + "," + Point[3]        + "," + Point[4] + "}\n";
        byte b[] = pointMessage.getBytes();
        file.write(b);
        file.close();
        System.out.println("该坐标点存储完毕");
    }

    /**
     * 本方法会将文件信息提取并且返回此文件信息列表
     *
     * @param FileURL - 指定的文件URL
     * @return - 返回的文件信息列表
     * @throws Exception - 如果发生 I/O 错误则抛出异常
     */
    public ArrayList<int[]> FileToArrayList(String FileURL) throws Exception {

        String string = null;
        int[] Point = null;
        ArrayList<int[]> PointList = new ArrayList<int[]>();
        //本人采用正则表达式提取数据,
        Pattern p = Pattern.compile("\\{([^,]+),([^,]+),([^,]+),([^,]+),([^\\}]+)\\}");
        BufferedReader File = new BufferedReader(new InputStreamReader(new FileInputStream(FileURL)));

        while ((string = File.readLine()) != null) {
            //虽然有其他存数据办法，比如数据库，但是不可能让用户专门下载个数据库，这是一个正常的逻辑
            Matcher rule = p.matcher(string);

            while (rule.find()) {
                // 将每行的数据提取并且赋值,最后添加进容器中
                int X = Integer.parseInt(rule.group(1));
                int Y = Integer.parseInt(rule.group(2));
                int R = Integer.parseInt(rule.group(3));
                int G = Integer.parseInt(rule.group(4));
                int B = Integer.parseInt(rule.group(5));
                Point = new int[] { X, Y, R, G, B };
                PointList.add(Point);
            }
        }
        File.close();
        return PointList;

    }

    /**
     * 本方法会根据设定的判断点与真实点进行对比,如果颜色一致则移动鼠标到该点进行单击操作
     *
     * @param Point - 判断点的相关信息
     * @throws Exception - 如果平台配置不允许使用Robot类则抛出异常
     */
    public void MouseResponse(int[] Point) throws Exception {

        // 获取判断点的信息
        int decisionX = Point[0];
        int decisionY = Point[1];
        int decisionR = Point[2];
        int decisionG = Point[3];
        int decisionB = Point[4];
        // 获取真实点的颜色
        Robot robot = new Robot();
        Color decisionRGB = robot.getPixelColor(decisionX, decisionY);
        int mouseR = decisionRGB.getRed();
        int mouseG = decisionRGB.getGreen();
        int mouseB = decisionRGB.getBlue();
        // 如果真实点与判断点颜色一致,则执行以下操作
      /*  if (Math.abs(mouseR - decisionR) < 5 && Math.abs(mouseG - decisionG) < 5 && Math.abs(mouseB - decisionB) < 5) {
        }*/
        // 计算鼠标位置并且移动到该位置
        int mouseMoveX = (int) (Math.random() * 4 ) + decisionX;
        int mouseMoveY = (int) (Math.random() * 5 ) + decisionY;
        // 修复JDK8的移动不正确的BUG
        for (int i = 0; i < 6; i++) {
            robot.mouseMove(mouseMoveX, mouseMoveY);
            System.out.println("鼠鼠移动到了(" + mouseMoveX + "," +  mouseMoveY + ")");
        }
        // 模拟计算鼠标按下的间隔并且按下鼠标
        int moveTime = (int) (Math.random() * 500 + 400);
        int mousePressTime = (int) (Math.random() * 500 + 210);
        robot.delay(moveTime);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(mousePressTime);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }




}
