package tasty.frenchdonuts.pavlov;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import javax.inject.Inject;

import tasty.frenchdonuts.pavlov.db.entities.Goal;
import tasty.frenchdonuts.pavlov.db.tables.GoalsTable;
import tasty.frenchdonuts.pavlov.utils.Time;

/**
 * Created by frenchdonuts on 10/3/15.
 */
public class Handlers {
    private static final String TAG = Handlers.class.getSimpleName();

    StorIOSQLite storIOSQLite;

    @Inject
    Handlers(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    public void addGoalToSQLite(Action.AddGoalAction a) {
        long startDate = System.currentTimeMillis();
        long endDate = Time.endDate(startDate, a.daysDueIn, a.monthsDueIn, a.yearsDueIn);
        long millisInOneLv = (endDate - startDate) / (8 - a.priority);
        Goal g = Goal.newGoal(null, a.name, startDate, endDate, a.priority, millisInOneLv);

        storIOSQLite.
            put()
            .object(g)
            .prepare()
            .executeAsBlocking();
    }

    public void removeGoalFromSQLite(Action.RemoveGoalAction a) {
        storIOSQLite.delete()
            .byQuery(DeleteQuery.builder()
                .table(GoalsTable.TABLE)
                .where(GoalsTable.COLUMN_ID + "=?")
                .whereArgs(a.id)
                .build())
            .prepare()
            .executeAsBlocking();
    }
}
