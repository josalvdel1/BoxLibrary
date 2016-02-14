package com.josalvdel1.boxlibrary.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.adapter.LibraryAdapter;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.ui.activity.BookDetailActivity;
import com.josalvdel1.boxlibrary.ui.presenter.LibraryPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.Bind;
import butterknife.OnClick;


public class LibraryFragment extends BaseFragment implements LibraryPresenter.LibraryView {

    public static final String TAG = "grid_fragment";
    private static final String GRID_STATE = "state_grid";

    @Inject
    LibraryPresenter libraryPresenter;

    @Inject
    Provider<LibraryAdapter> libraryAdapterProvider;
    @Bind(R.id.rv_books)
    RecyclerView rvBooks;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_empty_view)
    TextView tvEmptyView;
    @Bind(R.id.cl_library_container)
    CoordinatorLayout clLibraryContainer;
    private boolean viewShowingAsGrid;
    private LibraryAdapter libraryAdapter;

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
        toolbar.setTitle(R.string.activity_library_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        libraryPresenter.setLibraryView(this);
        libraryPresenter.init();
        libraryAdapter = libraryAdapterProvider.get();
        rvBooks.setAdapter(libraryAdapter);
        if (viewShowingAsGrid) {
            loadGridView();
        } else {
            loadListView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        libraryPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        libraryPresenter.pause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(GRID_STATE, viewShowingAsGrid);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            viewShowingAsGrid = savedInstanceState.getBoolean(GRID_STATE, true);
        }
    }

    private void loadGridView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),
                getActivity().getResources().getInteger(R.integer.rv_library_span_count));
        rvBooks.setLayoutManager(layoutManager);
        libraryAdapter.setLayoutResource(R.layout.book_grid_item_layout);
        rvBooks.setAdapter(libraryAdapter);
        rvBooks.invalidate();
        viewShowingAsGrid = true;
        getActivity().invalidateOptionsMenu();
    }

    private void loadListView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvBooks.setLayoutManager(layoutManager);
        libraryAdapter.setLayoutResource(R.layout.book_list_item_layout);
        rvBooks.setAdapter(libraryAdapter);
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

    @OnClick(R.id.fb_refresh)
    public void onRefreshClicked() {
        libraryPresenter.onRefresh();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvBooks.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rvBooks.setVisibility(View.VISIBLE);
        tvEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showBooks(List<Book> eBooks) {
        libraryAdapter.setBooks(eBooks);
        if (rvBooks.getVisibility() == View.GONE) {
            rvBooks.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNewBook(Book book) {
        libraryAdapter.addBook(book);
        if (rvBooks.getVisibility() == View.GONE) {
            rvBooks.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBookClicked(Book book) {
        startActivity(BookDetailActivity.getLaunchIntent(getActivity(), book.getFilePath()));
    }

    @Override
    public void showEmpty() {
        rvBooks.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar
                .make(clLibraryContainer, getString(R.string.error), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
