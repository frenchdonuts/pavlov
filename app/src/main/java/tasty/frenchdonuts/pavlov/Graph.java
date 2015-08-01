package tasty.frenchdonuts.pavlov;

import javax.inject.Singleton;

import dagger.Component;
import tasty.frenchdonuts.pavlov.data.DataLayer;
import tasty.frenchdonuts.pavlov.data.DataModule;
import tasty.frenchdonuts.pavlov.data.RealmModule;
import tasty.frenchdonuts.pavlov.data.RealmServiceImpl;
import tasty.frenchdonuts.pavlov.viewmodels.GoalsViewModel;

/**
 * Created by frenchdonuts on 7/30/15.
 */
@Singleton
@Component(modules = {AndroidModule.class, RealmModule.class, DataModule.class})
public interface Graph {

    void inject(RealmServiceImpl realmService);
    void inject(DataLayer dataLayer);
    void inject(GoalsViewModel goalsViewModel);

    public final static class Initializer {
        public static Graph init(AndroidModule androidModule) {
            return DaggerGraph.builder()
                .androidModule(androidModule)
                .build();
        }
    }
}
