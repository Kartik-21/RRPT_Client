package com.jk.rrpt.API;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPCall {

    public String GET(String url) {

        String result = null;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
            Log.d("Result" , result);
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
            Log.d("Result-Exp" , result);
        }

        return result;

    }

    public String POST(String url, RequestBody postData) {
        String result = null;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(postData)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }


}
