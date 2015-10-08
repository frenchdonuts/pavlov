package tasty.frenchdonuts.pavlov;

import android.content.Context;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Inject;

/**
 *
 */
public class Dispatcher {
    private final StorIOSQLite storIOSQLite;
    private final Context appContext;

    @Inject
    Dispatcher(StorIOSQLite storIOSQLite, Context appContext) {
        this.storIOSQLite = storIOSQLite;
        this.appContext = appContext;
    }

    public void dispatch(Action action) {
        switch (action.actionType()) {
            case "AddGoalAction":
                Handlers.addGoalToSQLite((Action.AddGoalAction) action, this, storIOSQLite);
                break;

            case "RemoveGoalAction":
                Handlers.removeGoalFromSQLite((Action.RemoveGoalAction) action, storIOSQLite);
                break;

            case "ToastUserAction":
                Handlers.toastUser((Action.ToastUserAction) action, appContext);

            default:
                break;
        }
    }
}
