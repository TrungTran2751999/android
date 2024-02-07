/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.webservice.processing;

import android.util.Log;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class WebserviceProcessing {
    public String doPostString(String url, String entityStr, String contentType,String authorization) throws ClientProtocolException, IOException {
        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpPost request = new HttpPost(url);
        StringEntity s = new StringEntity(entityStr, "UTF-8");
        if(CommonHelper.checkValidString(authorization)){

            request.setHeader("Authorization", authorization);
        }

        s.setContentEncoding("UTF-8");
        s.setContentType(contentType);
        if (GlobalVariable.LOG) {
            Log.d("XXXXXXXXX", ">>REQUEST: (" + url + ")" + entityStr);
            System.out.println(entityStr);
        }
        request.setEntity(s);

        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    //
                    // jsonString = processString(str);
                    // r = new JSONObject(jsonString);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }

    public String doGetStringPublic(String url) throws ClientProtocolException, IOException {
        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpGet request = new HttpGet(url);
        //StringEntity s = new StringEntity(entityStr, "UTF-8");

        if (GlobalVariable.LOG) {
            //Log.d("123", ">>REQUEST: (" + url + ")" + entityStr.toString());
        }
        //request.setHeader("Authorization", entityStr);

        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    public String doGetString(String url, String entityStr) throws ClientProtocolException, IOException {
        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpGet request = new HttpGet(url);
        StringEntity s = new StringEntity(entityStr, "UTF-8");
        if (GlobalVariable.LOG) {
            Log.d("123", ">>REQUEST: (" + url + ")" + entityStr.toString());
        }
        request.setHeader("Authorization", entityStr);

        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    public String doGetString2(String url, String entityStr,List<NameValuePair> params) throws ClientProtocolException, IOException {
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url+="?"+paramString;
        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpGet request = new HttpGet(url);
        StringEntity s = new StringEntity(entityStr, "UTF-8");
        if (GlobalVariable.LOG) {
            Log.d("123", ">>REQUEST: (" + url + ")" + entityStr.toString());
        }
        request.setHeader("Authorization", entityStr);

        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    public String doGetStringParameters(String url, List<NameValuePair> params) throws ClientProtocolException, IOException {

        String paramString = URLEncodedUtils.format(params, "utf-8");
        url+="?"+paramString;

        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpGet request = new HttpGet(url);


        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    public int doPostReturnStatus(String url, JSONObject c) throws ClientProtocolException, IOException {
        int status = 0;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        StringEntity s = new StringEntity(c.toString(), "UTF-8");

        request.setEntity(s);

        HttpResponse response;
        try {
            response = httpclient.execute(request);
            status = response.getStatusLine().getStatusCode();

        } catch (Exception e) {

        }
        return status;
    }
    public String doPostAPIAIIMG_NEW(String url, JSONObject c){

        HttpClient httpclient = new DefaultHttpClient();
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 10000);
        HttpConnectionParams.setSoTimeout(myParams, 10000);

        InputStream contentStream = null;
        String resultString = "";
        String json = c.toString();

        try {
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("Content-Type", "application/json");
            httppost.setHeader("Accept", "application/json");

//            StringEntity se = new StringEntity(json);
//            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//            httppost.setEntity(se);
            httppost.setEntity(new StringEntity(json.toString(), HTTP.UTF_8));


            HttpResponse response = httpclient.execute(httppost);

            // Do some checks to make sure that the request was processed properly
            Header[] headers = response.getAllHeaders();
            HttpEntity entity = response.getEntity();
            contentStream = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(contentStream,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            contentStream.close();
            resultString = sb.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultString;
    }
    public String doPostStringAPITextImg(String url, JSONObject c, String contentType, String authorization, ArrayList postParameters) throws ClientProtocolException, IOException {
        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpPost request = new HttpPost(url);
        StringEntity s = new StringEntity(c.toString(), HTTP.UTF_8);
        if(CommonHelper.checkValidString(authorization)){

            request.setHeader("Authorization", authorization);
        }
        request.setHeader("Content-Type", "application/json");
        if (GlobalVariable.LOG) {
            Log.d("123", ">>REQUEST: (" + url + ")" + c.toString());
        }
        if(postParameters != null){
            request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
        }
        request.setEntity(s);

        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    //
                    // jsonString = processString(str);
                    // r = new JSONObject(jsonString);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    public String doPostStringAPITextImg_HWC(String url, JSONObject c, String contentType, String authorization, ArrayList postParameters) throws ClientProtocolException, IOException {
        int timeoutConnection = 150000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 50000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpPost request = new HttpPost(url);
        StringEntity s = new StringEntity(c.toString(), HTTP.UTF_8);
        if(CommonHelper.checkValidString(authorization)){

            request.setHeader("Authorization", authorization);
        }
        request.setHeader("Content-Type", contentType);
        if (GlobalVariable.LOG) {
            Log.d("123", ">>REQUEST: (" + url + ")" + c.toString());
        }
        if(postParameters != null){
            request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
        }
        request.setEntity(s);

        HttpResponse response;
        String str = "";
        try {
            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    //
                    // jsonString = processString(str);
                    // r = new JSONObject(jsonString);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    @SuppressWarnings("resource")
    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }
    public String doGetStringMapParameters(String url, JSONObject params, String entityStr) throws ClientProtocolException, IOException {
        String str = "";
        try {

            int timeoutConnection = 150000;
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 50000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            HttpClient httpclient = new DefaultHttpClient(httpParameters);

//        HttpGet request = new HttpGet(url);
            HttpGetWithEntity request = new HttpGetWithEntity(url);
            JSONObject holder = params;//getJsonObjectFromMap(params);
            StringEntity se = new StringEntity(holder.toString(), HTTP.UTF_8);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);

//        StringEntity s = new StringEntity(entityStr, "UTF-8");
            if (GlobalVariable.LOG) {
                Log.d("123", ">>REQUEST: (" + url + ")" + entityStr.toString());
            }
            request.setHeader("Authorization", entityStr);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response;

            synchronized (request) {
                response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    str = getResponseText(instream);
                    if (GlobalVariable.LOG) {
                        Log.d("123", ">>RESPONSE: " + str);
                    }
                    instream.close();
                }
            }

        } catch (Exception e) {
            if (GlobalVariable.LOG) {
                Log.d("123", "exception: " + e.getMessage());
            }
        } finally {
        }
        return str;
    }
    class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
        public final static String METHOD_NAME = "GET";
        public HttpGetWithEntity(String uri) throws URISyntaxException {
            this.setURI(new URI(uri));
        }

        public HttpGetWithEntity(URI uri){
            this.setURI(uri);
        }
        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }
}
