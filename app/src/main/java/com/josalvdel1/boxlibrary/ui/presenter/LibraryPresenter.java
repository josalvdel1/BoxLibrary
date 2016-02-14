package com.josalvdel1.boxlibrary.ui.presenter;

import android.support.annotation.NonNull;

import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.data.task.DownloadFilesTask;
import com.josalvdel1.boxlibrary.data.task.GetLocalBooksTask;
import com.josalvdel1.boxlibrary.data.task.SearchFilesTask;
import com.josalvdel1.boxlibrary.entities.Book;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class LibraryPresenter implements BasePresenter {

    private LibraryView libraryView;

    @Inject
    BoxLibraryApplication application;
    @Inject
    Provider<SearchFilesTask> searchFilesTaskProvider;
    @Inject
    Provider<DownloadFilesTask> downloadFilesTaskProvider;
    @Inject
    Provider<GetLocalBooksTask> getBooksTaskProvider;

    public LibraryPresenter() {
    }

    @Override
    public void init() {
        //loadBooks
    }

    @Override
    public void resume() {
        getBooksTaskProvider.get().setCallback(new GetLocalBooksTask.Callback() {
            @Override
            public void onBooksAvailable(List<Book> books) {
                libraryView.showBooks(books);
            }

            @Override
            public void onBookAvailable(Book book) {
                libraryView.showNewBook(book);
            }

            @Override
            public void onError() {
                libraryView.showError();
            }
        }).execute();

        /*searchBooks(new SearchFilesTask.Callback() {
            @Override
            public void onSearchComplete(DbxFiles.SearchResult searchResult) {
                ArrayList<DbxFiles.Metadata> metadataList = new ArrayList<>();
                for (DbxFiles.SearchMatch match : searchResult.matches) {
                    metadataList.add(match.metadata);
                }
                downloadFilesTaskProvider.get().setCallback(new DownloadFilesTask.Callback() {
                    @Override
                    public void onDownloadsComplete() {

                    }

                    @Override
                    public void onError() {

                    }
                }).execute(metadataList);
            }

            @Override
            public void onError() {

            }
        });*/
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        libraryView = null;
    }

    public void setLibraryView(@NonNull LibraryView libraryView) {
        this.libraryView = libraryView;
    }

    private void searchBooks(SearchFilesTask.Callback callback) {
        searchFilesTaskProvider.get().setCallback(callback).execute();
    }

    public void onBookClicked(Book book) {
        libraryView.onBookClicked(book);
    }

    public interface LibraryView {
        void showLoading();

        void hideLoading();

        void showBooks(List<Book> books);

        void showNewBook(Book book);

        void onBookClicked(Book book);

        void showEmpty();

        void showError();
    }
}
