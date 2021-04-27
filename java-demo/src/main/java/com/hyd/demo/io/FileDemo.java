package com.hyd.demo.io;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作
 */
public class FileDemo {
    public static void main(String[] args) throws Exception{
        String pathname = "C:\\projects\\study-projects\\java-demo\\src\\main\\resources\\temp\\短歌行.txt";
        /* File对象有一个静态变量用于表示当前平台的系统分隔符 */
        String pathname2 = "C:"+File.separator+"projects"+File.separator+"study-projects"+File.separator+"java-demo"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"temp"+File.separator+"短歌行.txt";

        /* File对象既可以表示文件，也可以表示目录 */
        File file = new File(pathname2);
        FileReader fileReader = new FileReader(file);
        int c = fileReader.read();
        while (c!=-1){
            char ch = (char)c;
            System.out.print(ch);
            c = fileReader.read();
        }

        /* 文件路径 */


        /*
        File对象有3种形式表示的路径，一种是getPath()，返回构造方法传入的路径，
        一种是getAbsolutePath()，返回绝对路径，
        一种是getCanonicalPath，它和绝对路径类似，但是返回的是规范路径。
        规范路径就是把.和..转换成标准的绝对路径后的路径
         */
        File file2 = new File("..");
        System.out.println(file2.getName());
        System.out.println(file2.getPath());
        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.getCanonicalPath());

        /* 获取当前类所在路径 */
        File f = new File(FileDemo.class.getResource("/").getPath());
        System.out.println(f);
        /* 获取当前类的所在工程路径 */
        f = new File(FileDemo.class.getResource("").getPath());
        System.out.println(f);
        /* 获取当前工程编译后classes目录下文件 */
        URL resource = FileDemo.class.getClassLoader().getResource("\\temp\\短歌行.txt");
        System.out.println(resource);
        /* 获取当前工程路径 */
        System.out.println(System.getProperty("user.dir"));
        /* 获取当前工程java类路径 */
        System.out.println( System.getProperty("java.class.path"));

        /* 文件和目录 */

        /* 判断当前对象是否是一个已经存在的文件 */
        file.isFile();
        /* 判断当前对象是否是一个已经存在的目录 */
        file.isDirectory();
        /* 判断文件或目录是否存在 两种方法*/
        file.exists();
        Files.exists(Paths.get(file.getPath()));

        /* 创建和删除文件 */

        /* 判断当前file如果不存在，则创建新文件，根据返回值判断是否创建成功 */
        boolean newFile = file.createNewFile();
        /* 文件删除 */
        file2.delete();
        /* 内存中临时文件创建 */
        File tempFile = File.createTempFile("temp", ".txt");
        /* JVM退出时自动删除该文件 */
        tempFile.deleteOnExit();
        /* 创建目录 */
        File file4 = new File("fileDemoTest");
        /* 创建当前File对象表示的目录 */
        file4.mkdir();
        /* 创建当前File对象表示的目录，并在必要时将不存在的父目录也创建出来 */
        file4.mkdirs();
        /* 删除当前File对象表示的目录，当前目录必须为空才能删除成功 */
        file4.delete();

        /* 遍历文件和目录 */

        File dir = new File("C:\\Windows");
        /* 列出目录下的文件和子目录名 */
        String[] list = dir.list();
        /* 列出目录下的文件和子目录名 */
        File[] files = dir.listFiles(new FilenameFilter() {//过滤器，仅返回.exe文件
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".exe");//返回true表示接受该文件
            }
        });

    }

    /**
     * 删除目录（或文件）
     * 如果是文件或者空目录，可以直接删除
     * 如果目录中有文件或者子目录，则必须递归删除
     * @param dir
     * @return
     */
    private static boolean delDir(File dir){
        if(dir.isDirectory()){
            String[] children = dir.list();
            //递归删除目录下的子目录
            for(String child:children){
                boolean success = delDir(new File(dir,child));
                if(!success){
                    return false;
                }
            }
        }
        //此时目录为空，可以删除
        return dir.delete();
    }

    /**
     * 判断文件是否存在（方法1）
     * @param filePath
     * @return
     */
    private static boolean existsFile1(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            return true;
        }
        return false;
    }

    /**
     * 判断文件是否存在（方法2）
     * @param filePath
     * @return
     */
    private static boolean existsFile2(String filePath){
        Path path = Paths.get(filePath);
        if(Files.exists(path)){
            return true;
        }
        return false;
    }
}
