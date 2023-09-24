package com.example.common.util.qrcode;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QrCodeGenerator {


    private static final String DEFAULT_CHAR_SET = "UTF-8";

    private static final String DEFAULT_FORMAT_NAME = "JPG";


    // 二维码宽度
    private static final int DEFAULT_QR_CODE_WIDTH = 300;
    // 二维码高度
    private static final int DEFAULT_QR_CODE_HEIGHT = 300;

    /**
     * 创建BitMatrix比特矩阵
     * @Date 2023/09/24 22:29
     * @Param contents 二维码里的内容
     * @Param width 二维码宽度
     * @param height 二维码高度
     * @return com.google.zxing.common.BitMatrix
     */
    public static  BitMatrix createBitMatrix(String contents , int width , int height) throws WriterException, IOException {
        if (ObjectUtil.isNull(width)) {
            width = DEFAULT_QR_CODE_WIDTH;
        }
        if (ObjectUtil.isNull(height)) {
            height = DEFAULT_QR_CODE_HEIGHT;
        }

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 纠错等级L,M,Q,H
        hints.put(EncodeHintType.CHARACTER_SET, DEFAULT_CHAR_SET);// 编码utf-8
        hints.put(EncodeHintType.MARGIN, 1);  // 边距

        // 创建比特矩阵
        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                BarcodeFormat.QR_CODE, width, height, hints);
        return bitMatrix;

    }

    /**
     * 创建二维码，返回字节数组
     * @Date 2023/09/24 22:30
     * @Param contents 二维码里的内容
     * @Param imageFormat 图片后缀名
     * @Param width 二维码宽度
     * @param height 二维码高度
     * @return byte[]
     */
    public static byte[] createQrCode(String contents , String imageFormat , int width , int height) throws WriterException, IOException {
        if (StrUtil.isBlank(imageFormat)){
            imageFormat = DEFAULT_FORMAT_NAME;
        }
        BitMatrix bitMatrix = createBitMatrix(contents , width, height);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, os);
        return os.toByteArray();
    }

    /**
     * 创建二维码，返回base64字符串
     * @Date 2023/09/24 22:30
     * @Param contents 二维码里的内容
     * @Param imageFormat 图片后缀名
     * @Param width 二维码宽度
     * @param height 二维码高度
     * @return byte[]
     */
    public static String createQrCodeBase64(String contents , String imageFormat , int width , int height) throws WriterException, IOException {
        byte[] bytes =createQrCode(contents , imageFormat , width, height);
        return Base64.encode(bytes);
    }

    /**
     * 解码二维码
     * @Date 2023/09/24 22:32
     * @Param [image]
     * @return java.lang.String
     */
    public static String decodeQrCode(BufferedImage image) throws Exception {
        if (image == null) return StrUtil.EMPTY;
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, DEFAULT_CHAR_SET);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 转换为BufferedImage
     * @Date 2023/09/24 22:32
     * @Param [bitMatrix]
     * @return java.awt.image.BufferedImage
     */
    public static BufferedImage toBufferedImage(BitMatrix bitMatrix) throws IOException, WriterException {
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(-16777215, -1);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
        return bufferedImage;
    }

    /**
     * 给二维码添加logo
     * @Date 2023/09/24 22:33
     * @Param [bufferedImage, logoFile]
     * @return java.awt.image.BufferedImage
     */
    public static BufferedImage addQrCodeLogo(BufferedImage bufferedImage, File logoFile) throws IOException {
        Graphics2D graphics = bufferedImage.createGraphics();
        int matrixWidth = bufferedImage.getWidth();
        int matrixHeigh = bufferedImage.getHeight();

        // 读取logo图片文件
        BufferedImage logo = ImageIO.read(logoFile);
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        //  计算logo放置位置
        int x = bufferedImage.getWidth()  / 5*2;
        int y = bufferedImage.getHeight() / 5*2;
        int width = matrixWidth / 5;
        int height = matrixHeigh / 5;

        // 开始绘制图片
        graphics.drawImage(logo, x, y, width, height, null);
        graphics.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
        graphics.setStroke(new BasicStroke(5.0F, 1, 1));
        graphics.setColor(Color.white);
        graphics.drawRect(x, y, logoWidth, logoHeight);

        graphics.dispose();
        bufferedImage.flush();
        return bufferedImage;
    }

    public static void main(String[] args) throws Exception {
        BufferedImage bufferedImage = toBufferedImage(createBitMatrix("https://blog.csdn.net", 300, 300));
        ImageIO.write(bufferedImage, "png", new File("D:/qrcode.jpg"));

        System.out.println(decodeQrCode(bufferedImage));

        BufferedImage logoQrCode = addQrCodeLogo(bufferedImage, new File("D://logo.png"));
        ImageIO.write(logoQrCode, "png", new File("D:/logoQrcode.jpg"));
    }

}
