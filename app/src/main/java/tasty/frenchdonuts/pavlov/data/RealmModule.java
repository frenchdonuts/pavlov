package tasty.frenchdonuts.pavlov.data;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by frenchdonuts on 7/31/15.
 */
@Module
public class RealmModule {

    @Provides
    RealmConfiguration provideDefaultRealmConfiguration(Context context) {
        return new RealmConfiguration.Builder(context).build();
    }

    @Provides
    Realm provideDefaultRealm(RealmConfiguration defaultConfig) {
        return Realm.getInstance(defaultConfig);
    }
}
