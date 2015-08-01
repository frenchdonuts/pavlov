package tasty.frenchdonuts.pavlov.states;


import auto.parcel.AutoParcel;

/**
 * Created by frenchdonuts on 7/23/15.
 */
@AutoParcel
public abstract class GoalItemViewState {
    public abstract String title();
    public abstract int priority();
    public abstract long primaryKey_startDate();
    public abstract String dueIn();
    public boolean isCircleViewEnabled = false;
    public boolean isDividerEnabled = false;

    public static GoalItemViewState create(String title, int priority, long primaryKey_startDate, String dueIn) {
        return new AutoParcel_GoalItemViewState(title, priority, primaryKey_startDate, dueIn);
    }

}
