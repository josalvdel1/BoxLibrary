package com.josalvdel1.boxlibrary.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.josalvdel1.boxlibrary.LibraryBoxApplication;

import butterknife.ButterKnife;

/**
 * Base activity to provide DI and share common logic among all activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LibraryBoxApplication.get(this).inject(this);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    public abstract int getLayout();
}
