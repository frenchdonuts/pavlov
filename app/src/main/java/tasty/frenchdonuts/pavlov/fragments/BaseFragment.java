package tasty.frenchdonuts.pavlov.fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Properly handles subscriptions to Observables
 *  Unsubscribe from all onStop
 */
public class BaseFragment extends Fragment {

    @NonNull
    CompositeSubscription compositeSubscriptionForOnStop = new CompositeSubscription();
    protected void unsubscribeOnStop(Subscription subscription) {
        compositeSubscriptionForOnStop.add(subscription);
    }

    @Override
    public void onStop() {
        compositeSubscriptionForOnStop.clear();
        super.onStop();
    }
}
