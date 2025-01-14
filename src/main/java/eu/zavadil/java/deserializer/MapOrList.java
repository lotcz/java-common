package eu.zavadil.java.deserializer;

import java.util.List;
import java.util.Map;

/**
 * This abomination (looking in your general direction, Upgates) is for cases when a JSON property is sometimes an array and sometimes a map.
 */
public class MapOrList<ValueType> {

    private final List<ValueType> listProperty;

    private final Map<String, ValueType> mapProperty;

    public MapOrList(List<ValueType> property) {
        this.listProperty = property;
        this.mapProperty = null;
    }

    public MapOrList(Map<String, ValueType> property) {
        this.listProperty = null;
        this.mapProperty = property;
    }

    public boolean hasValue() {
        return this.isList() || this.isMap();
    }

    public boolean isEmpty() {
        return !this.hasValue();
    }

    public boolean isMap() {
        return this.mapProperty != null;
    }

    public boolean isList() {
        return this.listProperty != null;
    }

    public Map<String, ValueType> asMap() {
        return this.mapProperty;
    }

    public List<ValueType> asList() {
        if (this.isList()) return this.listProperty;
        return this.isMap() ? this.mapProperty.values().stream().toList() : null;
    }

}
