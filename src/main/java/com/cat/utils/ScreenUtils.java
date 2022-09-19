package com.cat.utils;


import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;

import java.awt.image.*;

/**
 * 一些声明信息
 * Description:
 * date: 2022/9/2 15:10
 * author: 无境科技-KOL团队-许良
 */
public class ScreenUtils {
    /**
     * 本方法会根据游戏界面句柄扫描进程窗口,窗口尺寸并且返回窗口截图(进程不可以最小化)
     *
     * @param hwnd        - 游戏界面句柄
     * @param game_width  - 窗口宽度
     * @param game_height - 窗口高度
     *
     * @return - 返回窗口截图
     */
    public static BufferedImage scanningProcess(WinDef.HWND hwnd, int game_width, int game_height) {

        // 检索游戏窗口区域的显示设备上下文环境的句柄,以后在GDI函数中使用该句柄来在设备上下文环境中绘图
        WinDef.HDC gameDC = GDI32.INSTANCE.GetDC(hwnd);
        // 创建与指定的设备环境相关的设备兼容的位图
        WinDef.HBITMAP outputBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(gameDC, game_width, game_height);
        try {
            // 创建一个与指定设备兼容的内存设备上下文环境
            WinDef.HDC blitDC = GDI32.INSTANCE.CreateCompatibleDC(gameDC);
            try {
                // 选择一对象到指定的设备上下文环境中
                WinNT.HANDLE oldBitmap = GDI32.INSTANCE.SelectObject(blitDC, outputBitmap);
                try {
                    // 对指定的源设备环境区域中的像素进行位块(bit_block)转换
                    GDI32.INSTANCE.BitBlt(blitDC, 0, 0, game_width, game_height, gameDC, 0, 0, GDI32.SRCCOPY);
                } finally {
                    GDI32.INSTANCE.SelectObject(blitDC, oldBitmap);
                }
                // 位图信息头,大小固定40字节
                WinGDI.BITMAPINFO bi = new WinGDI.BITMAPINFO(40);
                bi.bmiHeader.biSize = 40;
                // 函数获取指定兼容位图的位,然后将其作一个DIB—设备无关位图使用的指定格式复制到一个缓冲区中
                boolean ok = GDI32.INSTANCE.GetDIBits(blitDC, outputBitmap, 0, game_height, (byte[]) null, bi,
                        WinGDI.DIB_RGB_COLORS);
                if (ok) {
                    WinGDI.BITMAPINFOHEADER bih = bi.bmiHeader;
                    bih.biHeight = -Math.abs(bih.biHeight);
                    bi.bmiHeader.biCompression = 0;
                    BufferedImage img = bufferedImageFromBitmap(blitDC, outputBitmap, bi);
                    return img;
                }
            } finally {
                GDI32.INSTANCE.DeleteObject(blitDC);
            }
        } finally {
            GDI32.INSTANCE.DeleteObject(outputBitmap);
        }

        return null;

    }
    // 依赖方法scanningProcess
    private static BufferedImage bufferedImageFromBitmap(WinDef.HDC blitDC, WinDef.HBITMAP outputBitmap, WinGDI.BITMAPINFO bi) {
        WinGDI.BITMAPINFOHEADER bih = bi.bmiHeader;
        int height = Math.abs(bih.biHeight);
        final ColorModel cm;
        final DataBuffer buffer;
        final WritableRaster raster;
        int strideBits = (bih.biWidth * bih.biBitCount);
        int strideBytesAligned = (((strideBits - 1) | 0x1F) + 1) >> 3;
        final int strideElementsAligned;
        switch (bih.biBitCount) {
            case 16:
                strideElementsAligned = strideBytesAligned / 2;
                cm = new DirectColorModel(16, 0x7C00, 0x3E0, 0x1F);
                buffer = new DataBufferUShort(strideElementsAligned * height);
                raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned,
                        ((DirectColorModel) cm).getMasks(), null);
                break;
            case 32:
                strideElementsAligned = strideBytesAligned / 4;
                cm = new DirectColorModel(32, 0xFF0000, 0xFF00, 0xFF);
                buffer = new DataBufferInt(strideElementsAligned * height);
                raster = Raster.createPackedRaster(buffer, bih.biWidth, height, strideElementsAligned,
                        ((DirectColorModel) cm).getMasks(), null);
                break;
            default:
                throw new IllegalArgumentException("检测到不支持的图片位数: " + bih.biBitCount);
        }
        final boolean ok;
        switch (buffer.getDataType()) {
            case DataBuffer.TYPE_INT: {
                int[] pixels = ((DataBufferInt) buffer).getData();
                ok = GDI32.INSTANCE.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), pixels, bi, 0);
            }
            break;
            case DataBuffer.TYPE_USHORT: {
                short[] pixels = ((DataBufferUShort) buffer).getData();
                ok = GDI32.INSTANCE.GetDIBits(blitDC, outputBitmap, 0, raster.getHeight(), pixels, bi, 0);
            }
            break;
            default:
                throw new AssertionError("检测到不支持的缓冲元素类型: " + buffer.getDataType());
        }
        if (ok) {
            return new BufferedImage(cm, raster, false, null);
        } else {
            return null;
        }
    }

    interface GDI32 extends com.sun.jna.platform.win32.GDI32 {
        GDI32 INSTANCE = (GDI32) Native.loadLibrary(GDI32.class);

        boolean BitBlt(WinDef.HDC hdcDest, int nXDest, int nYDest, int nWidth,
                       int nHeight, WinDef.HDC hdcSrc, int nXSrc, int nYSrc, int dwRop);

        WinDef.HDC GetDC(WinDef.HWND hWnd);

        boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines,
                          byte[] pixels, WinGDI.BITMAPINFO bi, int usage);

        boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines,
                          short[] pixels, WinGDI.BITMAPINFO bi, int usage);

        boolean GetDIBits(WinDef.HDC dc, WinDef.HBITMAP bmp, int startScan, int scanLines,
                          int[] pixels, WinGDI.BITMAPINFO bi, int usage);

        int SRCCOPY = 0xCC0020;
    }


    /**
     * 本方法可以向后台进程窗口发送鼠标事件从而实现后台操作游戏
     *
     * @param hwnd   - 指定后台进程窗口
     * @param mouseMessages - 鼠标事件描述
     */
    public static void MouseClick(WinDef.HWND hwnd, java.util.List<Work> mouseMessages) {

        for (int i = 0; i < mouseMessages.size(); i++) {
            // 解析鼠标坐标参数,低位为X轴,高位为Y轴坐标
           /* String X = Integer.toHexString(mouseMessages.get(i).getMouseX());
            String Y = Integer.toHexString(mouseMessages.get(i).getMouseY());*/
            String X = "1";
            String Y = "2";
            while (X.length() < 4) {
                X = "0" + X;
            }
            while (Y.length() < 4) {
                Y = "0" + Y;
            }
            Integer in = Integer.valueOf(Y + X, 16);
            WinDef.LPARAM lPARAM = new WinDef.LPARAM(in);
            int moveTime = (int) (Math.random() * 400 + 300);
            int mousePressTime = (int) (Math.random() * 500 + 400);
            try {
                // 模拟计算鼠标按下的间隔并且按下鼠标
                Thread.sleep(moveTime);
                User32.INSTANCE.PostMessage(hwnd, 513, new WinDef.WPARAM(513), lPARAM);
                Thread.sleep(mousePressTime);
                User32.INSTANCE.PostMessage(hwnd, 514, new WinDef.WPARAM(514), lPARAM);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }






}
