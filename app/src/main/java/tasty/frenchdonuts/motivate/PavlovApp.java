package tasty.frenchdonuts.motivate;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by frenchdonuts on 1/8/15.
 */
public class PavlovApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
						.setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
						.setFontAttrId(R.attr.fontPath)
						.build()
		);

	}
}
