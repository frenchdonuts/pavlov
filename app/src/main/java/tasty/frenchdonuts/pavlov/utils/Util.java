package tasty.frenchdonuts.pavlov.utils;

import fj.F;
import fj.F2;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by frenchdonuts on 7/24/15.
 */
public class Util {

    public static <A,B> F<A,B> toF(Func1<A, B> f) {
        return (a) -> f.call(a);
    }
    public static <A,B> Func1<A,B> toFunc1(F<A, B> f) {
        return (a) -> f.f(a);
    }

    public static <A,B,C> F2<A,B,C> toF2(Func2<A,B,C> f) {
        return (a, b) -> f.call(a, b);
    }

    public static <A,B,C> Func2<A,B,C> toFunc2(F2<A,B,C> f) {
        return (a, b) -> f.f(a, b);
    }
}
