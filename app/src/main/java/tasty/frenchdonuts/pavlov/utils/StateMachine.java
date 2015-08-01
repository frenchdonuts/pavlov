package tasty.frenchdonuts.pavlov.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by frenchdonuts on 7/23/15.
 */
public class StateMachine<T> {
    public final Observable<T> statesObservable;

    public StateMachine(Observable<Func1<T, T>> transformations, T initialState) {
        Observable<Func1<T, T>> transforms = transformations.startWith((x) -> x);

        PublishSubject<T> statesSubject = PublishSubject.create();
        transforms
                .scan(initialState, (state, transform) -> transform.call(state))
                .subscribe(statesSubject);

        statesObservable = statesSubject
                .asObservable()
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
