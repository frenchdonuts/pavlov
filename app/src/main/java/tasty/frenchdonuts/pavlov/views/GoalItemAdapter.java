package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import tasty.frenchdonuts.pavlov.Action;
import tasty.frenchdonuts.pavlov.Dispatcher;
import tasty.frenchdonuts.pavlov.PavlovApp;
import tasty.frenchdonuts.pavlov.R;
import tasty.frenchdonuts.pavlov.db.entities.Goal;
import tasty.frenchdonuts.pavlov.utils.Time;
import tasty.frenchdonuts.pavlov.views.helper.OnStartDragListener;
import tasty.frenchdonuts.pavlov.views.helper.SwipeItemTouchHelperAdapter;

/**
 *
 */
public class GoalItemAdapter extends RecyclerView.Adapter<GoalItemAdapter.ViewHolder>
        implements SwipeItemTouchHelperAdapter {
    private static final String TAG = GoalItemAdapter.class.getSimpleName();

    @Inject
    Dispatcher dispatcher;

    private List<Goal> goals;
    private Map<Long, State> localState = new HashMap<>();

    private OnStartDragListener startDragListener;

    public GoalItemAdapter(Context context, OnStartDragListener startDragListener) {
        PavlovApp.get(context).appComponent().inject(this);
        this.startDragListener = startDragListener;
    }

    public void setGoals(@Nullable List<Goal> goals) {
        if (goals == null) return;

        this.goals = goals;
        /** Calculate some local state **/
        long now = System.currentTimeMillis();
        // Calculate priorities
        for (int i = 0; i < goals.size(); i++) {
            Goal g = goals.get(i);
            State s = new State();

            s.priority = calcNewPriority(g, now);
            localState.put(g.id(), s);
        }
        // Calculate other local state variables based on priorities
        for (int i = 0; i < goals.size(); i++) {
            int cur = localState.get(goals.get(i).id()).priority;
            // The very first view is also first in its section
            int bef = i == 0 ? (cur + 1) : localState.get(goals.get(i - 1).id()).priority;
            // The very last view should not have a divider
            int aft = i == (goals.size() - 1) ? cur : localState.get(goals.get(i + 1).id()).priority;

            // If current view is first in its section (cur < bef), enable circle view
            int cvVisibility = (cur < bef) ? View.VISIBLE : View.INVISIBLE;
            // If current view is last in its section (cur > aft), enable divider
            int dividerVisibility = (cur > aft) ? View.VISIBLE : View.GONE;

            String dueIn = Time.millisToDaysAndHrsString
                               .f(goals.get(i).endDate() - now).run()._1();

            State s = localState.get(goals.get(i).id());
            s.dueIn = dueIn;
            s.cvVisibility = cvVisibility;
            s.dividerVisibility = dividerVisibility;
        }

        notifyDataSetChanged();
    }
    private static int calcNewPriority(Goal goal, long now) {
        long millisToEnd = goal.endDate() - now;

        if (millisToEnd < 0) return 8;

        int decs = (int) (millisToEnd / goal.millisInOneLv());

        return 8 - decs - 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
            inflate(R.layout.goal_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Goal goal = goals.get(position);
        final State state = localState.get(goal.id());

        holder.tvName.setText(goal.name());
        holder.tvDueIn.setText(state.dueIn);
        holder.cv.setPriority(state.priority);
        holder.cv.setVisibility(state.cvVisibility);
        holder.llDivider.setVisibility(state.dividerVisibility);

        holder.itemView.setOnTouchListener((view, event) -> {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                startDragListener.onStartDrag(holder);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return goals == null ? 0 : goals.size();
    }

    @Override
    public void onItemDismiss(int position) {
        dispatcher.dispatch(new Action.RemoveGoalAction(goals.get(position).id()));
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cvPriority)
        CircleView cv;

        @Bind(R.id.tvName)
        TextView tvName;

        @Bind(R.id.tvDueIn)
        TextView tvDueIn;

        @Bind(R.id.divider)
        LinearLayout llDivider;

        View itemView;

        ViewHolder(View goalItemView) {
            super(goalItemView);
            ButterKnife.bind(this, goalItemView);
            this.itemView = goalItemView;
        }
    }

    private class State {
        public int priority = 1;
        public String dueIn = "";
        public int cvVisibility = View.VISIBLE;
        public int dividerVisibility = View.VISIBLE;
    }

}
