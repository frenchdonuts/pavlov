package tasty.frenchdonuts.pavlov.data;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

/**
 * Created by frenchdonuts on 7/28/15.
 */
@Module
public class DataModule {

    @Provides
    DataLayer provideDataLayer(RealmConfiguration realmConfiguration, RealmService realmService) {
        return new DataLayer(realmConfiguration, realmService);
    }
    @Provides
    RealmService providePavlovDataService(RealmConfiguration realmConfig) {
        return new RealmServiceImpl(realmConfig);
    }

}
