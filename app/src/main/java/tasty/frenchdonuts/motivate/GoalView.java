package tasty.frenchdonuts.motivate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by frenchdonuts on 1/7/15.
 */
public class GoalView extends RelativeLayout {

	private Goal goal;
	@InjectView(R.id.task_tvTitle)
	TextView tvTitle;
	@InjectView(R.id.task_tvDueIn)
	TextView tvDueIn;
	@InjectView(R.id.task_cvLevel)
	CircleView cvLevel;
	@InjectView(R.id.divider)
	LinearLayout divider;

	public GoalView(Context context) {
		super(context);
		this.setGravity(Gravity.CENTER_VERTICAL);
	}
	public GoalView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		ButterKnife.inject(this);
	}

	public void bindTo(Goal goal) {
		this.goal = goal;
	}

	public void render() {
		if (goal != null) {
			tvTitle.setText(goal.getTitle());

			long countDown = goal.getEndDate() - System.currentTimeMillis();
			cvLevel.setPriority(goal.getPriority());
			tvDueIn.setText(millisToDateString(countDown));
		}
	}

	public static String millisToDateString(long millis) {
		if (millis < 0) return "You failed.";

		long remainder = millis;
		String result = "";

		int yrs = (int) (remainder / MainActivity.millisInYear);
		if ( yrs > 0) {
			remainder = remainder - yrs * MainActivity.millisInYear;
			result = result + yrs + pluralize("yr", yrs) + "  ";
		}

		int mos = (int) (remainder / MainActivity.millisInMonth);
		if (mos > 0) {
			remainder = remainder - mos * MainActivity.millisInMonth;
			result = result + mos + pluralize("mo", mos) + "  ";
		}

		int days = (int) (remainder / MainActivity.millisInDay);
		if (days > 0) {
			//remainder = remainder - days * MainActivity.millisInDay;
			result = result + days + pluralize("d", days);
		}


		return result;
	}
/*
	public static int millisToPriority(long millisToEnd, long millisInLv) {
		if (millisToEnd < 0) return 8;

		int decs = (int) (millisToEnd / millisInLv);
		return 8 - decs - 1;
	}*/

	private static String pluralize(String singular, int amount) {
		if (amount == 1)
			return singular;
		else return singular + "s";
	}

	public void enableCircleView(boolean enable) {
		if(enable) {
			cvLevel.setVisibility(View.VISIBLE);
		}
		else
			cvLevel.setVisibility(View.INVISIBLE);
	}

	public void enableDivider(boolean enable) {
		if(enable)
			divider.setVisibility(View.VISIBLE);
		else
			divider.setVisibility(View.GONE);
	}

}
