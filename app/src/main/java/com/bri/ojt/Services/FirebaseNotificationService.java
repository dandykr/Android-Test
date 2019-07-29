package com.bri.ojt.Services;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.bri.ojt.Activity.HomeActivity;
import com.bri.ojt.R;
import com.bri.ojt.Util.Consts;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import androidx.core.app.NotificationCompat;

public class FirebaseNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> notificationData = remoteMessage.getData();

            String title = notificationData.get("title");
            String body = notificationData.get("body");

            displayNotification(title, body);
        }

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Consts.getInstance().setDeviceId(token);
        Log.e("Firebase Token", token);
    }

    private void displayNotification(String title, String message) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.notification_channel);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_cashcard)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(notifSound)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setChannelId(channelId)
                .setGroup("TransferGroup");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelGroup channelGroup = new NotificationChannelGroup("TransferGroup", "TransferGroup");
            notificationManager.createNotificationChannelGroup(channelGroup);

            NotificationChannel channel = new NotificationChannel(channelId, "All Notification", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("all incoming notification are send to this channel");
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.enableLights(true);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setGroup("TransferGroup");

            notificationManager.createNotificationChannel(channel);
        }

        int notificationId = new Random().nextInt(3000);
        notificationManager.notify(notificationId, notifBuilder.build());

    }
}
