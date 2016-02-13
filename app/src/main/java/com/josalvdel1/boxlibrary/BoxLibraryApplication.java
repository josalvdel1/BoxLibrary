package com.josalvdel1.boxlibrary;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public class BoxLibraryApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(new AppModule(this));
        objectGraph.inject(this);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public static BoxLibraryApplication get(Context context) {
        return (BoxLibraryApplication)context.getApplicationContext();
    }
}
