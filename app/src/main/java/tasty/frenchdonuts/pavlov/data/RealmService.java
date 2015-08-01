package tasty.frenchdonuts.pavlov.data;


import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by frenchdonuts on 7/26/15.
 */
public interface RealmService {

    Observable<RealmResults<Goal>> goals();

}
