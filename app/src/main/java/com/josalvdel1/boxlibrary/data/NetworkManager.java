package com.josalvdel1.boxlibrary.data;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.josalvdel1.boxlibrary.BoxLibraryApplication;

import javax.inject.Inject;

public class NetworkManager {

    private Application application;

    @Inject
    public NetworkManager(BoxLibraryApplication application) {
        this.application = application;
    }

    public boolean hasNetworkConnection() {
        ConnectivityManager connectivity = (ConnectivityManager) application.getSystemService(application.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
