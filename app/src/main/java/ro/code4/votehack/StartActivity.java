package ro.code4.votehack;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import ro.code4.votehack.constants.Auth;

public class StartActivity extends BaseActivity {
    private Account account;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        account = createSyncAccount(this);
    }

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(Auth.ACCOUNT, Auth.ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            return newAccount;
        } else {
            return null;
        }
    }
}
