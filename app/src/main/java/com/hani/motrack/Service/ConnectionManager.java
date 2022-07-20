package com.hani.motrack.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ConnectionManager implements Callback {
    private IConnectionManager iConnectionManager = null;
    private OkHttpClient okHttpClient = null;

    private int requestCode;


    public interface IConnectionManager {
        public void onConnectionResponse(int requestCode, String response);

        public void onConnectionFailure(int requestCode, String response);
    }

    public ConnectionManager(IConnectionManager iConnectionManager, int requestCode) {
        okHttpClient = new OkHttpClient();
        this.iConnectionManager = iConnectionManager;
        this.requestCode = requestCode;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (iConnectionManager == null) {
            return;
        }
        iConnectionManager.onConnectionFailure(requestCode, e.toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (iConnectionManager == null) {
            return;
        }
        iConnectionManager.onConnectionResponse(requestCode, response.body().string());
    }

    public void getConnectionRequest(String url, Map<String, String> headers) {
        //Request request=new Request.Builder().url(url).build();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.get().build();
        okHttpClient.newCall(request).enqueue(this);
    }


    public void postConnectionRequestFormData(String url, Map<String, String> params, Map<String, String> headers) {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (String key : params.keySet()) {
            multipartBuilder.addFormDataPart(key, params.get(key));
        }
        RequestBody requestBody = multipartBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.post(requestBody).build();
        okHttpClient.newCall(request).enqueue(this);
    }


    public void postConnectionRequest(String url, String jsonParam, Map<String, String> headers) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonParam);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.post(requestBody).build();
        okHttpClient.newCall(request).enqueue(this);

    }

    public void putConnectionRequest(String url, String jsonParam, Map<String, String> headers) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonParam);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.put(requestBody).build();
        okHttpClient.newCall(request).enqueue(this);

    }

    public void deleteConnectionRequest(String url, String jsonParam, Map<String, String> headers) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonParam);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.delete(requestBody).build();
        okHttpClient.newCall(request).enqueue(this);

    }


    public InputStream getConnectionStream(String link, String params) throws Exception {
        try {
            if (params != null && !params.trim().isEmpty()) {
                link += params;
            }
            URL connection_url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection_url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + "utf-8");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();
            return httpURLConnection.getInputStream();
        } catch (Exception ex) {
            return null;
        }
    }

}
