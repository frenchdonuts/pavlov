package tasty.frenchdonuts.pavlov;

/**
 * Created by frenchdonuts on 9/27/15.
 */
public abstract class Action {
    public abstract String actionType();

    public static class AddGoalAction extends Action {
        public String actionType() {
            return "AddGoalAction";
        }

        public final String name;
        public final int daysDueIn;
        public final int monthsDueIn;
        public final int yearsDueIn;
        public final int priority;

        public AddGoalAction(String name, String daysDueIn, String monthsDueIn, String yearsDueIn,
                             String priority) {
            this.name = name;

            //
            int days = 1;
            if (!daysDueIn.isEmpty())
                days = Integer.parseInt(daysDueIn);

            int months = 0;
            if (!monthsDueIn.isEmpty()) months = Integer.parseInt(monthsDueIn);

            int years = 0;
            if (!yearsDueIn.isEmpty()) years = Integer.parseInt(yearsDueIn);

            this.daysDueIn = days;
            this.monthsDueIn = months;
            this.yearsDueIn = years;
            //

            int prior = 1;
            if (!priority.isEmpty())
                prior = Integer.parseInt(priority);
            this.priority = prior;
        }
    }

    public static class RemoveGoalAction extends Action {
        public String actionType() {
            return "RemoveGoalAction";
        }

        public final long id;

        public RemoveGoalAction(long id) {
            this.id = id;
        }
    }

    public static class ToastUserAction extends Action {
        public String actionType() { return "ToastUserAction"; }

        public final String msg;
        public final int duration;

        public ToastUserAction(String msg, int duration) {
            this.msg = msg;
            this.duration = duration;
        }
    }
}
