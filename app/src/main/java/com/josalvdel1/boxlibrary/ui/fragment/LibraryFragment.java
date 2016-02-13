package com.josalvdel1.boxlibrary.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.ui.activity.BookDetailActivity;
import com.josalvdel1.boxlibrary.ui.adapter;
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
    adapter.LibraryAdapter libraryAdapter;

    @Bind(R.id.rv_books)
    RecyclerView rvBooks;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public int getLayout() {
        return R.layout.fragment_library;
    }


    @Override
    public void loadView() {
        loadGridView();
        toolbar.setTitle(R.string.activity_library_title);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        libraryPresenter.setLibraryView(this);
        rvBooks.setAdapter(libraryAdapter);
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
    public void showBooks(List<Book> eBooks) {
        libraryAdapter.setBooks(eBooks);
    }

    @Override
    public void showNewBook(Book book) {
        libraryAdapter.addBook(book);
    }

    @Override
    public void onBookClicked(Book book) {
        startActivity(BookDetailActivity.getLaunchIntent(getActivity(), book.getFilePath()));
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }
}
