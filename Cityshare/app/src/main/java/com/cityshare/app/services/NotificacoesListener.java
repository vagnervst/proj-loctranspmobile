package com.cityshare.app.services;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by vagne_000 on 24/05/2017.
 */

public class NotificacoesListener extends NotificationListenerService {

    public NotificacoesListener() {

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d("REMOVED", "NOTIFICATION REMOVED");
    }
}
