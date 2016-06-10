package me.rorschach.nettydemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ServiceConnection{

    @BindView (R.id.login) Button mLogin;
    @BindView (R.id.ping) Button mPing;
    @BindView (R.id.start) Button mStart;
    @BindView (R.id.stop) Button mStop;

    private MyService.MyBinder mMyBinder;

    private Intent mIntent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Client.startConnect();
        mIntent = new Intent(this, MyService.class);
        bindService(mIntent, this, BIND_AUTO_CREATE);
        //startService(mIntent);
    }

    @Override protected void onDestroy() {

        unbindService(this);
        //stopService(mIntent);

        super.onDestroy();
    }

    @Override public void onServiceConnected(ComponentName name, IBinder service) {
        mMyBinder = ((MyService.MyBinder) service);
        mMyBinder.initBus();
        mMyBinder.startConnect();
    }

    @Override public void onServiceDisconnected(ComponentName name) {
    }

    @OnClick ({ R.id.login, R.id.ping, R.id.start, R.id.stop }) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //Client.send("{\"userId\":500,\"messageType\":\"Login\"}");

                mMyBinder.send("{\"userId\":500,\"messageType\":\"Login\"}");

                break;
            case R.id.ping:
                //Client.send("{\"userId\":500,\"messageType\":\"Ping\"}");

                mMyBinder.send("{\"userId\":500,\"messageType\":\"Ping\"}");
                break;
            case R.id.start:
                //Client.connect();

                mMyBinder.connect();

                break;
            case R.id.stop:
                //Client.disconnect();

                mMyBinder.disconnect();
                break;
        }
    }
}
