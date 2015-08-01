package tasty.frenchdonuts.pavlov.viewmodels;


import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fj.P5;
import fj.Unit;
import rx.Observable;
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
    public final PublishSubject<P5<String, String, String, String, String>> linkAddGoalAction;
    private final Observable<Unit> addGoalAction;

    public final PublishSubject<Long> linkRemoveGoalAction;
    private final Observable<Unit> removeGoalAction;// =

    // This is the Global Source of Truth for our application state
    // It pulls in domain data from our data repositories and maps it to UI state
    // Our View can then subscribe to this signal of states and render the states as they come in
    public final Observable<List<Observable<GoalItemViewState>>> goalItemViewStates;

    public GoalsViewModel() {
        PavlovApp.getInstance().getGraph().inject(this);

        linkAddGoalAction = PublishSubject.create();
        addGoalAction = linkAddGoalAction.asObservable()
            .subscribeOn(Schedulers.io())
            .flatMap(dataLayer::addGoalToRealm);

        linkRemoveGoalAction = PublishSubject.create();
        removeGoalAction = linkRemoveGoalAction.asObservable()
            .doOnNext((viewState) -> Log.i(TAG, "removeGoalAction ViewState: " + viewState.toString()))
            .subscribeOn(Schedulers.io())
            .flatMap(dataLayer::removeGoalFromRealm);

        goalItemViewStates = addGoalAction
            .mergeWith(removeGoalAction)
            .startWith(Unit.unit()) // Needed to kick off UI load before any user-interaction
            .flatMap((x) ->
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
                                return viewStates; }));

    }

    private GoalItemViewState convertGoalToViewState(Goal goal) {
        String dueIn = dueIn(goal.getEndDate() - System.currentTimeMillis());

        return GoalItemViewState.create(goal.getTitle(), goal.getPriority(), goal.getStartDate(), dueIn);
    }

    private String dueIn(long millis) {
        if (millis < 0)
            return "You failed.";
        return Time.millisToDaysAndHrsString.f(millis).run()._1();
    }

    public static boolean canDismiss(int position) {
        return true;
    }
}
