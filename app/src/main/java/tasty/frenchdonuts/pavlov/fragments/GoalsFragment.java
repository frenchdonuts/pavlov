package tasty.frenchdonuts.pavlov.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tasty.frenchdonuts.pavlov.PavlovApp;
import tasty.frenchdonuts.pavlov.R;
import tasty.frenchdonuts.pavlov.db.entities.Goal;
import tasty.frenchdonuts.pavlov.db.tables.GoalsTable;
import tasty.frenchdonuts.pavlov.views.GoalItemAdapter;
import tasty.frenchdonuts.pavlov.views.helper.OnStartDragListener;
import tasty.frenchdonuts.pavlov.views.helper.SwipeItemTouchHelperCallback;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class GoalsFragment extends BaseFragment implements OnStartDragListener {
    private static final String TAG = GoalsFragment.class.getSimpleName();

    @Inject
    StorIOSQLite storIOSQLite;

    @Bind(R.id.rvGoals)
    RecyclerView rvGoals;

    GoalItemAdapter goalsItemAdapter;
    ItemTouchHelper itemTouchHelper;

    public GoalsFragment() {
    }

    public static Fragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        PavlovApp.get(getContext()).appComponent().inject(this);
        goalsItemAdapter = new GoalItemAdapter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return inflater.inflate(R.layout.goals_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreate");
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        view.setBackgroundColor(Color.WHITE);

        rvGoals.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvGoals.setHasFixedSize(true);
        rvGoals.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        rvGoals.setVerticalScrollBarEnabled(false);

        goalsItemAdapter = new GoalItemAdapter(this.getContext(), this);
        rvGoals.setAdapter(goalsItemAdapter);

        itemTouchHelper = new ItemTouchHelper(new SwipeItemTouchHelperCallback(goalsItemAdapter));
        itemTouchHelper.attachToRecyclerView(rvGoals);
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    void reloadData() {
        Log.i(TAG, "reloadData");
        final String orderBy = GoalsTable.COLUMN_PRIORITY + " desc, " +
                               GoalsTable.COLUMN_END_DATE + " asc";
        Query query = Query.builder()
            .table(GoalsTable.TABLE)
            .orderBy(orderBy)
            .build();
        final Subscription subscription = storIOSQLite
            .get()
            .listOfObjects(Goal.class)
            .withQuery(query)
            .prepare()
            .createObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(goalsItemAdapter::setGoals);

        unsubscribeOnStop(subscription);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
