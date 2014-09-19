package common.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import common.annotations.JsonExclude;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:23:42
 */
public class SkipStrategy implements ExclusionStrategy {

    private final Class<?> clazz;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public SkipStrategy() {
        super();
        this.clazz = null;
    }
    public SkipStrategy(final Class<?> clazz) {
        super();
        this.clazz = clazz;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public boolean shouldSkipClass(final Class<?> clazz) {
        return this.clazz == clazz || clazz.getAnnotation(JsonExclude.class) != null;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see
    // com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public boolean shouldSkipField(final FieldAttributes field) {
        return field.getAnnotation(JsonExclude.class) != null;
    }
}
