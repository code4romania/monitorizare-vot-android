package ro.code4.monitorizarevot.viewmodel;


import javax.inject.Inject;

import android.support.annotation.StringRes;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.db.Data;
import ro.code4.monitorizarevot.domain.usecase.UseCaseFactory;
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

    // TODO: move this property in the Version class once API is refactored
    public @StringRes int getLetterForFormVersion(Version version) {
        switch (version.getKey()) {
            case "A": return R.string.form_1_letter;

            case "B": return R.string.form_2_letter;

            case "C": return R.string.form_3_letter;

            default: return R.string.empty;
        }
    }

    // TODO: move this property in the Version class once API is refactored
    public @StringRes int getDescriptionForFormVersion(Version version) {
        switch (version.getKey()) {
            case "A": return R.string.form_1;

            case "B": return R.string.form_2;

            case "C": return R.string.form_3;

            default: return R.string.empty;
        }
    }
}
