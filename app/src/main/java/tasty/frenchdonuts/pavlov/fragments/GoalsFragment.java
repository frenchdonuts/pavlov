package tasty.frenchdonuts.pavlov.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tasty.frenchdonuts.pavlov.R;
import tasty.frenchdonuts.pavlov.viewmodels.GoalsViewModel;
import tasty.frenchdonuts.pavlov.views.GoalsView;

/**
 * Created by frenchdonuts on 1/6/15.
 */
public class GoalsFragment extends Fragment {
    private static final String TAG = GoalsFragment.class.getSimpleName();
	private GoalsViewModel goalsViewModel;
    private GoalsView goalsView;

    public GoalsFragment() {}
    public static Fragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        // Instantiate GoalsViewModel
        goalsViewModel = new GoalsViewModel();

        // Tell GoalsViewModel to kick off data query?
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
		return inflater.inflate(R.layout.goals_fragment, container, false);
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreate");
        super.onViewCreated(view, savedInstanceState);
        this.goalsView = (GoalsView) view.findViewById(R.id.goals_view);
        // goalsViewModel.subscribeToDataStore();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        goalsView.setViewModel(goalsViewModel);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        goalsView.setViewModel(null);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView");
        super.onDestroyView();
        // goalsViewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        // goalsViewModel.dispose();
        // goalsViewModel = null;
    }
}
