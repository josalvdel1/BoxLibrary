package com.josalvdel1.boxlibrary.session;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dropbox.core.android.Auth;
import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.BuildConfig;
import com.josalvdel1.boxlibrary.ui.activity.LoginActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private String token_key = "access-token";
    private SharedPreferences sharePreferences;
    private Application application;

    @Inject
    public SessionManager(BoxLibraryApplication application, SharedPreferences sharedPreferences) {
        this.application = application;
        this.sharePreferences = sharedPreferences;
    }

    public void checkSessionState(Activity activity) {
        if (!isUserLogged()) {
            forceLogin(activity);
            activity.finish();
        }
    }

    public boolean isUserLogged() {
        return sharePreferences.getString(token_key, null) != null;
    }

    public void startAuthProcess() {
        Auth.startOAuth2Authentication(application, BuildConfig.APP_KEY);
    }

    private void forceLogin(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    public void storeCredentials() {
        if (!isUserLogged()) {
            String token = Auth.getOAuth2Token();
            sharePreferences.edit().putString(token_key, token).apply();
        }
    }
}
