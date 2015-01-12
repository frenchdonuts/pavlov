package tasty.frenchdonuts.motivate;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class TaskFragment extends ListFragment {
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

		RealmResults<Task> results = Realm.getInstance(getActivity())
				.allObjects(Task.class);
		results.sort("priority", false);
		taskAdapter = new TaskAdapter(getActivity(), results, true);
		this.setListAdapter(taskAdapter);
	}

	public class TaskAdapter extends RealmBindableAdapter<Task> {

		public TaskAdapter(Context context,
						 RealmResults<Task> realmResults,
						 boolean automaticUpdate) {
			super(context, realmResults, automaticUpdate);
			inflater = LayoutInflater.from(context);
		}

		public View newView(LayoutInflater inflater, int position, ViewGroup container) {
			return inflater.inflate(R.layout.goal_view, container, false);
		}

		public void bindView(Task task, int position, View view) {
			GoalView gv = (GoalView) view;
			gv.bindTo(task);

			int cur = task.getPriority();
			// The very first view will of course be first in its section
			int bef = position == 0 ? (cur + 1) : getItem(position - 1).getPriority();
			// The very last view should not have a divider
			int aft = position == (getCount() - 1) ? cur : getItem(position + 1).getPriority();

			// 7, 6, 5, 4
			// If current view is first in its section (cur < bef), enable circle view; else disable
			gv.enableCircleView(cur < bef);
			// If current view is last in its section (cur > aft), enable divider; else disable
			gv.enableDivider(cur > aft);
		}

		public RealmResults<Task> getRealmResults() {
			return realmResults;
		}
	}

	public void temp() {
		// [7,7,7,6,5,4,4,3,2,1] -> [3,4,5,7,8,9] OR [2,3,4,6,7,8]
		int[] arr = { 7,7,7,6,5,4,4,3,2,1 };
		List<Integer> ans = new ArrayList<Integer>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != arr[i+1]) {
				ans.add(i+1);
			}
		}
	}
}
