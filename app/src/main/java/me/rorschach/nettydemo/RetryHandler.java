package me.rorschach.nettydemo;

import android.util.Log;
import hugo.weaving.DebugLog;

/**
 * Created by lei on 16-6-8.
 */
public class RetryHandler {

    private static final String TAG = "RetryHandler";

    private static int retryCounts = 0;
    private static long timeDelay = 0L;

    @DebugLog
    public static void resetRetryCounts() {
        retryCounts = 0;
        timeDelay = 0L;
    }

    @DebugLog
    public static long calculateRetryCounts() {
        retryCounts++;
        Log.e(TAG, "retryCount: " + retryCounts);

        if (retryCounts <= 5) {
            timeDelay = retryCounts * 5;                //seconds

        } else if (retryCounts > 5 && retryCounts < 10) {
            timeDelay = (retryCounts - 5) * 60;         //minutes

        } else {
            timeDelay = 30 * 60;                        //half hour
        }

        return timeDelay;
    }
}
