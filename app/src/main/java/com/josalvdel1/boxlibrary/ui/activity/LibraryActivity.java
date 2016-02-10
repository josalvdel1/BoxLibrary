package com.josalvdel1.boxlibrary.ui.activity;

import android.os.Bundle;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.ui.fragment.LibraryGridFragment;
import com.josalvdel1.boxlibrary.ui.fragment.LibraryListFragment;

public class LibraryActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        loadGridFragment();
    }

    private void loadGridFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, new LibraryGridFragment(), LibraryGridFragment.TAG)
                .commit();
    }

    private void loadListFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, new LibraryListFragment(), LibraryListFragment.TAG)
                .commit();
    }
}
