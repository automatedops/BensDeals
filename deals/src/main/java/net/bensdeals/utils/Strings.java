package net.bensdeals.utils;

public class Strings {
    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }

        if (s.length() == 0) {
            return true;
        }

        if (s.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
