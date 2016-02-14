package com.josalvdel1.boxlibrary.ui.presenter;

import android.support.annotation.NonNull;

import com.dropbox.core.v2.DbxFiles;
import com.josalvdel1.boxlibrary.data.task.DownloadFilesTask;
import com.josalvdel1.boxlibrary.data.task.GetLocalBooksTask;
import com.josalvdel1.boxlibrary.data.task.SearchFilesTask;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class LibraryPresenter implements BasePresenter {

    private LibraryView libraryView;

    @Inject
    SessionManager sessionManager;
    @Inject
    Provider<SearchFilesTask> searchFilesTaskProvider;
    @Inject
    Provider<DownloadFilesTask> downloadFilesTaskProvider;
    @Inject
    Provider<GetLocalBooksTask> getBooksTaskProvider;

    private List<Book> currentBooks;


    @Override
    public void init() {
        if (sessionManager.isUserLogged() && currentBooks == null) {
            libraryView.showLoading();
        }
    }

    @Override
    public void resume() {
        if (currentBooks == null) {
            searchBooks();
        } else {
            if (libraryView != null) {
                libraryView.showBooks(currentBooks);
            }
        }
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

    private void searchBooks() {
        searchFilesTaskProvider.get().setCallback(new SearchFilesTask.Callback() {
            @Override
            public void onSearchComplete(DbxFiles.SearchResult searchResult) {
                ArrayList<DbxFiles.Metadata> metadataList = new ArrayList<>();
                for (DbxFiles.SearchMatch match : searchResult.matches) {
                    metadataList.add(match.metadata);
                }
                downloadFilesTaskProvider.get().setCallback(new DownloadFilesTask.Callback() {
                    @Override
                    public void onDownloadsComplete() {
                        loadBooks();
                    }

                    @Override
                    public void onError() {
                        if (libraryView != null) {
                            libraryView.showError();
                        }
                    }
                }).execute(metadataList);
            }

            @Override
            public void onError() {
                if (libraryView != null) {
                    libraryView.showError();
                }
            }
        }).execute();
    }

    public void onBookClicked(Book book) {
        libraryView.onBookClicked(book);
    }

    private void loadBooks() {
        getBooksTaskProvider.get().setCallback(new GetLocalBooksTask.Callback() {
            @Override
            public void onBooksAvailable(List<Book> books) {
                if (libraryView != null && books.size() > 0) {
                    currentBooks = books;
                    libraryView.showBooks(books);
                    libraryView.hideLoading();
                } else if (libraryView != null) {
                    currentBooks = books;
                    libraryView.showEmpty();
                }
            }

            @Override
            public void onBookAvailable(Book book) {
                if (libraryView != null) {
                    //libraryView.showNewBook(book); TODO the one by one loading needs more time
                    //libraryView.hideLoading();
                }
            }

            @Override
            public void onError() {
                if (libraryView != null) {
                    libraryView.showError();
                }
            }
        }).execute();
    }

    public void onRefresh() {
        libraryView.showEmpty();
        libraryView.showLoading();
        searchBooks();
    }

    public void updateBooks(List<Book> books) {
        currentBooks = books;
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
