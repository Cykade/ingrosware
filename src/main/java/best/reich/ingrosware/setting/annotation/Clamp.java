package best.reich.ingrosware.setting.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * made for Ingros
 *
 * @author Brennan
 * editted by neva lack
 * @since 6/13/2020
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Clamp {
    double min() default 0;

    double max();

    double inc() default 0.05D;
}
