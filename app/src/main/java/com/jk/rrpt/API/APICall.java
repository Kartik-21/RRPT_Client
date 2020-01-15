package com.jk.rrpt.API;

import android.util.Log;

import com.jk.rrpt.MODEL.AllNoti;
import com.jk.rrpt.MODEL.AllPdf;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class APICall {

    //String book_name,String book_image_url,String book_pdf_url

    public AllPdf GetPdfData() {

        AllPdf result = new AllPdf();
        HTTPCall call = new HTTPCall();
        String url = APIRes.GET_PDF;
        Log.d("result", url);
        String output = call.GET(url);
        Log.d("result", output);
        result = new Gson().fromJson(output, AllPdf.class);
        return result;
    }


    public AllNoti GetNotiData() {

        AllNoti result = new AllNoti();
        HTTPCall call = new HTTPCall();
        String url = APIRes.GET_NOTI;
        Log.d("result", url);
        String output = call.GET(url);
        result = new Gson().fromJson(output, AllNoti.class);
        return result;
    }


    public String GetRegister(String email, String name, String id) {
        String result = "";
        HTTPCall call = new HTTPCall();

        //this name will use in php file for fetching data
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("email", email)
                .add("name", name)
                .add("id", id);
        String url = APIRes.GET_REGI;
        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }



    public AllPdf GetUserPdfData(String email) {

        AllPdf result = new AllPdf();
        HTTPCall call = new HTTPCall();
        String url = APIRes.GET_USER_PDF + "?email=" + email.trim();
        Log.d("result", url);
        String output = call.GET(url);
        Log.d("result", output);
        result = new Gson().fromJson(output, AllPdf.class);
        return result;
    }



    public String AddBookUser(String email,String id) {
        String result = "";
        HTTPCall call = new HTTPCall();

        //this name will use in php file for fetching data
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("email", email)
                .add("id",id);

        String url = APIRes.ADD_BOOK;

        RequestBody postData = formBuilder.build();
        result = call.POST(url, postData);
        Log.d("result", result);
        return result;
    }

    public String DelBookUser(String id){

        String result="";
        HTTPCall call=new HTTPCall();
        FormBody.Builder builder=new FormBody.Builder()
                .add("id",id);
        String url=APIRes.DEL_BOOK;
        RequestBody body=builder.build();
        result=call.POST(url,body);
        return result;
    }



}

