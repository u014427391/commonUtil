package com.example.common.util.file;


import com.google.common.base.Splitter;
import org.bytedeco.javacpp.Loader;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class PictureProcess {

    public static void main(String[] args) throws Exception {

//        File file = new File("D:\\3061998946-65a91581cb9bf_cover(1)(1).jpg");
//        File[] files = file.listFiles();
//        for (File file1 : files) {
//
//        }
        String targetPath = "D:\\3061998946-65a91581cb9bf_cover.jpg";
        File file1 = new File("D:\\3061998946-65a91581cb9bf_cover(1)(1).jpg");
        System.out.println(rotate(file1.getPath(), targetPath , 90));

    }

    /**
     * 旋转
     *
     * @Date 2024/08/27 15:46
     * @Param imagePath 图片地址
     * @Param outputDir 输出目录
     * @Param angle 角度
     * @return java.lang.String 图片地址
     */
    public static String rotate(String imagePath, String outputDir, Integer angle) throws Exception {
        List<String> paths = Splitter.on(".").splitToList(imagePath);
        String ext = paths.get(paths.size() - 1);
        if (!Arrays.asList("png", "jpg", "jpeg").contains(ext)) {
            throw new Exception("format error");
        }
        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        ProcessBuilder builder =
                new ProcessBuilder(
                        ffmpeg,
                        "-i",
                        imagePath,
                        "-vf",
                        MessageFormat.format("rotate=PI*{0}/180", String.valueOf(angle)),
                        "-y",
                        outputDir);
        builder.inheritIO().start().waitFor();
        return outputDir;
    }
}
