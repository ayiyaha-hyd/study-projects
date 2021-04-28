package com.hyd.demo.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Java 输入输出流
 *
 */
public class InputOutputDemo {
    public static void main(String[] args) throws Exception{
        readFile5();
        byte[] buf = null;
        char[] chars = null;
        String s = null;
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        /* ------------------------------------------------------ */
        /*
        字节输入流 InputStream

        InputStream就是Java标准库提供的最基本的输入流。它位于java.io这个包里。java.io包提供了所有同步IO的功能。
        InputStream并不是一个接口，而是一个抽象类，它是所有输入流的超类。这个抽象类定义的一个最重要的方法就是int read()。
        这个方法会读取输入流的下一个字节，并返回字节表示的int值（0~255）。如果已读到末尾，返回-1表示不能继续读取了。

        类似文件、网络端口这些资源，都是由操作系统统一管理的。操作文件流要注意及时释放对应的底层资源。
        需要用try ... finally来保证InputStream在无论是否发生IO错误的时候都能够正确地关闭：
        如果对象实现了java.lang.AutoCloseable接口，可以使用try(resource = ...)，会自动加上finally语句并调用close()方法。
        InputStream和OutputStream都实现了这个接口，因此，都可以用在try(resource)中。
         */

        /* 读取文件，并简单处理异常示例 */
        readFile1();

        /*
        缓冲

        在读取流的时候，一次读取一个字节并不是最高效的方法。
        对于文件和网络流来说，利用缓冲区一次性读取多个字节效率往往要高很多。
        InputStream提供了两个重载方法来支持读取多个字节：
        int read(byte[] b)：读取若干字节并填充到byte[]数组，返回读取的字节数
        int read(byte[] b, int off, int len)：指定byte[]数组的偏移量和最大填充数
        利用上述方法一次读取多个字节时，需要先定义一个byte[]数组作为缓冲区，
        read()方法会尽可能多地读取字节到缓冲区， 但不会超过缓冲区的大小。
        read()方法的返回值不再是字节的int值，而是返回实际读取了多少个字节。如果返回-1，表示没有更多的数据了。
         */

        /* 缓冲区一次读取多个字节 示例 */
        readFile2();

        /*
        阻塞 BIO
        传统的java.io包，它是基于流模型实现，交互的方式是同步、阻塞方式。
        在调用InputStream的read()方法读取数据时，read()方法是阻塞（Blocking）的。对于以下代码：
            int n;
            n = input.read(); // 必须等待read()方法返回才能执行下一行代码
            int m = n;
        执行到第二行代码时，必须等read()方法返回后才能继续。因为读取IO流相比执行普通代码，速度会慢很多，
        因此，无法确定read()方法调用到底要花费多长时间。
         */


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
        /*
        字节输出流 InputStream

        和InputStream类似，OutputStream也是抽象类，它是所有输出流的超类。这个抽象类定义的一个最重要的方法就是void write(int b)
        这个方法会写入一个字节到输出流。要注意的是，虽然传入的是int参数，但只会写入一个字节，即只写入int最低8位表示字节的部分（相当于b & 0xff）。

        和InputStream类似，OutputStream也提供了close()方法关闭输出流，以便释放系统资源。要特别注意：OutputStream还提供了一个flush()方法，它的目的是将缓冲区的内容真正输出到目的地。
        为什么要有flush()？因为向磁盘、网络写入数据的时候，出于效率的考虑，操作系统并不是输出一个字节就立刻写入到文件或者发送到网络，而是把输出的字节先放到内存的一个缓冲区里（本质上就是一个byte[]数组），等到缓冲区写满了，再一次性写入文件或者网络。对于很多IO设备来说，一次写一个字节和一次写1000个字节，花费的时间几乎是完全一样的，所以OutputStream有个flush()方法，能强制把缓冲区内容输出。

        通常情况下，我们不需要调用这个flush()方法，因为缓冲区写满了OutputStream会自动调用它，并且，在调用close()方法关闭OutputStream之前，也会自动调用flush()方法。
        但是，在某些情况下，我们必须手动调用flush()方法。例如写入网络流是先写入内存缓冲区，等缓冲区满了才会一次性发送到网络。如果缓冲区大小是4K，则发送方要敲几千个字符后，操作系统才会把缓冲区的内容发送出去，这个时候，接收方会一次性收到大量消息。解决办法就是每输入一句话后，立刻调用flush()，不管当前缓冲区是否已满，强迫操作系统把缓冲区的内容立刻发送出去。
        写文件异常（磁盘已满，无权限写入）

        和InputStream一样，OutputStream的write()方法也是阻塞的。
        */

        /* 写文件示例 */
        writeFile1();




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

        /*
        扩展：
        BufferOutputStream相关概念（其实是考缓冲区是否需要刷新之类的问题）
        BufferOutputStream里的flush()方法是做什么的？
            flush把缓冲区里的数据写入文件，并刷新缓冲区。
        BufferOutputStream调用close后，会触发flush()来刷新缓冲区吗？
            close关闭此输出流并释放与此相关联的任何系统资源， 会调用flush，除了flushBuffer，还会调用父类的flush。
        BufferOutputStream调用close可能会丢数据吗？
            不会丢数据，因为上面这条原因。
        BufferOutputStream多次调用close会报错吗？
            多次调用不会报错。
         */



        /* ------------------------------------------------------ */
        /*
        字符输入流 Reader
        Reader/Writer提供兼容Unicode、面向字符的IO功能，为了国际化。
        Reader是Java的IO库提供的另一个输入流接口。
        和InputStream的区别是，InputStream是一个字节流，即以byte为单位读取，而Reader是一个字符流，即以char为单位读取：
        InputStream：字节流，以byte为单位；读取字节（-1，0~255）：int read()；
        Reader：字符流，以char为单位；读取字符（-1，0~65535）：int read()

        CharArrayReader可以在内存中模拟一个Reader，它的作用实际上是把一个char[]数组变成一个Reader，这和ByteArrayInputStream非常类似：
        StringReader可以直接把String作为数据源，它和CharArrayReader几乎一样：除了特殊的CharArrayReader和StringReader，普通的Reader实际上是基于InputStream构造的，因为Reader需要从InputStream中读入字节流（byte），然后，根据编码设置，再转换为char就可以实现字符流。如果我们查看FileReader的源码，它在内部实际上持有一个FileInputStream。
        Reader本质上是一个基于InputStream的byte到char的转换器，那么，如果我们已经有一个InputStream，想把它转换为Reader，是完全可行的。InputStreamReader就是这样一个转换器，它可以把任何InputStream转换为Reader。

        */
        Reader reader = new FileReader(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringReader stringReader = new StringReader(s);
        CharArrayReader charArrayReader = new CharArrayReader(chars);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        /* 读文件示例 */
        readFile3();
        /* 读文件示例（缓冲区） */
        readFile4();
        /* 读文件示例(InputStreamReader转换器) */
        readFile5();




        /* ------------------------------------------------------ */
        /*
        字符输出流 Writer

        */

        Writer fileWriter = new FileWriter(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        StringWriter stringWriter = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);






        /*
        序列化问题

        Q： 对某对象进行序列化时， 如何让里面某个敏感成员不被序列化？
        A:
            方法一：可使用transient关键字处理那个敏感成员
            方法二：可以通过覆盖Serializable接口的writeObject和readObject来实现序列化，  但是方法签名必须是private void writeObject(ObjetOutputStream stream) throw IOException;
            方法三： 实现Externalizable接口，可自定义实现writeExternal以及readExternal方法
        Q： Externalizable和Serializable哪个快？
        A： Externalizable更快。
        Q： Externalizable需要产生序列化ID吗？
        A： 采用Externalizable无需产生序列化ID（serialVersionUID）~而Serializable接口则需要
        */
    }

    /**
     * 读取文件
     * @throws IOException
     */
    private static void readFile1() throws IOException{
        String filePath = "C:\\projects\\study-projects\\java-demo\\src\\main\\resources\\temp\\1.txt";
        /* try(resource) 写法 */
        try(FileInputStream input = new FileInputStream(filePath)) {
            int n;
            while ((n=input.read())!=-1){
                System.out.print((char) n);
            }
        }
    }

    /**
     * 利用缓冲区读文件
     * @throws IOException
     */
    private static void readFile2() throws IOException{
        String filePath = "C:\\projects\\study-projects\\java-demo\\src\\main\\resources\\temp\\1.txt";
        /* 传统写法 try ... finally */
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath);
            byte[] buffer = new byte[2];
            int n;
            while ((n=input.read(buffer))!=-1){
                System.out.print("read "+n+" bytes;\n");
            }
        }finally {
            if(input!=null){
                input.close();
            }
        }
    }

    /**
     * 写文件
     * @throws IOException
     */
    private static void writeFile1()throws IOException{
        try(FileOutputStream output = new FileOutputStream("2-out.txt");){
            String str = "hello，编程";
            output.write(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * 读取文件（字符流）
     * @throws IOException
     */
    private static void readFile3()throws IOException{
        String filePath = "C:\\projects\\study-projects\\java-demo\\src\\main\\resources\\temp\\短歌行.txt";
        try(FileReader reader = new FileReader(filePath);){
            int n;
            while ((n=reader.read())!=-1){
                System.out.print((char)n);
            }

        }
    }

    /**
     * 读取文件（字符流，利用缓冲区）
     * @throws IOException
     */
    private static void readFile4()throws IOException{
        String filePath = "C:\\projects\\study-projects\\java-demo\\src\\main\\resources\\temp\\短歌行.txt";
        try(FileReader reader = new FileReader(filePath);){
            char[] buffer = new char[16];
            int n;
            while ((n=reader.read(buffer))!=-1){
                System.out.println("read "+n+" chars.");
            }
        }
    }

    private static void readFile5()throws IOException{
        String filePath = "C:\\projects\\study-projects\\java-demo\\src\\main\\resources\\temp\\短歌行.txt";
        InputStream in;
        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath));){
            int n;
            while ((n=reader.read())!=-1){
                System.out.print((char)n);
            }
        }
    }


}
