package ro.code4.monitorizarevot.viewmodel;


import javax.inject.Inject;

import android.support.annotation.StringRes;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;
import ro.code4.monitorizarevot.net.model.FormDetails;
import ro.code4.monitorizarevot.net.model.Version;
import ro.code4.monitorizarevot.presentation.LoadingMessageFactory;
import ro.code4.monitorizarevot.presentation.MessageFactory;

import java.util.List;

public class FormsListViewModel extends BaseViewModel {

    @Inject
    public FormsListViewModel(UseCaseFactory useCaseFactory, LoadingMessageFactory loadingMessageFactory,
                              MessageFactory messageFactory) {
        super(useCaseFactory, loadingMessageFactory, messageFactory);
    }

    public List<Version> getFormVersions() {
        return Data.getInstance().getFormVersions();
    }

    public List<FormDetails> getFormDetails() {
        return Data.getInstance().getFormDetails();
    }
}
