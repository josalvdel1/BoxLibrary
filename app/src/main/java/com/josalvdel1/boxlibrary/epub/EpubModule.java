package com.josalvdel1.boxlibrary.epub;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.siegmann.epublib.epub.EpubReader;

@Module(injects = {}, library = true)
public class EpubModule {

    @Provides
    @Singleton
    public EpubReader provideEpubReader() {
        return new EpubReader();
    }
}
