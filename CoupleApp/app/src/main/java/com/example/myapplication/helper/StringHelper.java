package com.example.myapplication.helper;

public class StringHelper {
    public static boolean isValidUsername(String username) {
        for (int i = 0; i < username.length(); i++)
            if ((username.charAt(i) >= '0' && username.charAt(i) <= '9') ||
                    (username.charAt(i) >= 'A' && username.charAt(i) <= 'Z') ||
                    (username.charAt(i) >= 'a' && username.charAt(i) <= 'z'))
                continue;
            else
                return false;
        return true;
    }
}
