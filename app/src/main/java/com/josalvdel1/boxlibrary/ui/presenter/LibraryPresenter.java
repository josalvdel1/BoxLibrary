package com.josalvdel1.boxlibrary.ui.presenter;

import android.support.annotation.NonNull;

import com.dropbox.core.v2.DbxFiles;
import com.josalvdel1.boxlibrary.data.MockService;
import com.josalvdel1.boxlibrary.data.task.DownloadFilesTask;
import com.josalvdel1.boxlibrary.data.task.SearchFilesTask;
import com.josalvdel1.boxlibrary.entities.Book;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class LibraryPresenter implements BasePresenter {


    private LibraryView libraryView;

    @Inject
    Provider<SearchFilesTask> searchFilesTaskProvider;
    @Inject
    Provider<DownloadFilesTask> downloadFilesTaskProvider;

    public LibraryPresenter() {
    }

    @Override
    public void init() {
        //loadBooks
    }

    @Override
    public void resume() {
        libraryView.showBooks(MockService.getMockBooks());
        searchBooks(new SearchFilesTask.Callback() {
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
        });
    }

    @Override
    public void pause() {

    }

    public void setLibraryView(@NonNull LibraryView libraryView) {
        this.libraryView = libraryView;
    }

    private void searchBooks(SearchFilesTask.Callback callback) {
        searchFilesTaskProvider.get().setCallback(callback).execute();
    }

    public interface LibraryView {
        void showLoading();

        void hideLoading();

        void showBooks(List<Book> books);

        void showEmpty();

        void showError();
    }
}
