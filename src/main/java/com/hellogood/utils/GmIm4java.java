package com.hellogood.utils;
import java.io.IOException;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

/**
 * 图片压缩并且补白边
 */
public class GmIm4java {

    /**
     * @param args
     * author：kejian
     * data：2018-1-7 下午5:15:13
     */
    public static void main(String[] args) {

        String picPath = "C:\\Users\\KJ\\Desktop\\1.jpg";  //需要处理图片地址
        String drawPicPath = "C:\\Users\\KJ\\Desktop\\2test.jpg";  //输出图片地址
        int width = 400; //压缩后宽度
        int height = 400;//压缩后高度
        try {
            dramImg(picPath, drawPicPath , width, height);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IM4JavaException e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理图片
     * @param picPath 处理图片地址
     * @param drawPicPath  输出图片地址
     * @param width  压缩宽度
     * @param height 压缩高度
     * @throws IOException
     * @throws InterruptedException
     * @throws IM4JavaException
     * author：kejian
     * data：2018-1-8 下午5:51:12
     */
    private static void dramImg(String picPath, String drawPicPath, int width, int height) throws IOException, InterruptedException, IM4JavaException{
        IMOperation op = new IMOperation();
        op.addImage(picPath);
        op.resize(width, height);  //压缩
        op.background("white");   //补白必须
        op.gravity("center");     //补白必须
        op.extent(width, height); //补白必须
        op.quality(80d);
        op.addImage(drawPicPath);
        ConvertCmd cmd = new ConvertCmd(true);
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.indexOf("win") >= 0){
            cmd.setSearchPath("C:\\Program Files\\GraphicsMagick-1.3.28-Q16"); //安装目录
        }
        cmd.setErrorConsumer(StandardStream.STDERR);
        cmd.run(op);
    }

}
