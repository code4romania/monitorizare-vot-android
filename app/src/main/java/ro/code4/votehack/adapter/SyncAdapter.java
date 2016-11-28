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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ro.code4.votehack.constants.Sync;
import ro.code4.votehack.net.HttpCallback;
import ro.code4.votehack.net.HttpClient;
import ro.code4.votehack.net.model.Section;
import ro.code4.votehack.net.model.Version;
import ro.code4.votehack.net.model.response.VersionResponse;
import ro.code4.votehack.util.Logify;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private ContentResolver contentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        init(context);
    }

    private void init(Context context) {
        contentResolver = context.getContentResolver();
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
                RealmResults<Version> queryResult = Realm.getDefaultInstance()
                        .where(Version.class)
                        .findAll();
                Version existingVersion = queryResult.size() > 0 ? queryResult.first() : null;
                if(!versionsEqual(existingVersion, response.getVersion())) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(response.getVersion());
                    realm.commitTransaction();
                    realm.close();
                    // TODO separate code
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
        HttpClient.getInstance().getForm("A", new HttpCallback<Section[]>(Section[].class) {
            @Override
            public void onSuccess(Section[] sections) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(Arrays.asList(sections));
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onError() {

            }
        });
        HttpClient.getInstance().getForm("B", new HttpCallback<Section[]>(Section[].class) {
            @Override
            public void onSuccess(Section[] sections) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(Arrays.asList(sections));
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onError() {

            }
        });
        HttpClient.getInstance().getForm("C", new HttpCallback<Section[]>(Section[].class) {
            @Override
            public void onSuccess(Section[] sections) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(Arrays.asList(sections));
                realm.commitTransaction();
                realm.close();
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
