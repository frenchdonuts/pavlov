package tasty.frenchdonuts.pavlov.viewmodels;


import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fj.P5;
import fj.Unit;
import fj.data.Array;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import tasty.frenchdonuts.pavlov.PavlovApp;
import tasty.frenchdonuts.pavlov.data.DataLayer;
import tasty.frenchdonuts.pavlov.data.Goal;
import tasty.frenchdonuts.pavlov.states.GoalItemViewState;
import tasty.frenchdonuts.pavlov.utils.Time;


/**
 * Created by frenchdonuts on 7/22/15.
 */
public class GoalsViewModel {
    private static final String TAG = GoalsViewModel.class.getSimpleName();

    @Inject
    DataLayer dataLayer;

    // TODO: Use a POJO instead of product type to make code more readable?
    public final PublishSubject<P5<String, String, String, String, String>> linkAddGoalAction;// = PublishSubject.create();
    private final Observable<Unit> addGoalAction; //= linkAddGoalAction.asObservable()
//        .flatMap(dataLayer::addGoalToRealm);

    public final PublishSubject<Long> linkRemoveGoalAction;// = PublishSubject.create();
    private final Observable<Unit> removeGoalAction;// =
//        linkRemoveGoalAction.asObservable()
//        .flatMap(dataLayer::removeGoalFromRealm);

