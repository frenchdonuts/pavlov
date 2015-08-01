package tasty.frenchdonuts.pavlov;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by frenchdonuts on 7/30/15.
 */
@Module
public class AndroidModule {
    private final PavlovApp app;

    public AndroidModule(PavlovApp app) {
        this.app = app;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return app;
    }
}
