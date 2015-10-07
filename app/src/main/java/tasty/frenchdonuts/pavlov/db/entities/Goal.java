package tasty.frenchdonuts.pavlov.db.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import tasty.frenchdonuts.pavlov.db.tables.GoalsTable;

/**
 * Created by frenchdonuts on 9/25/15.
 */
@StorIOSQLiteType(table = GoalsTable.TABLE)
public class Goal {

    @Nullable
    @StorIOSQLiteColumn(name = GoalsTable.COLUMN_ID, key = true)
    Long id;

    @NonNull
    @StorIOSQLiteColumn(name = GoalsTable.COLUMN_NAME)
    String name;

    @NonNull
    @StorIOSQLiteColumn(name = GoalsTable.COLUMN_START_DATE)
    Long startDate;

    @NonNull
    @StorIOSQLiteColumn(name = GoalsTable.COLUMN_END_DATE)
    Long endDate;

    @NonNull
    @StorIOSQLiteColumn(name = GoalsTable.COLUMN_PRIORITY)
    Integer priority;

    @NonNull
    @StorIOSQLiteColumn(name = GoalsTable.COLUMN_MILLIS_ONE_LV)
    Long millisInOneLv;

    Goal() {

    }

    private Goal(@Nullable Long id, @NonNull String name,
                 @NonNull Long startDate, @NonNull Long endDate,
                 @NonNull Integer priority, @NonNull Long millisInOneLv) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.millisInOneLv = millisInOneLv;
    }

    @NonNull
    public static Goal newGoal(@Nullable Long id, @NonNull String name,
                               @NonNull Long startDate, @NonNull Long endDate,
                               @NonNull Integer priority, @NonNull Long millisInOneLv) {
        Goal goal = new Goal(id, name, startDate, endDate, priority, millisInOneLv);
        return goal;
    }

    @NonNull
    public static Goal newGoal(@NonNull String name,
                               @NonNull Long startDate, @NonNull Long endDate,
                               @NonNull Integer priority, @NonNull Long millisInOneLv) {
        Goal goal = new Goal(null, name, startDate, endDate, priority, millisInOneLv);
        return goal;
    }

    @Nullable
    public Long id() {
        return id;
    }

    @NonNull
    public String name() {
        return name;
    }

    @NonNull
    public Long startDate() {
        return startDate;
    }

    @NonNull
    public Long endDate() {
        return endDate;
    }

    @NonNull
    public Integer priority() {
        return priority;
    }

    @NonNull
    public Long millisInOneLv() {
        return millisInOneLv;
    }
}
