package tasty.frenchdonuts.motivate;

/**
 * Created by frenchdonuts on 1/8/15.
 */
public class Conv {
	// Get the screen's density scale
	public static float scale;
	public static float scaledDensity;
	// Convert the dps to pixels, based on density scale
	public static int scale(int dp) {
		return (int) (dp * scale + 0.5f);
	}
	public static int scaleSp(int sp) { return (int) (sp * scaledDensity); }
}
