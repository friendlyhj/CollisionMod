package youyihj.collision.util;


import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author youyihj
 */
public class CircleIterator<E> implements Iterator<E> {
    private Iterator<E> internal;
    private final Iterable<E> parent;

    public CircleIterator(Iterator<E> iterator, CircleIterable<E> parent) {
        this.internal = iterator;
        this.parent = parent.internal;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public E next() {
        if (!internal.hasNext())
            internal = parent.iterator();
        return internal.next();
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        throw new UnsupportedOperationException("CircleIterator cannot foreach");
    }
}
