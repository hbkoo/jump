package jump;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static sun.audio.AudioDevice.device;

public class Unit {

    //    private double k = 2.089;
    private double k = 1.4;
    private int high = 600, width = 50;

    private static Unit unit = null;

    private Unit() {
    }

    public static Unit getInstance() {
        if (unit == null) {
            unit = new Unit();
        }
        return unit;
    }

    public long getTime() {
        long time;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("E:\\jump\\adb.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] end = getEndLocation1(image);
        int[] start = getStartLocation(image);
        double distance = Math.sqrt((Math.pow(start[0] - end[0], 2) + Math.pow(start[1] - end[1], 2)));
        time = (long) (distance * k);

        System.out.println("height:" + image.getHeight() + ",width:" + image.getWidth());
        System.out.println("start:" + start[0] + " " + start[1]);
        System.out.println("end:" + end[0] + " " + end[1]);
        System.out.println("distance:" + distance);

        return time;
    }

    private int[] getStartLocation(BufferedImage image) {
        high = image.getHeight() * 2 / 3;
        width = image.getWidth();
        int start[] = new int[2];
        int rgb[];
        int min = width, max = 0;
        int py = 300;
        for (int i = image.getHeight() / 3; i <= high; i++) {
            for (int j = 50; j <= width; j++) {
                rgb = getRGB(image, j, i);//获取该点的RGB值
                if (Math.abs(rgb[0] - 54) < 10 &&
                        Math.abs(rgb[1] - 52) < 10 &&
                        Math.abs(rgb[2] - 92) < 10) {
                    //以便确定棋子的横坐标，
                    // 即确定棋子的最左边的坐标和最右边的坐标，二者中间即是棋子的横坐标
                    if (j < min) {
                        min = j;
                        py = i;
                    }
                    if (j > max) max = j;
                }
            }
        }
        start[0] = (max + min) / 2;
        start[1] = py;
        System.out.println("start:max=" + max + ",min=" + min);
        return start;
    }

    private int[] getEndLocation(BufferedImage image) {
        high = image.getHeight() * 2 / 3;//853
        width = image.getWidth();

        int num1 = 0, num2 = 0;
        int text = 0;

        boolean isOver = false;
        int end[] = new int[2];
        int first[], second[];
        int min = width, max = 0;
        int py = 0;

        first = getRGB(image, 5, image.getHeight() / 3);
        int h;//426
        for (h = image.getHeight() / 3; !isOver && h <= high; h++) {
            for (int w = 5; w < width; w++) {
                second = getRGB(image, w, h);
                if ((Math.abs(first[0] - second[0])
                        + Math.abs(first[1] - second[1])
                        + Math.abs(first[2] - second[2])) > 10) {
                    num1++;
                    if (w < min) {
                        if (py >= h) {
                            isOver = true;
                            break;
                        } else {
                            py = h;
                            min = w;
                        }
                    }
                    if (w > max) max = w;
                }
                first = second;
                num2++;
            }
        }
        end[0] = (max + min) / 2;
        end[1] = py;
        System.out.println("end:max=" + max + ",min=" + min);
        System.out.println("h=" + h);
        System.out.println("end:num1=" + num1 + ",num2 = " + num2 + ",百分比:" + num1 * 100 / num2 + "%");
        return end;
    }

    private int[] getEndLocation1(BufferedImage image) {
        high = image.getHeight() * 2 / 3;//853
        width = image.getWidth();

        boolean isOver = false;
        int end[] = new int[2];
        int left[] = new int[2];
        left[0] = width;
        int rgb[] = new int[2];
        int first[], second[];
        first = getRGB(image, 5, image.getHeight() / 3);

        int h;
        //寻找上顶点
        for (h = image.getHeight() / 3; !isOver && h <= high; h++) {
            for (int w = 5; w < width; w++) {
                second = getRGB(image, w, h);
                if ((Math.abs(first[0] - second[0])
                        + Math.abs(first[1] - second[1])
                        + Math.abs(first[2] - second[2])) > 10) {
                    //发现颜色突变，判断这个突变的颜色是否为棋子的颜色
                    if (isStart(first)) {
                        //第一次遇到棋子的颜色
                        continue;
                    } else if (isStart(second)) {
                        //第二次遇到棋子的颜色
                        continue;
                    } else {
                        //找到上顶点
                        end[0] = w;
                        isOver = true;
                        rgb = second;
                        break;
                    }
                }
                first = second;
            }
        }
        //寻找左顶点
        for (; h <= high; h++) {
            int w = 5;
            for (; w < width; w++) {
                second = getRGB(image, w, h);
                if ((Math.abs(rgb[0] - second[0])
                        + Math.abs(rgb[1] - second[1])
                        + Math.abs(rgb[2] - second[2])) < 10) {
                    first = getRGB(image, end[0], h);
                    if ((Math.abs(first[0] - 245)
                            + Math.abs(first[1] - 245)
                            + Math.abs(first[2] - 245)) < 3) {
                        end[1] = h + 10;
                        System.out.println("middle,中心");
                        return end;
                    }
                    if (left[0] < w) {
                        end[1] = left[1];
                        return end;
                    } else {
                        left[0] = w;
                        left[1] = h;
                        break;
                    }
                }
            }

        }

        return end;
    }

    private boolean isStart(int [] rgb) {
        //判断是否为棋子的颜色
        if (Math.abs(rgb[0] - 54) < 10 &&
                Math.abs(rgb[1] - 52) < 10 &&
                Math.abs(rgb[2] - 92) < 10){
            return true;
        }
        return false;
    }

    private int[] getRGB(BufferedImage image, int i, int j) {
        int[] rgb = new int[3];
        if (image != null && i < image.getWidth() && j < image.getHeight()) {
            int pixel = image.getRGB(i, j);
            rgb[0] = (pixel & 0xff0000) >> 16;
            rgb[1] = (pixel & 0x00ff00) >> 8;
            rgb[2] = (pixel & 0x0000ff);
        }
        return rgb;
    }

    //获取截屏图片并保存到PC机上
    public void screenANDsave(Runtime runtime) {
        String PULL = "adb pull /sdcard/adb1.png E:\\jump\\adb.png";
        String SCRENN = "adb shell screencap -p /sdcard/adb1.png";
        try {
            Process process = runtime.exec(SCRENN);
            process.waitFor();
            process.destroy();
            Process process1 = runtime.exec(PULL);
            process1.waitFor();
            process1.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //控制跳跃
    public void jump(long time, Runtime runtime) {
        String jump = "adb shell input swipe 200 200 300 300 " + time;
        try {
            Process process = runtime.exec(jump);
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int[] getStartLocation1(BufferedImage image) {
        high = image.getHeight() * 2 / 3;
        width = image.getWidth();

        int start[] = new int[2];
        int rgb[];
        int min, max;
        int colorLength = 0;
        int s1 = 0, s2 = 0;
        for (int j = image.getHeight() / 3; j < high; j++) {
            min = width;
            max = 0;
            for (int i = 50; i < width; i++) {
                rgb = getRGB(image, i, j);//获取该点的RGB值
                if (Math.abs(rgb[0] - 54) < 10 &&
                        Math.abs(rgb[1] - 52) < 10 &&
                        Math.abs(rgb[2] - 92) < 10) {
                    //以便确定棋子的横坐标，
                    // 即确定棋子的最左边的坐标和最右边的坐标，二者中间即是棋子的横坐标
                    if (i < min) min = i;
                    if (i > max) max = i;
                }
            }
            if (max - min > colorLength) {
                colorLength = max - min;
                s1 = max;
                s2 = min;
                start[0] = (max + min) / 2;
                start[1] = j;
            }
        }
        System.out.println("start:max=" + s1 + ",min=" + s2);

        return start;
    }

}
