package tasty.frenchdonuts.pavlov;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by frenchdonuts on 10/4/15.
 */
@Module
public class HandlersModule {
    @Provides
    @NonNull
    @Singleton
    public Dispatcher provideDispatcher(@NonNull Handlers handler) {
        return new Dispatcher(handler);
    }

    @Provides
    @NonNull
    public Handlers provideHandlers(@NonNull StorIOSQLite storIOSQLite) {
        return new Handlers(storIOSQLite);
    }
}
