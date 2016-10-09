package ro.code4.votehack.net.model.response;

import java.io.Serializable;

import ro.code4.votehack.net.model.Version;

public class VersionResponse implements Serializable {
    private Version versiune;

    public Version getVersion() {
        return versiune;
    }
}
