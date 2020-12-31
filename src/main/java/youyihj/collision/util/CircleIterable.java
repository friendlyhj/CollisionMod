package youyihj.collision.util;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author youyihj
 */
public class CircleIterable<T> implements Iterable<T> {
    final Iterable<T> internal;

    private CircleIterable(Iterable<T> iterable) {
        this.internal = iterable;
    }

    public static <T> CircleIterable<T> of(Iterable<T> iterable) {
        return new CircleIterable<>(iterable);
    }

    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return new CircleIterator<>(internal.iterator(), this);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        throw new UnsupportedOperationException("CircleIterable cannot foreach");
    }
}
