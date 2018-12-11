package ro.code4.monitorizarevot.net.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ro.code4.monitorizarevot.net.model.Version;

public class VersionResponse implements Serializable {

    // TODO serialized names to be translated when api is updated
    @Expose
    @SerializedName("versiune")
    private Version version;

    public Version getVersion() {
        return version;
    }
}
