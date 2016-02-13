package com.josalvdel1.boxlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.ui.presenter.BookDetailPresenter;

import javax.inject.Inject;

import butterknife.Bind;

public class BookDetailActivity extends BaseActivity implements BookDetailPresenter.DetailView {

    public static final String EXTRA_BOOK_PATH = "extra_book_path";

    @Inject
    BookDetailPresenter detailPresenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_book_cover)
    ImageView bookCoverImage;
    @Bind(R.id.rv_books)
    NestedScrollView nsvBookInfo;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.tv_book_author)
    TextView tvBookAuthor;
    @Bind(R.id.tv_book_description)
    TextView tvBookDescription;

    private String bookPath;


    public static Intent getLaunchIntent(Context context, String path) {
        if (path == null) {
            throw new IllegalArgumentException("Detail view with a null book");
        }

        Intent launchIntent = new Intent(context, BookDetailActivity.class);
        launchIntent.putExtra(EXTRA_BOOK_PATH, path);
        return launchIntent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookPath = getIntent().getStringExtra(EXTRA_BOOK_PATH);

        setSupportActionBar(toolbar);

        detailPresenter.init();
        detailPresenter.setDetailView(this);
        detailPresenter.setBookPath(bookPath);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detailPresenter.pause();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showBook(Book book) {
        bookCoverImage.setImageBitmap(book.getCover());
        collapsingToolbarLayout.setTitle(book.getTitle());
        tvBookDescription.setText(book.getDescription() != null ? book.getDescription() : "");
        tvBookAuthor.setText(book.getAuthor() != null ? book.getAuthor() : "");
    }

    @Override
    public void showError() {
    }
}
