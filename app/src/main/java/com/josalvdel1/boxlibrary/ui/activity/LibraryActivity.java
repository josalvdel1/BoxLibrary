package com.josalvdel1.boxlibrary.ui.activity;

import android.os.Bundle;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.ui.fragment.LibraryFragment;

public class LibraryActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadGridFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_library;
    }

    private void loadGridFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, new LibraryFragment(), LibraryFragment.TAG)
                .commit();
    }
}


