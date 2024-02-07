/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareReferenceConfig {
    private static ShareReferenceConfig instance = null;

    private String LOGIN_ACCESS_TOKEN = "LOGIN_ACCESS_TOKEN";
    private String accessToken = "";

    private String LOGIN_TOKEN_TYPE = "LOGIN_TOKEN_TYPE";
    private String tokenType = "";

    private String DEVICE_TOKEN = "DEVICE_TOKEN";
    private String deviceToken = "";

    private String USERNAME = "USER_NAME";
    private String userName;

    private String PASSWORD = "PASS_WORD";
    private String passWord;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private ShareReferenceConfig(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(GlobalVariable.PREFERENCES, 4);
        accessToken = prefs.getString(LOGIN_ACCESS_TOKEN, "");
        tokenType = prefs.getString(LOGIN_TOKEN_TYPE, "");
        deviceToken = prefs.getString(DEVICE_TOKEN,"");
        userName = prefs.getString(USERNAME,"");
        passWord = prefs.getString(PASSWORD,"");
        settings = context.getSharedPreferences(GlobalVariable.PREFERENCES, 4);
        editor = settings.edit();

    }

    public static synchronized ShareReferenceConfig instance(Context context) {
        if (instance == null) {
            instance = new ShareReferenceConfig(context);
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        editor.putString(USERNAME,userName);
        editor.commit();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
        editor.putString(PASSWORD,passWord);
        editor.commit();
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
        editor.putString(DEVICE_TOKEN, deviceToken);
        editor.commit();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        editor.putString(LOGIN_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
        editor.putString(LOGIN_TOKEN_TYPE, tokenType);
        editor.commit();
    }


}
