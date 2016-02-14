package com.josalvdel1.boxlibrary.ui.presenter;

import android.support.annotation.NonNull;

import com.josalvdel1.boxlibrary.session.SessionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginPresenter implements BasePresenter {

    private LoginView loginView;
    private SessionManager sessionManager;

    @Inject
    public LoginPresenter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void init() {
    }

    @Override
    public void resume() {
        sessionManager.storeCredentials();
        if (sessionManager.isUserLogged()) {
            loginView.navigateForward();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        loginView = null;
    }

    public void attemptLogin() {
        sessionManager.startAuthProcess();
    }

    public void setLoginView(@NonNull LoginView loginView) {
        this.loginView = loginView;
    }

    public interface LoginView {
        void showLoading();

        void hideLoading();

        void navigateForward();

        void showError(String error);
    }
}
