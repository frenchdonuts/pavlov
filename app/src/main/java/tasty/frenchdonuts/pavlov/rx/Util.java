package tasty.frenchdonuts.pavlov.rx;

import fj.F;
import fj.F2;
import fj.F2Functions;
import fj.data.Array;
import rx.Observable;

/**
 * Created by frenchdonuts on 7/28/15.
 */
public class Util {
    // foldM :: (b -> a -> f b) -> f b -> [f a] -> f b
    public static <A,B> Observable<B> foldM(F2<B, A, Observable<B>> f, Observable<B> start, Array<Observable<A>> list) {
        return list.foldLeft((acc, observable) -> {
            return acc.flatMap(b -> {
                F<A, Observable<B>> g2 = F2Functions.curry(f).f(b);
                return observable.flatMap(a -> g2.f(a));
            });
        }, start);
    }
}
