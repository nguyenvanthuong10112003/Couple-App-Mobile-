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
        return username.length() > 0;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidAuthenCode(String authenCode) {
        for (int i = 0; i < authenCode.length(); i++)
            if (authenCode.charAt(i) > '9' || authenCode.charAt(i) < '0')
                return false;
        return authenCode.length() == 6;
    }
}
