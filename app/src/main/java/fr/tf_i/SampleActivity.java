package fr.tf_i;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class SampleActivity extends Activity {

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    public static class MyHandler extends Handler {
        private final WeakReference<SampleActivity> mActivity;

        public MyHandler(SampleActivity activity) {
            mActivity = new WeakReference<SampleActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SampleActivity activity = mActivity.get();
            if (activity != null) {
                // ...
            }
        }
    }

    public final MyHandler mHandler = new MyHandler(this);

    /**
     * Instances of anonymous classes do not hold an implicit
     * reference to their outer class when they are "static".
     */
    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() { /* ... */ }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Post a message and delay its execution for 10 minutes.
        mHandler.postDelayed(sRunnable, 1000 * 60 * 10);

        // Go back to the previous Activity.
        finish();
    }
}
