package com.huewaco.cskh.FCM;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.NotificationCompat;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.huewaco.cskh.activity.ASplash1;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;

import me.leolin.shortcutbadger.ShortcutBadger;



public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        if(remoteMessage.getNotification()!=null)
//        {
//            hienThiThongBao(remoteMessage.getNotification().getBody());
//        }
        String body = "";
        String title = "";
        int badgeCount = 0;
        try{
            body = remoteMessage.getData().get("body");
            title = remoteMessage.getData().get("title");
            badgeCount = Integer.valueOf(remoteMessage.getData().get("unreadnumber")) ;
        }catch(Exception e) {

        }finally {

        }

        hienThiThongBao(body,title);
        showBadgeNumber(badgeCount);

    }
    protected void showBadgeNumber(int badgeCount){
        if(badgeCount < 0 ){
            badgeCount = 0;
        }
        boolean success = ShortcutBadger.applyCount(getApplicationContext(), badgeCount);

        //Toast.makeText(getApplicationContext(), "Set count=" + badgeCount + ", success=" + success, Toast.LENGTH_SHORT).show();
    }
    private void hienThiThongBao(String body,String title) {
        try{
            Intent intent=new Intent(this,ASplash1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Bundle extra = new Bundle();
            extra.putString("frg","ok");
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this, GlobalVariable.MY_CHANEL_ID)
                    //.setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setExtras(extra)
                    .setContentIntent(pendingIntent);
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //Ap dụng cho SDK API >= 26
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(GlobalVariable.MY_CHANEL_ID,
                        "Huewaco", importance);
                manager.createNotificationChannel(mChannel);
            }
            manager.notify(0,builder.build());

        }catch (Exception e){

        }

    }
    private void hienThiThongBao(String body) {
        hienThiThongBao(body,"HueWaco Thông Báo");
    }
}
