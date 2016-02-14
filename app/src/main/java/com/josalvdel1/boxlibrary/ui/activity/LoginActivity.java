package com.josalvdel1.boxlibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.ui.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView {

    @Inject
    LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoxLibraryApplication.get(this).inject(this);
        setContentView(getLayout());
        ButterKnife.bind(this);
        loginPresenter.init();
        loginPresenter.setLoginView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.resume();
    }

    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.destroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void navigateForward() {
        startActivity(new Intent(this, LibraryActivity.class));
        finish();
    }

    @Override
    public void showError(String error) {

    }

    @OnClick(R.id.login_btn)
    public void onLogingClicked() {
        loginPresenter.attemptLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
