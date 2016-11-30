package ro.code4.votehack.net.model.response;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import ro.code4.votehack.net.model.Version;

public class VersionResponse implements Serializable {
    @Expose
    private Version versiune;

    public Version getVersion() {
        return versiune;
    }
}
