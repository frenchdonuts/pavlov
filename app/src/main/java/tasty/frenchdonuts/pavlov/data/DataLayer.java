package tasty.frenchdonuts.pavlov.data;


import android.util.Log;

import javax.inject.Inject;

import fj.P5;
import fj.Unit;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import tasty.frenchdonuts.pavlov.PavlovApp;
import tasty.frenchdonuts.pavlov.utils.Time;

/**
 * Created by frenchdonuts on 7/29/15.
 */
public class DataLayer {
    private RealmConfiguration realmConfiguration;
    private RealmService realmService;

    @Inject DataLayer(RealmConfiguration realmConfiguration, RealmService realmService) {
        PavlovApp.getInstance().getGraph().inject(this);
        this.realmConfiguration = realmConfiguration;
        this.realmService = realmService;
    }

    public Observable<Unit> addGoalToRealm(P5<String, String, String, String, String> userInput) {
        int prior = 1;

        if (!userInput._5().isEmpty())
            prior = Integer.parseInt(userInput._5());
        long endDate = Time.endDate(userInput._2(), userInput._3(), userInput._4());

        Realm realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();

        Goal goal = realm.createObject(Goal.class);
        goal.setTitle(userInput._1());
        goal.setStartDate(System.currentTimeMillis());
        goal.setEndDate(endDate);
        goal.setPriority(prior);
        goal.setMillisInOneLv((endDate - System.currentTimeMillis()) / (8 - prior));

        realm.commitTransaction();

        return Observable.just(Unit.unit());
    }

    public Observable<Unit> removeGoalFromRealm(long primaryKey) {
        Realm realm = Realm.getInstance(realmConfiguration);
        realm.beginTransaction();

        Goal g = realm.where(Goal.class).equalTo("startDate", primaryKey)
            .findAll().get(0);
        Log.i("removeGoalFromRealm", g.toString());
        g.removeFromRealm();

        realm.commitTransaction();

        return Observable.just(Unit.unit());
    }

    public Observable<RealmResults<Goal>> goals() {
        return realmService.goals();
    }
}
