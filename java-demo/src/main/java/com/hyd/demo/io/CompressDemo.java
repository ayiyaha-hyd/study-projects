package com.hyd.demo.io;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * java压缩解压
 */
public class CompressDemo {
    public static void main(String[] args) throws Exception {
        /*
        java 压缩解压 位于java.util.zip 目录下，继承关系如下：
        java.lang.Object
            java.io.InputStream
                java.io.FilterInputStream
                    java.util.zip.InflaterInputStream
                        java.util.zip.ZipInputStream

        zip扮演着归档和压缩两个角色；gzip并不将文件归档，仅只是对单个文件进行压缩，
        所以，在UNIX平台上，命令tar通常用来创建一个档案文件，然后命令gzip来将档案文件压缩，后缀名.gz



         */
        String srcFilePath1 = "C:\\Users\\ayiya\\Downloads\\jdk-8u281-windows-x64-demos.zip";
        String srcFilePath = "C:\\Users\\ayiya\\Downloads\\test.zip";
        String desDirPath = "C:\\Users\\ayiya\\Downloads";
        unzipFile(srcFilePath,desDirPath);

    }

    private static void zipFile(File file) {

    }

    /**
     * zip解压
     * （路径及文件名不能包含中文）
     * @param srcFilePath 压缩文件的路径
     * @param desDirPath  解压到的目录路径
     * @throws IOException
     */
    private static void unzipFile(String srcFilePath, String desDirPath) throws IOException {
        //获取当前压缩文件
        File srcFile = new File(srcFilePath);
        //判断源文件是否存在
        if (!srcFile.exists()) {
            throw new FileNotFoundException("指定文件不存在！");
        }
        ZipInputStream zIn = null;
        BufferedOutputStream bOut = null;
        try {
            zIn = new ZipInputStream(new FileInputStream(srcFile));
            ZipEntry entry = null;
            File file = null;
            while ((entry = zIn.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    file = new File(desDirPath, entry.getName());
                    if (!file.exists()) {
                        //创建此文件的上级目录
                        file.getParentFile().mkdirs();//写法二：new File(file.getParent()).mkdirs();
                    }
                    bOut = new BufferedOutputStream(new FileOutputStream(file));
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = zIn.read(buf)) != -1) {
                        bOut.write(buf, 0, len);
                    }
                }
            }
        } finally {
            if (bOut != null) {
                bOut.close();
            }
            if (zIn != null) {
                zIn.close();
            }
        }
    }

    private static void gzipFile(File file) {

    }

    private static void ungzipFile(File file) {

    }
}
