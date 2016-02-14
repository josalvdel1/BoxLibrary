package com.josalvdel1.boxlibrary.ui.fragment;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.adapter.LibraryAdapter;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.ui.activity.BookDetailActivity;
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

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private boolean viewShowingAsGrid;

    @Override
    public int getLayout() {
        return R.layout.fragment_library;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void loadView() {
        loadGridView();
        toolbar.setTitle(R.string.activity_library_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        rvBooks.setLayoutManager(layoutManager);
        libraryAdapter.setLayoutResource(R.layout.book_grid_item_layout);
        rvBooks.invalidate();
        viewShowingAsGrid = true;
        getActivity().invalidateOptionsMenu();
    }

    private void loadListView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvBooks.setLayoutManager(layoutManager);
        libraryAdapter.setLayoutResource(R.layout.book_list_item_layout);
        rvBooks.invalidate();
        viewShowingAsGrid = false;
        getActivity().invalidateOptionsMenu();
    }

    private void sortByDate() {
        libraryAdapter.sortBooksByDate();
    }

    private void sortByName() {
        libraryAdapter.sortBooksByTitle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.library_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.show_as_grid_action).setVisible(!viewShowingAsGrid);
        menu.findItem(R.id.show_as_list_action).setVisible(viewShowingAsGrid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_as_grid_action:
                loadGridView();
                break;
            case R.id.show_as_list_action:
                loadListView();
                break;
            case R.id.sort_by_name:
                sortByName();
                break;
            case R.id.sort_by_date:
                sortByDate();
                break;
        }
        return super.onOptionsItemSelected(item);
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
