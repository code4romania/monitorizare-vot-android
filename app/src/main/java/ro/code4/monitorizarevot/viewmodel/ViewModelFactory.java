package ro.code4.monitorizarevot.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import ro.code4.monitorizarevot.util.AbstractComponentFactory;

public class ViewModelFactory extends AbstractComponentFactory<ViewModel> implements ViewModelProvider.Factory {

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        super(creators);
    }
}
