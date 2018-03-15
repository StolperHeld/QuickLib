package com.example.ro_en.quicklib;

import android.content.SyncStatusObserver;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by thoma on 11.03.2018.
 */

public class HttpRequestBuilder extends AsyncTask<String, Void, String> {

    public String buildHttpRequest(String ISBN) throws Exception{
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:"+ISBN +"&jscmd=data&format=json";
        System.out.println(url);
        String json;
        json = doInBackground(url) ;
        System.out.println(json);
        return url;
    }

    @Override
    protected String doInBackground(String... sUrl){
        String content = "", line;
        try{
            URL url = new URL(sUrl[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return content;
    }
}
