package tasty.frenchdonuts.pavlov;

import android.content.Context;
import android.widget.Toast;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import tasty.frenchdonuts.pavlov.db.entities.Goal;
import tasty.frenchdonuts.pavlov.db.tables.GoalsTable;
import tasty.frenchdonuts.pavlov.utils.Time;

/**
 * Created by frenchdonuts on 10/3/15.
 */
public class Handlers {
    private static final String TAG = Handlers.class.getSimpleName();

    public static void addGoalToSQLite(Action.AddGoalAction a, Dispatcher dispatcher, StorIOSQLite storIOSQLite) {
        if (a.name.isEmpty()) {
            dispatcher.dispatch(
                new Action.ToastUserAction("Goal must have a title!", Toast.LENGTH_SHORT));
            return;
        }
        if (a.priority > 7 || 0 > a.priority) {
            dispatcher.dispatch(
                new Action.ToastUserAction("Priority must be less than 7.", Toast.LENGTH_SHORT));
            return;
        }

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

    public static void removeGoalFromSQLite(Action.RemoveGoalAction a, StorIOSQLite storIOSQLite) {
        storIOSQLite.delete()
            .byQuery(DeleteQuery.builder()
                .table(GoalsTable.TABLE)
                .where(GoalsTable.COLUMN_ID + "=?")
                .whereArgs(a.id)
                .build())
            .prepare()
            .executeAsBlocking();
    }

    public static void toastUser(Action.ToastUserAction a, Context appContext) {
        Toast.makeText(appContext, a.msg, a.duration).show();
    }
}
