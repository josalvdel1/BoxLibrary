package com.josalvdel1.boxlibrary.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.session.SessionManager;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Base activity to provide DI and share common logic among all activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    SessionManager sessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BoxLibraryApplication.get(this).inject(this);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionManager.checkSessionState(this);
    }

    public abstract int getLayout();
}
