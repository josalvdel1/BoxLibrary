package com.josalvdel1.boxlibrary.data;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {}, library = true)
public class DataModule {

    @Provides
    @Singleton
    public DbxClientV2 provideDbxClient() {
        DbxClientV2 clientV2 = new DbxClientV2(new DbxRequestConfig("dropbox/java-tutorial", "es_ES"),
                "1ipON2vfjx4AAAAAAAAAGnWIudHvoOvpoy6558h_KZcU4QpeY7KmGYVwz-6oicFw");
        return clientV2;
    }
}
