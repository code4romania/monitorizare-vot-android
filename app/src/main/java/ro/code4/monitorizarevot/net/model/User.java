package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private String phone;

    @Expose
    private String pin;

    @Expose
    private String udid;

    public User(String phone, String pin, String udid) {
        this.phone = phone;
        this.pin = pin;
        this.udid = udid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }
}
