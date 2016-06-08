package me.rorschach.nettydemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {


    private MyBinder mMyBinder = new MyBinder();

    public MyService() {
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (mMyBinder != null) {
            mMyBinder.disconnectFromServer();
        }
    }

    @Override public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    class MyBinder extends Binder {

        public void init() {
            Client.init();
        }

        public void connectToServer() {
            Client.connect();
        }

        public void disconnectFromServer() {
            Client.disconnect();
        }
    }
}
