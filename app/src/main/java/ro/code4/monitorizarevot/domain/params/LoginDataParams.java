package ro.code4.monitorizarevot.domain.params;

public class LoginDataParams extends BaseDataParams {

    private String mPhoneNumber;

    private String mPinNumber;

    private String mUdid;

    public LoginDataParams(String phoneNumber, String pinNumber, String udid) {
        super(false);

        mPhoneNumber = phoneNumber;
        mPinNumber = pinNumber;
        mUdid = udid;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getPinNumber() {
        return mPinNumber;
    }

    public String getUdid() {
        return mUdid;
    }
}
