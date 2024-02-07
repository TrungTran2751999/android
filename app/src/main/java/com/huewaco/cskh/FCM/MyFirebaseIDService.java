package com.huewaco.cskh.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//
//public class MyFirebaseIDService extends FirebaseInstanceIdService {
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String token= FirebaseInstanceId.getInstance().getToken();
//        Log.d("MyFirebaseIDService----------------------",token);
//        luuTokenVaoCSDLRieng(token);
//
//    }
//
//    private void luuTokenVaoCSDLRieng(String token) {
//       // new FireBaseIDTask().execute(token);
//    }
//}
//import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseIDService extends FirebaseMessagingService {
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String token= FirebaseInstanceId.getInstance().getToken();
//        Log.d("MyFirebaseIDService----------------------",token);
//        luuTokenVaoCSDLRieng(token);
//
//    }

    private void luuTokenVaoCSDLRieng(String token) {
        // new FireBaseIDTask().execute(token);
    }
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("NEW_TOKEN",token);
        luuTokenVaoCSDLRieng(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

}