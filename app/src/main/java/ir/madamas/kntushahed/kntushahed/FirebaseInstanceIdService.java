package ir.madamas.kntushahed.kntushahed;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by NFP_7037 on 7/2/2017.
 */

public class FirebaseInstanceIdService extends FirebaseMessagingService {

    private static final String TAG = "service class";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0){
            //Log.d(TAG, "Data: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null){
            //Log.d(TAG, "Message body: " + remoteMessage.getNotification().getBody());
            sendNotofication(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification());
        }
    }

    private void sendNotofication(String title, RemoteMessage.Notification notification) {

        Intent intent;
        String body = notification.getBody();

        NotificationCompat.Builder builder;
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent;

        Toast.makeText(getApplicationContext(), "title: " + title, Toast.LENGTH_SHORT).show();
        switch (title.toString().trim()){
            case "update":
                intent = new Intent(getApplicationContext(), MainActivity.class);
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setContentTitle("به روز رسانی برنامه")
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(notificationSound)
                        .setContentIntent(pendingIntent);
                break;

            case "booklet":
                intent = new Intent(getApplicationContext(), bookletActivity.class);
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setContentTitle("جزوه جدید به برنامه اضافه شد!")
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(notificationSound)
                        .setContentIntent(pendingIntent);
                break;

            case "course":
                intent = new Intent(getApplicationContext(), MainActivity.class);
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,intent, PendingIntent.FLAG_ONE_SHOT);
                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setContentTitle("درس های اضافه شده به برنامه را ببینید!")
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(notificationSound)
                        .setContentIntent(pendingIntent);
                break;

            default:
                //nothing to do yet
                intent = new Intent(getApplicationContext(), bookletActivity.class);
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,intent, PendingIntent.FLAG_ONE_SHOT);

                builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_logo)
                        .setContentTitle("جزوه جدید به برنامه اضافه شد!")
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(notificationSound)
                        .setContentIntent(pendingIntent);
                break;
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
