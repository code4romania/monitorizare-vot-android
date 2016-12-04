package ro.code4.monitorizarevot.db;

import com.pixplicity.easyprefs.library.Prefs;

public class Preferences {
    private static final String PREFS_COUNTY_CODE = "PREFS_COUNTY_CODE";
    private static final String PREFS_BRANCH_NUMBER = "PREFS_BRANCH_NUMBER";
    private static final String PREFS_USERNAME = "PREFS_USERNAME";
    private static final String PREFS_TOKEN = "PREFS_TOKEN";


    public static void clear() {
        Prefs.clear();
    }

    public static void saveCountyCode(String countyCode) {
        Prefs.putString(PREFS_COUNTY_CODE, countyCode);
    }

    public static String getCountyCode() {
        return Prefs.getString(PREFS_COUNTY_CODE, "");
    }

    public static void saveBranchNumber(int number) {
        Prefs.putInt(PREFS_BRANCH_NUMBER, number);
    }

    public static int getBranchNumber() {
        return Prefs.getInt(PREFS_BRANCH_NUMBER, -1);
    }

    public static void saveUsername(String username){
        Prefs.putString(PREFS_USERNAME, username);
    }

    public static String getUsername() {
        return Prefs.getString(PREFS_USERNAME, null);
    }

    public static void saveToken(String token) {
        Prefs.putString(PREFS_TOKEN, token);
    }

    public static String getToken() {
        return Prefs.getString(PREFS_TOKEN, null);
    }

}
