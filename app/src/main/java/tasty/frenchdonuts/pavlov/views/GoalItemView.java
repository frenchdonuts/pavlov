package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import tasty.frenchdonuts.pavlov.states.GoalItemViewState;
import tasty.frenchdonuts.pavlov.R;

import static rx.Observable.combineLatest;

/**
 * Created by frenchdonuts on 1/7/15.
 */
public class GoalItemView extends RelativeLayout {
    private static final String TAG = GoalItemView.class.getSimpleName();
    private ReplaySubject<Boolean> onFinishInflate;

    @InjectView(R.id.task_tvTitle)
    TextView tvTitle;
    @InjectView(R.id.task_tvDueIn)
    TextView tvDueIn;
    @InjectView(R.id.task_cvLevel)
    CircleView cvLevel;
    @InjectView(R.id.divider)
    LinearLayout divider;

    public GoalItemView(Context context) {
        super(context);
        onFinishInflate = ReplaySubject.create();
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    public GoalItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onFinishInflate = ReplaySubject.create();
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        onFinishInflate.onNext(true);
    }

    public void setStateObservable(Observable<GoalItemViewState> state) {
        combineLatest(state, onFinishInflate, (viewState, bool) -> viewState)
            .subscribe(this::render);
    }

    private void render(GoalItemViewState state) {

        tvTitle.setText(state.title());

        cvLevel.setPriority(state.priority());

        tvDueIn.setText(state.dueIn());

        cvLevel.setVisibility(state.isCircleViewEnabled ? View.VISIBLE : View.INVISIBLE);

        divider.setVisibility(state.isDividerEnabled ? View.VISIBLE : View.GONE);
    }

}
