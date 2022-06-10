package com.doromv.utils;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-10-11:14
 */
public class SleepUtils {
    public static void sleep(int second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
