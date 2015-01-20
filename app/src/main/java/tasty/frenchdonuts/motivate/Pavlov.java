package tasty.frenchdonuts.motivate;

import android.app.Application;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by frenchdonuts on 1/8/15.
 */
public class Pavlov extends Application {
	public static Calendar calendar;

	@Override
	public void onCreate() {
		super.onCreate();
		calendar = Calendar.getInstance();
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
						.setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
						.setFontAttrId(R.attr.fontPath)
						.build()
		);

	}
}
