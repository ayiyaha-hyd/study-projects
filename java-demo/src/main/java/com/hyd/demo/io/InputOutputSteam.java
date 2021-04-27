package com.hyd.demo.io;

import java.io.*;

/**
 * Java 输入输出流
 */
public class InputOutputSteam {
    public static void main(String[] args) throws Exception{
        byte buf[] = null;
        char[] chars = null;
        String s = null;
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        /* ------------------------------------------------------ */
        /* 字节输入流 InputStream */



        /* 字节数组char[] 作为输入源 */
        InputStream byteArrayInputStream = new ByteArrayInputStream(buf);
        /* 字符串作为输入源。此类无法正确将字符转换为字节。 从JDK 1.1开始，从字符串创建流的首选方法是通过StringReader类 */
        InputStream stringBufferInputStream = new StringBufferInputStream(s);
        /* 文件作为输入 */
        InputStream fileInputStream = new FileInputStream(file);
        /* 用于多线程之间管道通信的输入源 */
        InputStream pipeInputStream = new PipedInputStream();

        /* 装饰后，不仅可读字符串，还可读取例如int、long等java基本类型，继承至FilterInputStream父类 */
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        /* 装饰后，支持分批缓冲读取，继承至FilterInputStream父类 */
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);


        /* ------------------------------------------------------ */
        /* 字节输出流 InputStream */

        /*
        BufferOutputStream和ByteArrayOutputStream 区别
        ByteArrayOutputStream是将数据全部缓存到自身，然后一次性输出；而BufferedOutputStream是缓存一部分后，一次一次的输出
        当你资源不足够用时,选择BufferedOutputStream是最佳的选择, 当你选择快速完成一个作业时,可以选择ByteArrayOutputStream之类的输出流
         */

        /* 输出到缓冲区 */
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /* 写到文件 */
        OutputStream fileOutputStream = new FileOutputStream(file);
        /* 写入管道 */
        OutputStream pipedOutputStream = new PipedOutputStream();
        /* 过滤字节输出流，为字节输出处理流提供扩展。 常用的子类有：BufferedOutputStream, DataOutputStream, PrintStream */
        OutputStream filterOutputStream = new FilterOutputStream(outputStream);
        OutputStream dataOutputStream = new DataOutputStream(outputStream);
        /* 缓冲输出流 */
        OutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        OutputStream printStream = new PrintStream(outputStream);





        /* ------------------------------------------------------ */
        /* 字符节输出流 InputStream */

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringReader stringReader = new StringReader(s);
        CharArrayReader charArrayReader = new CharArrayReader(chars);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

























        /* ------------------------------------------------------ */
        /* 字节输出流 InputStream */
    }


}
