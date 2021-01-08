package youyihj.collision.util;

import java.lang.annotation.Annotation;

/**
 * @author youyihj
 */
public class AnnotationUtil {
    private AnnotationUtil() {}

    public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    public static boolean hasAnnotation(Object obj, Class<? extends Annotation> annotation) {
        return hasAnnotation(obj.getClass(), annotation);
    }

    public static boolean hasNotAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return !clazz.isAnnotationPresent(annotation);
    }

    public static boolean hasNotAnnotation(Object obj, Class<? extends Annotation> annotation) {
        return !hasAnnotation(obj.getClass(), annotation);
    }
}
