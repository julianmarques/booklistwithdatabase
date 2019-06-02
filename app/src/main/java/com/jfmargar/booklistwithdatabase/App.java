package com.jfmargar.booklistwithdatabase;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Julián Marqués on 2019-06-02.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
