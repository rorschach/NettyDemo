package me.rorschach.nettydemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MyService extends Service {

    private MyBinder mMyBinder = new MyBinder();

    private Subscription mSubscription;

    private static final int USER_ID = 500;

    public MyService() {
    }

    @Override public void onDestroy() {
        super.onDestroy();

        if (mMyBinder != null) {
            mMyBinder.stopConnect();
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

    }

    @Override public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    class MyBinder extends Binder {

        public void initBus() {
            mSubscription = RxBus.getDefault().toObservable(Event.class)
                    .debounce(50, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Event>() {
                        @Override
                        public void call(Event event) {
                            String message = event.getName();
                            try {
                                parseMessage(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        private void parseMessage(String message) throws JSONException {
            JSONObject object = new JSONObject(message);

            int userId = object.optInt("userId");
            if (userId == USER_ID) {

                String type = object.optString("type");
                String content = object.optString("content");

                WeakReference<Context> reference = new WeakReference<Context>(MyService.this);
                NotificationUtil.showNotification(reference.get(), type, content);
            } else {
                throw new JSONException("Json message parse error!");
            }
        }

        public void startConnect() {
            Client.start();
        }

        public void stopConnect() {
            Client.stop();
        }

        public void connect() {
            Client.connect();
        }

        public void disconnect() {
            Client.disconnect();
        }

        public void send(String msg) {
            Client.send(msg);
        }
    }
}
