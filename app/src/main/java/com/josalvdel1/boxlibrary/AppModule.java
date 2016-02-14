package com.josalvdel1.boxlibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.josalvdel1.boxlibrary.data.DataModule;
import com.josalvdel1.boxlibrary.epub.EpubModule;
import com.josalvdel1.boxlibrary.ui.UiModule;
import com.josalvdel1.boxlibrary.ui.activity.BookDetailActivity;
import com.josalvdel1.boxlibrary.ui.activity.LibraryActivity;
import com.josalvdel1.boxlibrary.ui.activity.LoginActivity;
import com.josalvdel1.boxlibrary.ui.fragment.LibraryFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                UiModule.class,
                DataModule.class,
                EpubModule.class,
        },
        injects = {
                BoxLibraryApplication.class,

                //Activities
                LoginActivity.class,
                LibraryActivity.class,
                BookDetailActivity.class,

                //Fragments
                LibraryFragment.class,
        },
        library = true)

public final class AppModule {

    private final BoxLibraryApplication application;

    public AppModule(BoxLibraryApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    BoxLibraryApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences preferences() {
        return application.getSharedPreferences(application.getString(R.string.app_name), Context.MODE_PRIVATE);
    }
}

