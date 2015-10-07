package tasty.frenchdonuts.pavlov;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import tasty.frenchdonuts.pavlov.db.DbModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by frenchdonuts on 1/8/15.
 */
public class PavlovApp extends Application {

    @Nullable
    private volatile AppComponent appComponent;

    @NonNull
    public static PavlovApp get(@NonNull Context context) {
        return (PavlovApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @NonNull
    public AppComponent appComponent() {
        if (appComponent == null) {
            synchronized (PavlovApp.class) {
                if (appComponent == null) {
                    appComponent = createAppComponent();
                }
            }
        }

        return appComponent;
    }

    @NonNull
    private AppComponent createAppComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule())
                .build();
    }
}
