package tasty.frenchdonuts.pavlov.db;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tasty.frenchdonuts.pavlov.db.entities.Goal;
import tasty.frenchdonuts.pavlov.db.entities.GoalStorIOSQLiteDeleteResolver;
import tasty.frenchdonuts.pavlov.db.entities.GoalStorIOSQLiteGetResolver;
import tasty.frenchdonuts.pavlov.db.entities.GoalStorIOSQLitePutResolver;

@Module
public class DbModule {
    @Provides
    @NonNull
    @Singleton
    public StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(Goal.class, SQLiteTypeMapping.<Goal>builder()
                    .putResolver(new GoalStorIOSQLitePutResolver())
                    .getResolver(new GoalStorIOSQLiteGetResolver())
                    .deleteResolver(new GoalStorIOSQLiteDeleteResolver())
                    .build())
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public SQLiteOpenHelper provideSQLiteOpenHelper(@NonNull Context context) {
        return new DbOpenHelper(context);
    }
}
