package com.example.ro_en.quicklib;

import android.content.SyncStatusObserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thoma on 11.03.2018.
 */

public class HttpRequestBuilder {

    public String buildHttpRequest(String ISBN) throws Exception{
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:"+ ISBN + "&callback=mycallback";
        String json;
        json = getUrlContent(url);
        System.out.println(json);
        return url;
    }

    public static String getUrlContent(String sUrl) throws Exception {
        URL url = new URL(sUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String content = "", line;
        while ((line = rd.readLine()) != null) {
            content += line + "\n";
        }
        return content;
    }
}
