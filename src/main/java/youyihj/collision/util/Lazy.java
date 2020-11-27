package youyihj.collision.util;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

/**
 * It is much like Optional of jdk, but a custom one.
 * @author youyihj
 * @see java.util.Optional
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
    public static <T> Lazy<T> of(@Nullable T t) {
        return t == null ? NULL : new Lazy<>(t);
    }

    @SuppressWarnings("unchecked")
    public <R> Lazy<R> map(Function<T, R> mapper) {
        Objects.requireNonNull(mapper);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lazy<?> lazy = (Lazy<?>) o;
        return Objects.equals(value, lazy.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        if (this.isNull()) {
            return "Lazy{NULL}";
        }
        return "Lazy{" +
                "value=" + value +
                '}';
    }
}
