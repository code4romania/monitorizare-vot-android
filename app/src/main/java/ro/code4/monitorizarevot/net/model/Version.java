package ro.code4.monitorizarevot.net.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;


public class Version extends RealmObject implements Serializable {
    @PrimaryKey
    private String key;
    private Integer value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Version)) return false;
        Version version = (Version) o;
        return getKey().equals(version.getKey()) &&
                getValue().equals(version.getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] { getKey(), getValue() });
    }
}
