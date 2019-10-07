package com.tq.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SimpleService extends Service {
    public SimpleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
