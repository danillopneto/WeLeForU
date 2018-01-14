package br.ufg.inf.weleforu;

import android.app.Application;
import android.content.Context;

/**
 * Created by danil on 1/14/2018.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getContext(){
        return App.context;
    }

    public static String getLocalMessage(int id) {
        return getContext().getString(id);
    }
}
