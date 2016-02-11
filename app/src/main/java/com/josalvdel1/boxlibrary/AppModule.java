package com.josalvdel1.boxlibrary;

import com.josalvdel1.boxlibrary.data.DataModule;
import com.josalvdel1.boxlibrary.ui.UiModule;
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
        },
        injects = {
                LibraryBoxApplication.class,

                //Activities
                LoginActivity.class,
                LibraryActivity.class,

                //Fragments
                LibraryFragment.class,
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

