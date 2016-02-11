package com.josalvdel1.boxlibrary.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.ui.LibraryAdapter;
import com.josalvdel1.boxlibrary.ui.presenter.LibraryPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class LibraryFragment extends BaseFragment implements LibraryPresenter.LibraryView {

    public static final String TAG = "grid_fragment";
    private static final int SPAN_COUNT = 2;

    @Inject
    LibraryPresenter libraryPresenter;
    @Inject
    LibraryAdapter libraryAdapter;

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;


    @Override
    public int getLayout() {
        return R.layout.fragment_library;
    }


    @Override
    public void loadView() {
        loadGridView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        libraryPresenter.setLibraryView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        libraryPresenter.init();
        libraryPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        libraryPresenter.pause();
    }

    private void loadGridView() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL);
        rvBooks.setLayoutManager(layoutManager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showBooks(List<Book> books) {
        libraryAdapter.setBooks(books);
        rvBooks.setAdapter(libraryAdapter);
        libraryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }
}
