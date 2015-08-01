package tasty.frenchdonuts.pavlov.data;


import javax.inject.Inject;

import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import tasty.frenchdonuts.pavlov.PavlovApp;
import tasty.frenchdonuts.pavlov.rx.RealmObservable;

/**
 * Created by frenchdonuts on 7/26/15.
 */
public class RealmServiceImpl implements RealmService {
    private final RealmConfiguration realmConfig;

    @Inject
    public RealmServiceImpl(RealmConfiguration realmConfig) {
        PavlovApp.getInstance().getGraph().inject(this);
        this.realmConfig = realmConfig;
    }

    @Override
    public Observable<RealmResults<Goal>> goals() {
        return RealmObservable.results(realmConfig,
                (realm) -> {
                    RealmResults<Goal> results = realm.where(Goal.class).findAll();
                    // Calculate new priorities
                    for(int i = 0; i < results.size(); i++)
                        results.get(i).setPriority(Goal.calcNewPriority(results.get(i)));

                    results.sort("priority", false, "endDate", true);
                    return results;
                });
    }

}
