package tasty.frenchdonuts.pavlov;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import tasty.frenchdonuts.pavlov.utils.Conv;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Conv.scale = getResources().getDisplayMetrics().density;
        Conv.scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm.getInstance(this).close();
    }

}
