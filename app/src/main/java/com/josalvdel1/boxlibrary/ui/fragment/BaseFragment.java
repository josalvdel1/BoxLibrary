package com.josalvdel1.boxlibrary.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.josalvdel1.boxlibrary.LibraryBoxApplication;

import butterknife.ButterKnife;

/**
 * Base fragment to provide DI, VI and share common logic among all fragments
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LibraryBoxApplication.get(context).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        loadView();
        return view;

    }

    public abstract int getLayout();

    public abstract void loadView();
}

