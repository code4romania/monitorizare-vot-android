package ro.code4.monitorizarevot.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.widget.Toast;

import ro.code4.monitorizarevot.App;
import ro.code4.monitorizarevot.R;
import ro.code4.monitorizarevot.db.Preferences;

import static ro.code4.monitorizarevot.constants.Sync.ACCOUNT_TYPE;
import static ro.code4.monitorizarevot.constants.Sync.AUTHORITY;

public class AuthUtils {

    public static Account createSyncAccount(Context context) {
        String username = Preferences.getUsername();
        Account newAccount = new Account(username, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        }
        try {
            return accountManager.getAccountsByType(ACCOUNT_TYPE)[0];
        } catch (SecurityException e) {
            Toast.makeText(context, context.getString(R.string.error_permission, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static void initAccount(String username, String password, String token) {
        Preferences.saveUsername(username);
        Preferences.saveToken(token);

        AccountManager accountManager = AccountManager.get(App.getContext());
        Account account = new Account(username, ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(account, password, null);

        accountManager.setAuthToken(account, ACCOUNT_TYPE, token);
    }

    public static void removeAccountAndStopSync() {
        Context context = App.getContext();
        try {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            for (Account account : accounts) {
                if (account.type.equals(ACCOUNT_TYPE)) {
                    ContentResolver.setSyncAutomatically(account, AUTHORITY, false);
                    accountManager.removeAccount(account, null, null);
                }
            }
        } catch (SecurityException e) {
            Toast.makeText(context, context.getString(R.string.error_permission, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
        }
    }
}
