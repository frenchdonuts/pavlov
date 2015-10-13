package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subjects.ReplaySubject;
import tasty.frenchdonuts.pavlov.R;

/**
 *
 */
public class GoalItemView extends RelativeLayout {
    private static final String TAG = GoalItemView.class.getSimpleName();
    private ReplaySubject<Boolean> onFinishInflate;

    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvDueIn)
    TextView tvDueIn;
    @Bind(R.id.cvPriority)
    CircleView cvLevel;
    @Bind(R.id.divider)
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
        ButterKnife.bind(this);
        onFinishInflate.onNext(true);
    }

}
