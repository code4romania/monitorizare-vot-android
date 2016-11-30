package ro.code4.votehack.adapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;

import ro.code4.votehack.constants.Sync;
import ro.code4.votehack.db.Data;
import ro.code4.votehack.net.HttpCallback;
import ro.code4.votehack.net.HttpClient;
import ro.code4.votehack.net.model.Form;
import ro.code4.votehack.net.model.Version;
import ro.code4.votehack.net.model.response.VersionResponse;
import ro.code4.votehack.util.Logify;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Logify.d("SyncAdapter", "performing sync");

        getFormsDefinition();
    }

    private void getFormsDefinition() {
        HttpClient.getInstance().getFormVersion(new HttpCallback<VersionResponse>(VersionResponse.class) {
            @Override
            public void onSuccess(VersionResponse response) {
                Version existingVersion = Data.getInstance().getFormVersion();
                if(!versionsEqual(existingVersion, response.getVersion())) {
                    Data.getInstance().saveFormsVersion(response.getVersion());
                    // TODO clear any saved question when definition changes
                    getForms();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private boolean versionsEqual(Version before, Version current) {
        return (before != null)
                && before.getA().equals(current.getA())
                && before.getB().equals(current.getB())
                && before.getC().equals(current.getC());
    }

    private void getForms() {
        HttpClient.getInstance().getForm("A", new HttpCallback<Form[]>(Form[].class) {
            @Override
            public void onSuccess(Form[] forms) {
                Data.getInstance().saveFormsDefinition(Arrays.asList(forms));
            }

            @Override
            public void onError() {

            }
        });
        HttpClient.getInstance().getForm("B", new HttpCallback<Form[]>(Form[].class) {
            @Override
            public void onSuccess(Form[] forms) {
                Data.getInstance().saveFormsDefinition(Arrays.asList(forms));
            }

            @Override
            public void onError() {

            }
        });
        HttpClient.getInstance().getForm("C", new HttpCallback<Form[]>(Form[].class) {
            @Override
            public void onSuccess(Form[] forms) {
                Data.getInstance().saveFormsDefinition(Arrays.asList(forms));
            }

            @Override
            public void onError() {

            }
        });
    }

    public static void requestSync(Context context) {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(createSyncAccount(context), Sync.AUTHORITY, settingsBundle);
    }

    private static Account createSyncAccount(Context context) {
        Account newAccount = new Account(Sync.ACCOUNT, Sync.ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        }
        try {
            return accountManager.getAccountsByType(Sync.ACCOUNT_TYPE)[0];
        } catch (SecurityException e) {
            Toast.makeText(context, "Eroare permisiune " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
