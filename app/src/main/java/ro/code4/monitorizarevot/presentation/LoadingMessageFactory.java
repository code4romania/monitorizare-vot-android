package ro.code4.monitorizarevot.presentation;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.domain.usecase.UseCaseType;

@Singleton
public class LoadingMessageFactory {

    private final Context mContext;

    @Inject
    public LoadingMessageFactory(Context context) {
        mContext = context;
    }

    public LoadingMessage getMessage(UseCaseType type) {
        return new LoadingMessage(mContext.getString(R.string.please_wait));
    }
}
