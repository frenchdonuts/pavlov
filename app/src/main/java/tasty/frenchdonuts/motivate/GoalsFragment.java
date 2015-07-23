package tasty.frenchdonuts.motivate;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import io.realm.RealmResults;

/**
 * Created by frenchdonuts on 1/6/15.
 * View in MVVM
 */
public class GoalsFragment extends ListFragment {
	private TaskAdapter taskAdapter;
	private LayoutInflater inflater;

	public View createHeader() {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, Conv.scale(36));
		View tv = new View(getActivity());
		tv.setLayoutParams(lp);
		return tv;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		inflater = LayoutInflater.from(this.getActivity());
		this.getView().setBackgroundColor(Color.WHITE);
		this.getListView().setDivider(null);
		this.getListView().setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		this.getListView().setVerticalScrollBarEnabled(false);
		this.getListView().addHeaderView(createHeader());


        taskAdapter = new GoalsFragment.TaskAdapter(getActivity(), GoalsVM.goals(this.getActivity()), true);
		this.setListAdapter(taskAdapter);

		SwipeDismissListViewTouchListener touchListener =
		        new SwipeDismissListViewTouchListener(
		                this.getListView(),
		                new SwipeDismissListViewTouchListener.DismissCallbacks() {
							public boolean canDismiss(int position) { return GoalsVM.canDismiss(position); }

		                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                GoalsVM.dismissGoal(
                                        GoalsFragment.this.getActivity(),
                                        reverseSortedPositions,
                                        taskAdapter);
		                    }
		                });

		this.getListView().setOnTouchListener(touchListener);
		this.getListView().setOnScrollListener(touchListener.makeScrollListener());
	}

    // make this TaskAdapter exAnds RealmBindablAdapter<GoalMV>
   	public class TaskAdapter extends RealmBindableAdapter<Goal> {

		public TaskAdapter(Context context,
						 RealmResults<Goal> realmResults,
						 boolean automaticUpdate) {
			super(context, realmResults, automaticUpdate);
			inflater = LayoutInflater.from(context);
		}

		public View newView(LayoutInflater inflater, int position, ViewGroup container) {
            return new GoalView(this.context);
		}

		public void bindView(Goal goal, int position, View view) {
			GoalView gv = (GoalView) view;
			gv.bindTo(goal);

			int cur = goal.getPriority();
			// The very first view is also first in its section
			int bef = position == 0 ? (cur + 1) : getItem(position - 1).getPriority();
			// The very last view should not have a divider
			int aft = position == (getCount() - 1) ? cur : getItem(position + 1).getPriority();

			/**
			 * Instead of the code below, we could create an Observable that fires
			 */
			// 7, 6, 5, 4
			// If current view is first in its section (cur < bef), enable circle view; else disable
			gv.enableCircleView(cur < bef);
			// If current view is last in its section (cur > aft), enable divider; else disable
			gv.enableDivider(cur > aft);
			gv.render();
		}

		public RealmResults<Goal> getRealmResults() {
			return realmResults;
		}
	}
}
