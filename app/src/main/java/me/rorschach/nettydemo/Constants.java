package me.rorschach.nettydemo;

/**
 * Created by lei on 16-6-6.
 */
public interface Constants {

    int MESSAGE_READ = 0x01;
    int MESSAGE_SEND = 0x02;
    int CONNECT_SUCCESS = 0x03;
    int CONNECT_FAILED = 0x04;
    int MY_HANDLER = 0x08;
    int MY_CHAT = 0x16;

    String HOST = "120.25.167.157";
    int PORT = 19080;

    int RECONNECT_DELAY = 10;

    int BYTE_SIZE = 108;

}
