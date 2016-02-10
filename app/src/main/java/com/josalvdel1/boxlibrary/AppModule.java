package com.josalvdel1.boxlibrary;

import com.josalvdel1.boxlibrary.ui.activity.LibraryActivity;
import com.josalvdel1.boxlibrary.ui.activity.LoginActivity;
import com.josalvdel1.boxlibrary.ui.fragment.LibraryGridFragment;
import com.josalvdel1.boxlibrary.ui.fragment.LibraryListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
        },
        injects = {
                LibraryBoxApplication.class,

                //Activities
                LoginActivity.class,
                LibraryActivity.class,

                //Fragments
                LibraryGridFragment.class,
                LibraryListFragment.class,
        },
        library = true)

public final class AppModule {

    private final LibraryBoxApplication application;

    public AppModule(LibraryBoxApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    LibraryBoxApplication provideApplication() {
        return application;
    }
}

