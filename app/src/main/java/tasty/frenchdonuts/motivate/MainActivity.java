package tasty.frenchdonuts.motivate;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.getbase.floatingactionbutton.AddFloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends ActionBarActivity {

	private Realm realm;

	@InjectView(R.id.etTitle)
	EditText etTitle;
	@InjectView(R.id.etLv)
	EditText etLv;
	@InjectView(R.id.etDueInDays)
	EditText etDueInDays;
	@InjectView(R.id.etDueInMonths)
	EditText etDueInMonths;
	@InjectView(R.id.etDueInYears)
	EditText etDueInYears;
	@InjectView(R.id.btnAddGoal)
	AddFloatingActionButton fabAddGoal;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Realm.getInstance(this).close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		findViewById(R.id.mainLayout).requestFocus();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Conv.scale = getResources().getDisplayMetrics().density;
		Conv.scaledDensity = getResources().getDisplayMetrics().scaledDensity;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		etTitle.setOnEditorActionListener((view, actionId, event) -> {
			boolean handled = false;
			if(actionId == EditorInfo.IME_ACTION_NEXT) {
				etLv.requestFocus();
			}
			return handled;
		});

		etLv.setOnEditorActionListener((view, actionId, event) -> {
			boolean handled = false;
			if(actionId == EditorInfo.IME_ACTION_NEXT) {
				etDueInDays.requestFocus();
			}
			return handled;
		});
		// Make sure etLv input stays between 1 and 7

		etDueInDays.setOnEditorActionListener((view, actionId, event) -> {
			boolean handled = false;
			if(actionId == EditorInfo.IME_ACTION_NEXT) {
				etDueInMonths.requestFocus();
			}
			return handled;
		});

		etDueInMonths.setOnEditorActionListener((view, actionId, event) -> {
			boolean handled = false;
			if(actionId == EditorInfo.IME_ACTION_NEXT) {
				etDueInYears.requestFocus();
			}
			return handled;
		});

		Realm.deleteRealmFile(this);
		realm = Realm.getInstance(this);
		realm.beginTransaction();
		realm.clear(Task.class);
		realm.commitTransaction();

		fabAddGoal.setOnClickListener((btn) -> {
			realm.beginTransaction();

			Task task = realm.createObject(Task.class);
			task.setTitle(etTitle.getText().toString());
			int lv = 1;
			String lvString = etLv.getText().toString();
			if (!lvString.isEmpty()) lv = Integer.parseInt(lvString);
			task.setInit_priority(lv);
			task.setPriority(lv);
			task.setEndDate(endDate());

			realm.commitTransaction();

			// Clear all EditTexts
			findViewById(R.id.mainLayout).requestFocus();
		});
	}

	public static long millisInDay = 3600 * 24 * 1000;
	public static long millisInMonth = (long) millisInDay * 30;
	public static long millisInYear = (long) millisInMonth * 12;
	public long endDate() {
		String dueInDays = etDueInDays.getText().toString();
		String dueInMonths = etDueInMonths.getText().toString();
		String dueInYears = etDueInYears.getText().toString();

		int days = 1;
		if (!dueInDays.isEmpty())
			days = Integer.parseInt(dueInDays);
		int months = 0;
		if (!dueInMonths.isEmpty()) months = Integer.parseInt(dueInMonths);
		int years = 0;
		if (!dueInYears.isEmpty()) years = Integer.parseInt(dueInYears);

		long daysInMillis = (days + 1) * millisInDay;
		// TODO: make months calculation more accurate
		long monthsInMillis = months * millisInMonth;
		long yrsInMillis = years * millisInYear;

		return daysInMillis + monthsInMillis + yrsInMillis + System.currentTimeMillis();
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
