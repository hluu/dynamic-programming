package org.learning.common;

public class PrintUtils {
    public static void printWithLevel(int level, String msg) {
        System.out.printf("%2d ", level);
        for (int i = 0; i < level; i++) {
            System.out.print(" ");
        }
        System.out.println(msg);
    }
}
