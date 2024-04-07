package com.example.myapplication.helper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageHelper {
    public static boolean isImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            return contentType != null && contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
