package me.rorschach.nettydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView (R.id.login) Button mLogin;
    @BindView (R.id.ping) Button mPing;
    @BindView (R.id.start) Button mStart;
    @BindView (R.id.stop) Button mStop;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Client.init();
    }

    @OnClick ({ R.id.login, R.id.ping, R.id.start, R.id.stop }) public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                Client.send("{\"userId\":500,\"messageType\":\"Login\"}");
                break;
            case R.id.ping:
                Client.send("{\"userId\":500,\"messageType\":\"Ping\"}");
                break;
            case R.id.start:
                Client.connect();
                break;
            case R.id.stop:
                Client.disconnect();
                break;
        }
    }
}
