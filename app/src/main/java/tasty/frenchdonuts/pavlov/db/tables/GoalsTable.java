package tasty.frenchdonuts.pavlov.db.tables;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.queries.Query;

/**
 * Created by frenchdonuts on 9/25/15.
 */
public class GoalsTable {

    @NonNull
    public static final String TABLE = "tasks";

    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_NAME = "name";

    @NonNull
    public static final String COLUMN_PRIORITY = "priority";

    @NonNull
    public static final String COLUMN_START_DATE = "start_date";

    @NonNull
    public static final String COLUMN_END_DATE = "end_date";

    @NonNull
    public static final String COLUMN_MILLIS_ONE_LV = "millis_one_lv";

    @NonNull
    public static final Query QUERY_ALL = Query.builder()
        .table(TABLE)
        .build();

    private GoalsTable() {
        throw new IllegalStateException("No instances please");
    }


    @NonNull
    public static String getCreateTableQuery() {
        return
            "CREATE TABLE " + TABLE + "("
            + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_PRIORITY + " INTEGER NOT NULL, "
            + COLUMN_START_DATE + " INTEGER NOT NULL, "
            + COLUMN_END_DATE + " INTEGER NOT NULL, "
            + COLUMN_MILLIS_ONE_LV + " INTEGER NOT NULL"
            + ");";
    }
}
