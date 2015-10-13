import org.junit.Test;

import tasty.frenchdonuts.pavlov.utils.Time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by frenchdonuts on 10/8/15.
 */
public class TimeUtilsTests {

    @Test
    public void testmillisToDaysAndHrsString() {
        assertThat(Time.millisToDaysAndHrsString.f(Time.millisInDay).run()._1(), is("1 d 0 hrs "));

        long m = Time.millisInDay + Time.millisInHr;
        assertThat(Time.millisToDaysAndHrsString.f(m).run()._1(), is("1 d 1 hr "));

        m = 1444368951708L - 1444343273000L;
        assertThat(Time.millisToDaysAndHrsString.f(m).run()._1(), is("0 ds 7 hrs "));
    }
}
