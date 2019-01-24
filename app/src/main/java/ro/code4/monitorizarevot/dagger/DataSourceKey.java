package ro.code4.monitorizarevot.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;
import ro.code4.monitorizarevot.data.datasource.ApiDataSource;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface DataSourceKey {

    Class<? extends ApiDataSource> value();
}
