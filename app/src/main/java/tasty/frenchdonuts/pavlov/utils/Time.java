package tasty.frenchdonuts.pavlov.utils;

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
public class Time {
    public static final long millisInHr = 60 * 60 * 1000;
    public static final long millisInDay = 24 * 60 * 60 * 1000;
    public static final long millisInMonth = millisInDay * 30;
    public static final long millisInYear = millisInDay * 365;

    // millisToDays :: Long -> (Integer, Long)
    public static F<Long, P2<Integer, Long>> millisToDays = (millis) ->
        p((int) (millis / millisInDay), millis % millisInDay);


    // millisToHrs :: Long -> (Integer, Long)
    public static F<Long, P2<Integer, Long>> millisToHrs = (millis) ->
        p((int) (millis / millisInHr), millis % millisInHr);


    // pluralize :: Integer -> String -> String
    public static F2<Integer, String, String> pluralize = (n, singular) ->
        n == 1 ? (n + " " + singular + " ") : (n + " " + singular + "s ");

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

    // millisToDaysAndHrsString :: Long -> Write String Long
    public static F<Long, Writer<String, Long>> millisToDaysAndHrsString = (millis) ->
        unit(millis).flatMap(millisToDaysString).flatMap(millisToHrsString);

    public static long endDate(String ds, String mos, String yrs) {
        int days = 1;
        if (!ds.isEmpty())
            days = Integer.parseInt(ds);
        int months = 0;
        if (!mos.isEmpty()) months = Integer.parseInt(mos);
        int years = 0;
        if (!yrs.isEmpty()) years = Integer.parseInt(yrs);


        // Add one more day to account for integer truncation upon GoalView.millisToDateString
        long daysInMillis = days * millisInDay;
        long monthsInMillis = months * millisInMonth;
        long yrsInMillis = years * millisInYear;

        return daysInMillis + monthsInMillis + yrsInMillis + System.currentTimeMillis();
    }
}

