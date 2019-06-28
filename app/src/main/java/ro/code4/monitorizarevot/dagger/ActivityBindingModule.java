package ro.code4.monitorizarevot.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ro.code4.monitorizarevot.LoginActivity;
import ro.code4.monitorizarevot.StartActivity;
import ro.code4.monitorizarevot.ToolbarActivity;

@Module(includes = {ViewModelModule.class})
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract StartActivity activityStart();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector(modules = {FragmentBindingModule.class})
    abstract ToolbarActivity toolbarActivity();


}