    // This is the Global Source of Truth for our application state
    // It pulls in domain data from our data repositories and maps it to UI state
    // Our View can then subscribe to this signal of states and render the states as they come in
    public final Observable<List<Observable<GoalItemViewState>>> goalItemViewStates;// =
//        addGoalAction
//            .mergeWith(removeGoalAction)  // If we have actions that don't affect ViewState, then don't merge them
//            .startWith(Unit.unit()) // Needed to kick off UI load before any user-interaction
//            .flatMap((x) ->
//                // If we have multiple data Observables to use, we should probably merge/zip them
//                // into one Observable in a different module
//                dataLayer.goals()
//                    .map((listOfGoals) -> {
//                        final List<GoalItemViewState> goalItemViewStates =
//                            new ArrayList<GoalItemViewState>(listOfGoals.size());
//
//                        for (Goal goal : listOfGoals) {
//                            goalItemViewStates.add(convertGoalToViewState(goal));
//                        }
//                        return goalItemViewStates;
//                    }));

//    private final Array<Observable<RealmResults<Goal>>> dataServices;
//    public final Observable<List<GoalItemViewState>> goalItemViewStates1;
    // Can we have a fold across a [Observable Data] into Observable ViewState?
    // fold :: (ViewState -> Data -> Observable ViewState) -> Observable ViewState -> [Observable Data] -> Observable ViewState
    // foldM :: (b -> a -> f b) -> f b -> [f a] -> f b
    public GoalsViewModel() {
        PavlovApp.getInstance().getGraph().inject(this);

        linkAddGoalAction = PublishSubject.create();
        addGoalAction = linkAddGoalAction.asObservable()
            .subscribeOn(Schedulers.io())
            .flatMap(dataLayer::addGoalToRealm);

        linkRemoveGoalAction = PublishSubject.create();
        // Why won't linkRemoveGoalAction fire after the second time?
        removeGoalAction = linkRemoveGoalAction.asObservable()
            .doOnNext((viewState) -> Log.i(TAG, "removeGoalAction ViewState: " + viewState.toString()))
            .subscribeOn(Schedulers.io())
            .flatMap(dataLayer::removeGoalFromRealm);

        goalItemViewStates = addGoalAction
            .mergeWith(removeGoalAction)  // If we have actions that don't affect ViewState, then don't merge them
            .startWith(Unit.unit()) // Needed to kick off UI load before any user-interaction
            .flatMap((x) ->
                    // If we have multiple data Observables to use, we should probably merge/zip them
                    // into one Observable in a different module
                    // Observable RealmResults<Goal>
                    dataLayer.goals()
                        // Observable List<Observable<GoalItemViewState>>
                        .map((listOfGoals) -> {
                                final List<Observable<GoalItemViewState>> viewStates =
                                    new ArrayList<>(listOfGoals.size());

                                for (int i = 0; i < listOfGoals.size(); i++) {
                                    GoalItemViewState g = convertGoalToViewState(listOfGoals.get(i));

                                    int cur = g.priority();
                                    // The very first view is also first in its section
                                    int bef = i == 0 ? (cur + 1) : listOfGoals.get(i - 1).getPriority();
                                    // The very last view should not have a divider
                                    int aft = i == (listOfGoals.size() - 1) ? cur : listOfGoals.get(i + 1).getPriority();

                                    // If current view is first in its section (cur < bef), enable circle view
                                    g.isCircleViewEnabled = (cur < bef);
                                    // If current view is last in its section (cur > aft), enable divider
                                    g.isDividerEnabled = (cur > aft);

                                    viewStates.add(Observable.just(g));
                                }
                                return viewStates;
                                // Array Goal
//                                Array.iterableArray(listOfGoals)
//                                    // Array GoalItemViewState
//                                    .map(this::convertGoalToViewState)
//                                    .foldLeft((acc, viewState) -> {
//                                        int z = acc._2();
//                                        if (acc._2() > viewState.priority()) {
//                                            viewState.isCircleViewEnabled = true;
//                                            z = viewState.priority();
//                                        }
//                                        return p(acc._1().append(single(viewState)), x);
//                                    }, p(empty(), 8))
//                                    ._1().reverse()
//                                    .foldLeft((acc, viewState) -> {
//                                        int z = acc._2();
//                                        if (acc._2() < viewState.priority()) {
//                                            viewState.isDividerEnabled = true;
//                                            z = viewState.priority();
//                                        }
//                                        return p(acc._1().append(single(viewState)), x);
//                                    }, p(empty(), 0))
//                                    ._1().reverse()
//                                    .map((viewState) -> Observable.just(viewState))
                            }
                        )
//                        .observeOn(AndroidSchedulers.mainThread())
            );

//        dataServices = array(dataService.goals());
//        goalItemViewStates1 = addGoalAction.mergeWith(removeGoalAction)
//            .startWith(Unit.unit())
//            .flatMap((x) ->
//                Util.foldM((listOfViewStates, aDataService) ->
//
//                    aDataService.data()         // This data service returns an Observable of data
//                        .map((datum) -> useDataToChangeViewState(datum))
//
//                , Observable.just(null), dataServices));
    }

//    private List<GoalItemViewState> useDataToChangeViewState(RealmResults<Goal> goals) {
//        return null;
//    }
//    What if I have 2 or more dataServices that return the same data type? Ex:
//    two data sources that return Observable RealmResults<Goal>
//    I don't think this'll be a problem because it is reasonable for one dataService to be wholly responsible
//    for one data type: There should be exactly one data service that returns Observable RealmResults<Goal>
//    We could use the decorator pattern here...decorate viewStates!! Just map decorators over List<ViewStates>
//    private GoalItemViewState decorateAsGoalItem(ViewState viewState) {
//        return GoalItemDecorator.decorate(viewState);
//    }
//    private List<GoalItemViewState> useDataToChangeViewState(Object someDatum) {
//        return null
//    }

    private GoalItemViewState convertGoalToViewState(Goal goal) {
        String dueIn = dueIn(goal.getEndDate() - System.currentTimeMillis());

        return GoalItemViewState.create(goal.getTitle(), goal.getPriority(), goal.getStartDate(), dueIn);
    }

//    public void linkAddGoalAction(Observable<P5<String, String, String, String, String>> o) {
//        addGoalAction = o.flatMap(dataLayer::addGoalToRealm);
//    }
//
//    public void linkRemoveGoalAction(Observable<Long> o) {
//        removeGoalAction = o.flatMap(dataLayer::removeGoalFromRealm);
//    }

    private String dueIn(long millis) {
        if (millis < 0)
            return "You failed.";
        return Time.millisToDaysAndHrsString.f(millis).run()._1();
    }

    public static boolean canDismiss(int position) {
        return true;
    }
}
