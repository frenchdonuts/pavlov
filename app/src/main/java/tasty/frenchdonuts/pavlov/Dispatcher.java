package tasty.frenchdonuts.pavlov;

import javax.inject.Inject;

/**
 *
 */
public class Dispatcher {
    private final Handlers handlers;

    @Inject
    Dispatcher(Handlers handlers) {
        this.handlers = handlers;
    }

    public void dispatch(Action action) {
        switch (action.actionType()) {
            case "AddGoalAction":
                handlers.addGoalToSQLite((Action.AddGoalAction) action);
                break;

            case "RemoveGoalAction":
                handlers.removeGoalFromSQLite((Action.RemoveGoalAction) action);
                break;

            default:
                break;
        }
    }
}
