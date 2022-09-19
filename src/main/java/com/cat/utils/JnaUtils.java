package com.cat.utils;/**
 * @ClassName JnaUtils
 * @Description TODO
 * @Author 无境科技-许良
 * @Date 2022/9/19 15:54
 * @Version 1.0
 **/

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.junit.Test;

/**
 *@ClassName JnaUtils
 *@Description TODO
 *@Author xuliang
 *@Date 2022/9/19 15:54
 *@Version 1.0
 **/
public class JnaUtils {
    @Test
    public void bindWindows(){
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "雷电模拟器");
        if (hwnd == null) {
            System.out.println("TSITSMonitor is not running");
        } else {
            WinDef.RECT win_rect = new  WinDef.RECT();
            User32.INSTANCE.GetWindowRect(hwnd, win_rect);
            int win_width = win_rect.right - win_rect.left;
            int win_height = win_rect.bottom - win_rect.top;

            User32.INSTANCE.MoveWindow(hwnd, 300, 100, win_width, win_height, true);
        }

    }
}
