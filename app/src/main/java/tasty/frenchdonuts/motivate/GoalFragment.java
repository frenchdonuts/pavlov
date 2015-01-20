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

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class GoalFragment extends ListFragment {
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

		Realm realm = Realm.getInstance(getActivity());
		RealmResults<Goal> results = realm.allObjects(Goal.class);
		for(Goal g : results) g.setPriority(Goal.calcNewPriority(g));		// update priorities every time we display goals

		// fn(priority, endDate) -> someNumber s.t.
		// if priority1 > priority2, then fn(priority1, _) > fn(priority2, _)
		// if priority1 = priority2, and endDate1 < endDate2, then fn(priority1, endDate1) > fn(priority2, endDate2)
		// Another thing we know is that priority will be between 1 and 7 and endDate will always be a large positive number
		// fn(x, y) = (3^x)(2^y)
		// fn(x, y) = x^y; (x+1)^y vs x^(y+1)
		// fn(x, y) =

		results.sort("priority", false);

		taskAdapter = new TaskAdapter(getActivity(), results, true);
		this.setListAdapter(taskAdapter);

		SwipeDismissListViewTouchListener touchListener =
		        new SwipeDismissListViewTouchListener(
		                this.getListView(),
		                new SwipeDismissListViewTouchListener.DismissCallbacks() {
							public boolean canDismiss(int position) { return true; }
		                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
								realm.beginTransaction();
		                        for (int position : reverseSortedPositions) {
									taskAdapter.getItem(position-1).removeFromRealm();
		                        }
								realm.commitTransaction();
		                        //taskAdapter.notifyDataSetChanged();
		                    }
		                });
		this.getListView().setOnTouchListener(touchListener);
		this.getListView().setOnScrollListener(touchListener.makeScrollListener());
	}

	public class TaskAdapter extends RealmBindableAdapter<Goal> {

		public TaskAdapter(Context context,
						 RealmResults<Goal> realmResults,
						 boolean automaticUpdate) {
			super(context, realmResults, automaticUpdate);
			inflater = LayoutInflater.from(context);
		}

		public View newView(LayoutInflater inflater, int position, ViewGroup container) {
			return inflater.inflate(R.layout.goal_view, container, false);
		}

		public void bindView(Goal goal, int position, View view) {
			GoalView gv = (GoalView) view;
			gv.bindTo(goal);

			int cur = goal.getPriority();
			// The very first view is also first in its section
			int bef = position == 0 ? (cur + 1) : getItem(position - 1).getPriority();
			// The very last view should not have a divider
			int aft = position == (getCount() - 1) ? cur : getItem(position + 1).getPriority();

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
