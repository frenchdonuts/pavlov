package tasty.frenchdonuts.motivate;

import android.app.Activity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by frenchdonuts on 7/22/15.
 */
public class GoalsVM {

	public static RealmResults<Goal> goals(Activity activity) {
   		Realm realm = Realm.getInstance(activity);
		RealmResults<Goal> results = realm.allObjects(Goal.class);
		for(Goal g : results) g.setPriority(Goal.calcNewPriority(g));		// update priorities every time we display goals

		results.sort("priority", false, "endDate", true);

		return results;
	}

	public static void dismissGoal(Activity activity, int[] reverseSortedPositions, GoalsFragment.TaskAdapter taskAdapter) {
		Realm realm = Realm.getInstance(activity);
		realm.beginTransaction();

        for (int position : reverseSortedPositions)
			taskAdapter.getItem(position-1).removeFromRealm();

		realm.commitTransaction();
	}

	public static boolean canDismiss(int postion) {
		return true;
	}
}
