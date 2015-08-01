package tasty.frenchdonuts.pavlov.data;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class Goal extends RealmObject {
    private int priority;

    private long startDate;
    private long endDate;
    private long millisInOneLv;

    private String title;

    public static int calcNewPriority(Goal goal) {
        long millisToEnd = goal.getEndDate() - System.currentTimeMillis();

        if (millisToEnd < 0) return 8;

        int decs = (int) (millisToEnd / goal.getMillisInOneLv());

        return 8 - decs - 1;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
