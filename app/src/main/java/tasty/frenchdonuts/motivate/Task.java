package tasty.frenchdonuts.motivate;

import io.realm.RealmObject;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class Task extends RealmObject {
	private int init_priority;
	private int priority;

	private long startDate;
	private long endDate;

	private String title;

	private boolean completed;

	private boolean repeat;

	private long period;

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

	public int getInit_priority() {
		return init_priority;
	}

	public void setInit_priority(int init_priority) {
		this.init_priority = init_priority;
	}

	public int getPriority() {

		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
