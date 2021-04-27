package com.hyd.demo.baseType;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import sun.util.locale.LocaleObjectCache;

public class BaseType {
    public static void main(String[] args) throws Exception {

        /* 基本数据类型 */
        byte aByte = 0;
        short aShort = 01;
        int anInt = 02;
        long aLong = 0;

        float aFloat = 0;
        double aDouble = 0;

        boolean aBoolean = false;
        char aChar = 0;

        String str = "";
        Object obj = new Object();

        /* ========================================================= */

        System.out.println(""+ObjectSizeCalculator.getObjectSize(obj));
        System.out.println(""+ObjectSizeCalculator.getObjectSize(aByte));
        System.out.println(ObjectSizeCalculator.getObjectSize(aShort));
        System.out.println(ObjectSizeCalculator.getObjectSize(anInt));
        System.out.println(ObjectSizeCalculator.getObjectSize(aLong));
        System.out.println(ObjectSizeCalculator.getObjectSize(aFloat));
        System.out.println(ObjectSizeCalculator.getObjectSize(aDouble));
        System.out.println(ObjectSizeCalculator.getObjectSize(aBoolean));
        System.out.println(ObjectSizeCalculator.getObjectSize(aChar));
        System.out.println(ObjectSizeCalculator.getObjectSize(str));

        long objectSize = ObjectSizeCalculator.getObjectSize(obj);
        LocaleObjectCache c;







//        obj.getClass();
//        obj.hashCode();
//        obj.equals(null);
//        obj.toString();
//        obj.wait();
//        obj.notify();
//        obj.notifyAll();



    }
}
