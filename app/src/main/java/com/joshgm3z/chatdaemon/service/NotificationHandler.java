package com.joshgm3z.chatdaemon.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.DbHandler;
import com.joshgm3z.chatdaemon.common.data.Chat;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.chat.ChatActivity;

import java.util.List;

import static com.joshgm3z.chatdaemon.presentation.chat.ChatActivity.USER_ID;

public class NotificationHandler {

    private static final String CHANNEL_ID = "chat-daemon-channel-id";

    private static final CharSequence CHANNEL_NAME = "New messages";

    private static final String CHANNEL_DESC = "Notification about new messages";

    private Context mContext;

    private int notificationId = 0;

    public NotificationHandler(Context context) {
        mContext = context;
        createNotificationChannel();
    }

    public void notifyNewMessages(List<Chat> chatList) {
        Logger.log(Log.INFO, "Checking messages for notifying");
        for (Chat chat : chatList) {
            Logger.log(Log.INFO, "chat = [" + chat + "]");
            if (chat.getFromUser() != null && chat.getStatus() == Chat.Status.SENT) {
                notifyNewMessage(chat);
            } else {
                Logger.log(Log.WARN, "chat.getFromUser is null or chat is already seen");
            }
        }
    }

    public void notifyNewMessage(Chat chat) {
        Logger.entryLog();
        String title = chat.getFromUser().getUsername();
        String subtitle = chat.getMessage();
        sendNotification(chat.getFromUser().getId(), title, subtitle);
        Logger.exitLog();
    }


    private void sendNotification(String fromUserId, String title, String subtitle) {
        Logger.log(Log.INFO, "fromUserId = [" + fromUserId + "], title = [" + title + "], subtitle = [" + subtitle + "]");
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(mContext, ChatActivity.class);
        intent.putExtra(USER_ID, fromUserId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(subtitle)
                .setContentText(subtitle)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

        notificationManager.notify(notificationId++, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = CHANNEL_NAME;
        String description = CHANNEL_DESC;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}
