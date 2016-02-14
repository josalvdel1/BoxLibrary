package com.josalvdel1.boxlibrary.ui.presenter;

import android.support.annotation.NonNull;

import com.josalvdel1.boxlibrary.data.task.GetBookTask;
import com.josalvdel1.boxlibrary.entities.Book;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class BookDetailPresenter implements BasePresenter {

    @Inject
    Provider<GetBookTask> getBookTaskProvider;

    private DetailView detailView;
    private String path;

    public BookDetailPresenter() {
    }

    @Override
    public void init() {
        //loadBooks
    }


    @Override
    public void resume() {
        getBookTaskProvider.get().setBookPath(path)
                .setCallback(new GetBookTask.Callback() {
                    @Override
                    public void onBookAvailable(Book book) {
                        detailView.showBook(book);
                    }

                    @Override
                    public void onError() {
                        detailView.showError();
                    }
                }).execute();
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        detailView = null;
    }

    public void setBookPath(String path) {
        this.path = path;
    }

    public void setDetailView(@NonNull DetailView detailView) {
        this.detailView = detailView;
    }

    public interface DetailView {
        void showLoading();

        void hideLoading();

        void showBook(Book book);

        void showError();
    }
}
