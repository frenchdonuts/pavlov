package tasty.frenchdonuts.pavlov;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import tasty.frenchdonuts.pavlov.fragments.GoalsFragment;
import tasty.frenchdonuts.pavlov.utils.Conv;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Conv.scale = getResources().getDisplayMetrics().density;
        Conv.scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
            .add(R.id.goals, GoalsFragment.newInstance())
            .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
