package tasty.frenchdonuts.pavlov;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by frenchdonuts on 9/26/15.
 */
@Module
public class AppModule {

    @NonNull
    private final PavlovApp app;

    AppModule(@NonNull PavlovApp app) {
        this.app = app;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return app;
    }
}
