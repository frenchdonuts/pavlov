package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tasty.frenchdonuts.pavlov.Action;
import tasty.frenchdonuts.pavlov.Dispatcher;
import tasty.frenchdonuts.pavlov.PavlovApp;
import tasty.frenchdonuts.pavlov.R;

/**
 * Created by frenchdonuts on 9/27/15.
 */
public class AddGoalFormView extends FrameLayout {
    private static final String TAG = AddGoalFormView.class.getSimpleName();

    @Inject
    Dispatcher dispatcher;

    @Bind(R.id.etTitle)
    EditText etTitle;
    @Bind(R.id.etLv)
    EditText etLv;
    @Bind(R.id.etDueInDays)
    EditText etDueInDays;
    @Bind(R.id.etDueInMonths)
    EditText etDueInMonths;
    @Bind(R.id.etDueInYears)
    EditText etDueInYears;

    // TODO: Use official support library fab
    @OnClick(R.id.btnAddGoal)
    public void addGoal(View v) {
        dispatcher.dispatch(new Action.AddGoalAction(
            etTitle.getText().toString(),
            etDueInDays.getText().toString(),
            etDueInMonths.getText().toString(),
            etDueInYears.getText().toString(),
            etLv.getText().toString()));

        etTitle.getText().clear();
        etDueInDays.getText().clear();
        etDueInMonths.getText().clear();
        etDueInYears.getText().clear();
        etLv.getText().clear();
    }

    public AddGoalFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.add_goal_form, this);
        PavlovApp.get(context).appComponent().inject(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

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
}
