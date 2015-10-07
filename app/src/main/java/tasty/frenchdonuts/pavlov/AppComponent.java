package tasty.frenchdonuts.pavlov;

import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Singleton;

import dagger.Component;
import tasty.frenchdonuts.pavlov.db.DbModule;
import tasty.frenchdonuts.pavlov.fragments.GoalsFragment;
import tasty.frenchdonuts.pavlov.views.AddGoalFormView;
import tasty.frenchdonuts.pavlov.views.GoalItemAdapter;

/**
 * Created by frenchdonuts on 9/26/15.
 */
@Singleton
@Component(
    modules = {
        AppModule.class,
        DbModule.class,
        // Block off access to Handlers class (only Dispatcher should have access to Handlers)
        HandlersModule.class
    }
)
public interface AppComponent {
    void inject(@NonNull GoalsFragment fragment);
    void inject(@NonNull AddGoalFormView view);
    void inject(@NonNull GoalItemAdapter adapter);
}
