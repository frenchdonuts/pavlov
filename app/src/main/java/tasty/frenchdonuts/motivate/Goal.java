package tasty.frenchdonuts.motivate;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class Goal extends RealmObject {
	private int priority;

	private long startDate;
	private long endDate;
	private long millisInOneLv;

	private String title;

	private boolean completed;

	private boolean repeat;

	private long period;

	public static void addGoalToRealm(Realm realm, String title, long endDate, int priority, long millisInOneLv) {
		realm.beginTransaction();

		Goal goal = realm.createObject(Goal.class);
		goal.setTitle(title);
		goal.setEndDate(endDate);
		goal.setPriority(priority);
		goal.setMillisInOneLv(millisInOneLv);

		realm.commitTransaction();
	}

	public static int calcNewPriority(Goal goal) {
		long millisToEnd = goal.endDate - System.currentTimeMillis();
		if (millisToEnd < 0) return 8;

		int decs = (int) (millisToEnd / goal.millisInOneLv);

		return 8 - decs - 1;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getMillisInOneLv() {
		return millisInOneLv;
	}

	public void setMillisInOneLv(long millisInOneLv) {
		this.millisInOneLv = millisInOneLv;
	}
}
