package com.mvvm;

import android.app.Application;
import android.os.StrictMode;

import com.mvvm.utils.CrashHandler;

/**
 * Created by chiclaim on 2016/02/24
 */
public class MvvmApplication extends Application {

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        super.onCreate();
        CrashHandler.getInstance().init(this, "crash_log_mvvm");
    }
}
