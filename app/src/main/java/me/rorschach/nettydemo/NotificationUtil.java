package me.rorschach.nettydemo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by lei on 16-6-8.
 */
public class NotificationUtil {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void showNotification(Context context, String title, String content) {

        NotificationManager notificationManager =
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content);

        builder.setVibrate(new long[]{500, 0, 500});
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        Notification notification = builder.build();
        notificationManager.notify(0x16, notification);
    }

}
