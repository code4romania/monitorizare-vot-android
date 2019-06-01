package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class County extends RealmObject implements Serializable {

    public static final String ID_FIELD = "id";
    public static final String COUNTY_CODE_FIELD = "code";
    public static final String COUNTY_NAME_FIELD = "name";
    public static final String BRANCHES_COUNT_FIELD = "limit";

    @PrimaryKey
    @Expose
    @SerializedName(ID_FIELD)
    private int id;

    @Expose
    @SerializedName(COUNTY_CODE_FIELD)
    private String code;

    @Expose
    @SerializedName(COUNTY_NAME_FIELD)
    private String name;

    @Expose
    @SerializedName(BRANCHES_COUNT_FIELD)
    private int branchesCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranchesCount() {
        return branchesCount;
    }

    public void setBranchesCount(int branchesCount) {
        this.branchesCount = branchesCount;
    }
}
