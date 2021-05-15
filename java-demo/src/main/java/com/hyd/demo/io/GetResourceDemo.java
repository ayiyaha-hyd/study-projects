package com.hyd.demo.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取java配置资源
 */
public class GetResourceDemo {
    public static void main(String[] args) throws Exception {
        GetResourceDemo demo = new GetResourceDemo();
        demo.test();
    }
    public void test() throws IOException {
        InputStream input = null;
        try {
            /*
            读取classpath资源

            在classpath中的资源文件，路径总是以／开头，我们先获取当前的Class对象，
            然后调用getResourceAsStream()就可以直接从classpath读取任意的资源文件
             */
            input =   getClass().getResourceAsStream("/temp/4-getResourceDemo.properties");
            if(input==null){
                System.out.println("资源不存在");
            }
            int n;
            while ((n=input.read())!=-1){
                System.out.print((char) n);
            }
            System.out.println("\n---");



            /*
            读取Java的配置文件（通过Properties）
             */
            Properties props = new Properties();
            input = getClass().getResourceAsStream("/temp/5-properties-test.properties");
            //加载配置文件
            props.load(input);
            String name = props.getProperty("name");
            String age = props.getProperty("age");
            String pet = props.getProperty("pet");
            System.out.println("name:"+name+"\nage:"+age+"\npet:"+pet);


        }finally {
            if(input!=null){
                input.close();
            }
        }
    }
}
