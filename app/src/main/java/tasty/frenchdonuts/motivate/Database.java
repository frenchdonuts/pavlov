package tasty.frenchdonuts.motivate;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class Database {
	private Realm realm;
	public Database(Context context) {
		realm = Realm.getInstance(context);
		realm.addChangeListener(() -> {

		});
	}

	public Subscription loadTasks(Action1<RealmResults<Goal>> onNext, Action1<Throwable> onError) {
		return Observable.create((Observable.OnSubscribe<RealmResults<Goal>>)(observer) -> {
			RealmResults<Goal> result = realm.where(Goal.class).findAll();
			result.sort("priority", false);
			observer.onNext(result);
			observer.onError(new Throwable("loadTasks failed"));
		}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(onNext, onError);
	}
}
