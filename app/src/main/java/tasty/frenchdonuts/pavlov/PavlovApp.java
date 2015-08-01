package tasty.frenchdonuts.pavlov;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import tasty.frenchdonuts.pavlov.data.Goal;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by frenchdonuts on 1/8/15.
 */
public class PavlovApp extends Application {
    @Singleton
    @Component(modules={AndroidModule.class})
    public interface ApplicationComponent {
        void inject(PavlovApp app);
    }
    private static PavlovApp instance;

    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        instance = this;
        mGraph = Graph.Initializer.init(new AndroidModule(this));

//        Realm.deleteRealmFile(this);
//        Realm.getInstance(this).beginTransaction();
//        Realm.getInstance(this).clear(Goal.class);
//        Realm.getInstance(this).commitTransaction();

    }

    public static PavlovApp getInstance() {
        return instance;
    }

    public Graph getGraph() {
        return mGraph;
    }

}
