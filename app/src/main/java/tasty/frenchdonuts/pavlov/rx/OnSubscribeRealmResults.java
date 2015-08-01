package tasty.frenchdonuts.pavlov.rx;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public abstract class OnSubscribeRealmResults<T extends RealmObject> implements Observable.OnSubscribe<RealmResults<T>> {
    private Context context;
    private RealmConfiguration realmConfig;

    public OnSubscribeRealmResults(Context context) {
        this.context = context.getApplicationContext();
    }
    public OnSubscribeRealmResults(RealmConfiguration realmConfig) {
        this.realmConfig = realmConfig;
    }

    @Override
    public void call(final Subscriber<? super RealmResults<T>> subscriber) {
        final Realm realm = realmConfig == null ? Realm.getInstance(context) : Realm.getInstance(realmConfig);
        subscriber.add(Subscriptions.create(new Action0() {
            @Override
            public void call() {
                try {
                    realm.close();
                } catch (RealmException ex) {
                    subscriber.onError(ex);
                }
            }
        }));

        RealmResults<T> results;
        realm.beginTransaction();
        try {
            results = get(realm);
            realm.commitTransaction();
        } catch (RuntimeException e) {
            realm.cancelTransaction();
            subscriber.onError(new RealmException("Error during transaction.", e));
            return;
        } catch (Error e) {
            realm.cancelTransaction();
            subscriber.onError(e);
            return;
        }
        if (results != null) {
            subscriber.onNext(results);
        }
        subscriber.onCompleted();
    }

    public abstract RealmResults<T> get(Realm realm);
}




