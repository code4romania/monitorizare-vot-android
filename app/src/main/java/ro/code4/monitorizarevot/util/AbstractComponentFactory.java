package ro.code4.monitorizarevot.util;

import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Provider;

public class AbstractComponentFactory<C> {

    protected final Map<Class<? extends C>, Provider<C>> creators;

    public AbstractComponentFactory(Map<Class<? extends C>, Provider<C>> creators) {
        this.creators = creators;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <T extends C> T create(@NonNull Class<T> modelClass) {
        Provider<? extends C> creator = creators.get(modelClass);

        if (creator == null) {
            for (Map.Entry<Class<? extends C>, Provider<C>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }

        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }

        try {
            return (T) creator.get();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
