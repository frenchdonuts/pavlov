package tasty.frenchdonuts.motivate;

import fj.F;
import fj.F2;
import fj.Monoid;
import fj.P2;
import fj.data.Writer;

import static fj.P.p;
import static fj.data.Writer.unit;

/**
 * Created by frenchdonuts on 7/22/15.
 */
public class CountdownTimer {
    public static final long millisInHr = 60 * 60 * 1000;
    public static final long millisInDay = 24 * 60 * 60 * 1000;

    // millisToDays :: Long -> (Integer, Long)
    public static F<Long, P2<Integer, Long>> millisToDays = (millis) -> {
        return p( (int) (millis/millisInDay), millis % millisInDay);
    };

    // millisToHrs :: Long -> (Integer, Long)
    public static F<Long, P2<Integer, Long>> millisToHrs = (millis) -> {
        return p( (int) (millis/millisInHr), millis % millisInHr );
    };

    // pluralize :: Integer -> String -> String
    public static F2<Integer, String, String> pluralize = (n, singular) ->
            n == 1 ? (n + " " + singular + " ") : (n + " " + singular + "s ");

/*    TODO: Can generalize following functions to be higher-order
            Input: (Long, F<Integer, B>)
            Output: Writer<B, Long>
            This way the "render type" (in this case String) is not restricted
*/
    // millisToDaysString :: Long -> Writer String Long
    public static F<Long, Writer<String, Long>> millisToDaysString = (millis) -> {
        P2<Integer, Long> pair = millisToDays.f(millis);
        return unit(pair._2(), pluralize.f(pair._1(), "d"), Monoid.stringMonoid);
    };

    // millisToHrsString :: Long -> Write String Long
    public static F<Long, Writer<String, Long>> millisToHrsString = (millis) -> {
        P2<Integer, Long> pair = millisToHrs.f(millis);
        return unit(pair._2(), pluralize.f(pair._1(), "hr"), Monoid.stringMonoid);
    };

    /** TODO: can generalize this function to be higher-order
     *        Input: As many F<Long, Writer<B, Long> (a list of them?)
     *        Output: Writer<B, Long>
     *        Maybe we can use fold or something
     */
    // millisToDaysAndHrsString :: Long -> Write String Long
    public static F<Long, Writer<String, Long>> millisToDaysAndHrsString = (millis) ->
        unit(millis).flatMap(millisToDaysString).flatMap(millisToHrsString);
}
