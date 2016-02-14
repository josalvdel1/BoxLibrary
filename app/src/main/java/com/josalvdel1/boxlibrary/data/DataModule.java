package com.josalvdel1.boxlibrary.data;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.josalvdel1.boxlibrary.session.SessionManager;

import dagger.Module;
import dagger.Provides;

@Module(injects = {}, complete = false, library = true)
public class DataModule {

    @Provides
    public DbxClientV2 provideDbxClient(SessionManager sessionManager) {
        DbxClientV2 clientV2 = new DbxClientV2(new DbxRequestConfig("BoxLibrary", "es_ES"),
                sessionManager.getAuthToken());
        return clientV2;
    }
}
