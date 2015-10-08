package tasty.frenchdonuts.pavlov;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by frenchdonuts on 10/4/15.
 */
@Module
public class DispatcherModule {
    @Provides
    @NonNull
    @Singleton
    public Dispatcher provideDispatcher(@NonNull StorIOSQLite storIOSQLite,
                                        @NonNull Context appContext) {
        return new Dispatcher(storIOSQLite, appContext);
    }
}
