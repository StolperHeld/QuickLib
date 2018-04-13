package com.example.ro_en.quicklib.utils;


import android.os.AsyncTask;
import com.example.ro_en.quicklib.model.Book;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestBuilder extends AsyncTask<String, Book, String> {

    public AsyncResponse delegate = null;//Call back interface

    public HttpRequestBuilder(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }


    //Background Network Access. Return JSON-String
    @Override
    protected String doInBackground(String... ISBN){
        String sUrl = "http://openlibrary.org/api/books?bibkeys=ISBN:" + ISBN[0] + "&jscmd=data&format=json";
        System.out.println("URL " + sUrl);
        String content = "", line;
        try{
            URL url = new URL(sUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
    @Override
    protected void onPostExecute(String content){
        try {
            Book book = JsonToBook.jsontoBook(content);
            delegate.processFinish(book);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
