package com.example.gk.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.gk.R;

import com.example.gk.admin.UserProfileActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebasePushNotification extends FirebaseMessagingService {


    public FirebasePushNotification() {

        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("mobile"));

        super.onMessageReceived(remoteMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String messageTitle, String messageBody, String mobileNumber) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("user_MobileNumber",mobileNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationChannel channel = new NotificationChannel(
                getString(R.string.default_notification_channel_id),
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);

        Notification.Builder notification =
                new Notification.Builder(this, getString(R.string.default_notification_channel_id))
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setSmallIcon(R.drawable.logo)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(1, notification.build());
    }

}