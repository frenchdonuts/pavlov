package tasty.frenchdonuts.pavlov.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import tasty.frenchdonuts.pavlov.R;
import tasty.frenchdonuts.pavlov.states.GoalItemViewState;

/**
 * Created by frenchdonuts on 7/27/15.
 */
public class GoalItemAdapter extends BaseAdapter {
    private static final String TAG = GoalItemAdapter.class.getSimpleName();

    private Context context;
    private List<Observable<GoalItemViewState>> viewStates;

    public GoalItemAdapter(Context context) {
        this.context = context;
        this.viewStates = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return viewStates.size();
    }

    @Override
    public Observable<GoalItemViewState> getItem(int position) {
        return viewStates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;//getItem(position).primaryKey();
    }

    public void updateData(List<Observable<GoalItemViewState>> viewStates) {
        this.viewStates = viewStates;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoalItemView v;

        if (convertView == null)
            v = (GoalItemView) LayoutInflater.from(context).inflate(R.layout.goal_item_view, parent, false);
        else
            v = (GoalItemView) convertView;

        v.setStateObservable(getItem(position));

        return v;
    }

}
