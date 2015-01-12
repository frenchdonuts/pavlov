package tasty.frenchdonuts.motivate;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

	public void bindTo(Task task) {
		cvLevel.setColor(task.getPriority());
		tvTitle.setText(task.getTitle());
		tvDueIn.setText(getCountdown(task.getEndDate()));
	}

	public String getCountdown(long endDate) {
		// Convert countdown in millis to yrs, months, days
		long currentTime = System.currentTimeMillis();
		long countdown = endDate - currentTime;

		if(countdown < 0)
			// User failed to complete task
			return "You failed.";

		// Use recursive procedure to calculate string
		return millisToDateString(countdown);
	}

	public static String millisToDateString(long millis) {
		long remainder = millis;
		String result = "";

		int yrs = ((int) ((long)remainder / MainActivity.millisInYear));
		if ( yrs > 0) {
			remainder = remainder - yrs * MainActivity.millisInYear;
			result = result + yrs + pluralize("yr", yrs) + "  ";
		}

		int mos = ((int) ((long)remainder / MainActivity.millisInMonth));
		if (mos > 0) {
			remainder = remainder - mos * MainActivity.millisInMonth;
			result = result + mos + pluralize("mo", mos) + "  ";
		}

		int days = ((int) ((long)remainder / MainActivity.millisInDay));
		if (days > 0) {
			remainder = remainder - days * MainActivity.millisInDay;
			result = result + days + pluralize("d", days);
		}


		return result;
	}

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
