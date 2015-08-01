package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;
import tasty.frenchdonuts.pavlov.R;
import tasty.frenchdonuts.pavlov.rx.RxBinderUtil;
import tasty.frenchdonuts.pavlov.states.GoalItemViewState;
import tasty.frenchdonuts.pavlov.utils.SwipeDismissListViewTouchListener;
import tasty.frenchdonuts.pavlov.viewmodels.GoalsViewModel;

import static fj.P.p;
import static rx.Observable.switchOnNext;

/**
 * Created by frenchdonuts on 7/25/15.
 */
public class GoalsView extends FrameLayout {
    private static final String TAG = GoalsView.class.getSimpleName();
    private RxBinderUtil rxBinderUtil = new RxBinderUtil(this);

    private GoalItemAdapter listAdapter;
    @InjectView(R.id.lvGoals)
    ListView lv;
    @InjectView(R.id.etTitle)
    EditText etTitle;
    @InjectView(R.id.etLv)
    EditText etLv;
    @InjectView(R.id.etDueInDays)
    EditText etDueInDays;
    @InjectView(R.id.etDueInMonths)
    EditText etDueInMonths;
    @InjectView(R.id.etDueInYears)
    EditText etDueInYears;
    @InjectView(R.id.btnAddGoal)
    AddFloatingActionButton fabAddGoal;
    private Observable<OnClickEvent> addGoalClickObservable;
    // TODO: Make this a proper Observable that emits onDismissEvents
    private PublishSubject<Observable<GoalItemViewState>> onDismissSubject = PublishSubject.create();

    private GoalsViewModel viewModel;


    public GoalsView(Context context) {
        super(context, null);
        inflate(context, R.layout.goals_view, this);
    }
    public GoalsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.goals_view, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);

        setBackgroundColor(Color.WHITE);
        lv.setDivider(null);
        lv.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lv.setVerticalScrollBarEnabled(false);

        listAdapter = new GoalItemAdapter(this.getContext());
        lv.setAdapter(listAdapter);

        SwipeDismissListViewTouchListener touchListener =
            new SwipeDismissListViewTouchListener(
                lv,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    public boolean canDismiss(int position) {
                        return GoalsViewModel.canDismiss(position);
                    }

                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions)
                            onDismissSubject.onNext(listAdapter.getItem(position));
                    }
                });
        lv.setOnTouchListener(touchListener);
        lv.setOnScrollListener(touchListener.makeScrollListener());

        addGoalClickObservable = ViewObservable.clicks(fabAddGoal);

        // Guide user along form
        etTitle.setOnEditorActionListener((view, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etLv.requestFocus();
            }
            return handled;
        });

        // TODO: Make sure etLv input stays between 1 and 7
        etLv.setOnEditorActionListener((view, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etDueInDays.requestFocus();
            }
            return handled;
        });

        etDueInDays.setOnEditorActionListener((view, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etDueInMonths.requestFocus();
            }
            return handled;
        });

        etDueInMonths.setOnEditorActionListener((view, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etDueInYears.requestFocus();
            }
            return handled;
        });
    }

    public void setViewModel(GoalsViewModel viewModel) {
        this.viewModel = viewModel;
        rxBinderUtil.clear();

        if (viewModel != null) {
            rxBinderUtil.bindProperty(
                viewModel.goalItemViewStates, this::render);

            // TODO: Clear all EditTexts upon AddGoal
            // root.requestFocus(); ?
            addGoalClickObservable
                .map((x) -> p(etTitle.getText().toString(),
                    etDueInDays.getText().toString(),
                    etDueInMonths.getText().toString(),
                    etDueInYears.getText().toString(),
                    etLv.getText().toString()))
                .subscribe(viewModel.linkAddGoalAction);

            // Observable ViewState
            switchOnNext(onDismissSubject.asObservable())
                .map((viewState) -> viewState.primaryKey_startDate())
                .subscribe(viewModel.linkRemoveGoalAction);
        }
    }

    public void render(List<Observable<GoalItemViewState>> goalItemViewStates) {
        // Notify listAdapter that goalItemViewStates have changed
        listAdapter.updateData(goalItemViewStates);
    }
}
