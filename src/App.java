import java.util.Objects;
import java.util.function.*;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {
        Function<String, Integer> safeStringLength = ternaryOperator(Objects::isNull, obj -> 0,  CharSequence::length);
        System.out.println(safeStringLength.apply("123"));
        System.out.format("%.7f%n", integrate(t -> Math.pow(t, 2), 0, 10));
        pseudoRandomStream(13).limit(30).forEachOrdered(System.out::print);
    }

    public static <T, U> Function<T, U> ternaryOperator(
            Predicate<? super T> condition,
            Function<? super T, ? extends U> ifTrue,
            Function<? super T, ? extends U> ifFalse) {
        return t -> condition.test(t) ? ifTrue.apply(t) : ifFalse.apply(t);
    }

    public static double integrate(DoubleUnaryOperator f, double a, double b) {
        double result = 0;
        double accuracy = 1.e-7;
        double count = (b - a) * accuracy;
        for (int i = 0; i < 1 / accuracy; i++) {
            result += f.applyAsDouble(a) * count;
            a += count;
        }
        return result;
    }

    public static IntStream pseudoRandomStream(int seed) {
        return IntStream.iterate(seed, n -> n * n / 10 % 1000);
    }
}
