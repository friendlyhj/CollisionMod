package youyihj.collision.util;

import java.util.function.Function;

/**
 * @author youyihj
 */
@SuppressWarnings("rawtypes")
public class Lazy<T> {
    private Lazy() {
        value = null;
    }

    private Lazy(Object obj) {
        value = obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> Lazy<T> of(T t) {
        return t == null ? NULL : new Lazy<>(t);
    }

    @SuppressWarnings({"unchecked"})
    public <R> Lazy<R> map(Function<T, R> mapper) {
        return this.isNull() ? NULL : of(mapper.apply((T) this.value));
    }

    @SuppressWarnings("unchecked")
    public T orElse(T nullResult) {
        return this.isNull() ? nullResult : (T) this.value;
    }

    public T get() {
        return orElse(null);
    }

    private static final Lazy NULL = new Lazy();

    public boolean isNull() {
        return this == NULL;
    }

    private final Object value;
}
