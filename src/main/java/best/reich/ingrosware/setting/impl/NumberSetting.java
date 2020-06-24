package best.reich.ingrosware.setting.impl;

import best.reich.ingrosware.setting.AbstractSetting;
import best.reich.ingrosware.util.math.MathUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Field;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/13/2020
 **/
public class NumberSetting<T extends Number> extends AbstractSetting<T> {
    private final T inc;
    private final Class cls;
    private final T min;
    private final T max;

    public NumberSetting(String label, Object object, Field field, T min, T maximum, T inc) {
        super(label, object, field);
        this.min = min;
        this.max = maximum;
        this.inc = inc;
        cls = field.getType();
    }

    public T getInc() {
        return inc;
    }

    @Override
    public void setValue(T value) {
        super.setValue(MathUtil.clamp(value, min, max));
    }

    @Override
    public void setValue(String value) {
        try {
            if (cls == int.class) {
                setValue((T) NumberUtils.createInteger(value));
            } else if (cls == double.class) {
                setValue((T) NumberUtils.createDouble(value));
            } else if (cls == float.class) {
                setValue((T) NumberUtils.createFloat(value));
            } else if (cls == long.class) {
                setValue((T) NumberUtils.createLong(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
