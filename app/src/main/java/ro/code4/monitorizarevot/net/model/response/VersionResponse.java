package ro.code4.monitorizarevot.net.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ro.code4.monitorizarevot.net.model.Version;

public class VersionResponse implements Serializable {

    // TODO serialized names to be translated when api is updated
    @Expose
    @SerializedName("versiune")
    private List<Version> versions;

    public List<Version> getVersions() {
        return versions;
    }
}
