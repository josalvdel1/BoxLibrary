package com.josalvdel1.boxlibrary.ui;

import com.josalvdel1.boxlibrary.LibraryBoxApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public class UiModule {

    @Provides
    @Singleton
    public Picasso providePicasso(LibraryBoxApplication application) {
        return Picasso.with(application);
    }
}
