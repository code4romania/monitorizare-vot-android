package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BranchDetails extends RealmObject implements Syncable {

    // TODO serialized names to be translated when api is updated
    @PrimaryKey
    private String id;

    @Expose
    @SerializedName("codJudet")
    private String countyCode;

    @Expose
    @SerializedName("numarSectie")
    private int branchNumber;

    @Expose
    @SerializedName("esteZonaUrbana")
    private boolean isUrban;

    @Expose
    @SerializedName("presedinteBesvesteFemeie")
    private boolean isFemale;

    @Expose
    @SerializedName("oraSosirii")
    private String timeEnter;

    @Expose
    @SerializedName("oraPlecarii")
    private String timeLeave;

    private boolean isSynced;

    public BranchDetails() {

    }

    public BranchDetails(String countyCode, int branchNumber) {
        this.countyCode = countyCode;
        this.branchNumber = branchNumber;
        this.id = countyCode + String.valueOf(branchNumber);
        this.isSynced = false;
    }

    public BranchDetails(String countyCode, int branchNumber, boolean isUrban, boolean isFemale, String timeEnter, String timeLeave) {
        this(countyCode, branchNumber);
        this.isUrban = isUrban;
        this.isFemale = isFemale;
        this.timeEnter = timeEnter;
        this.timeLeave = timeLeave;
    }

    public String getId() {
        return id;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public boolean isUrban() {
        return isUrban;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public String getTimeEnter() {
        return timeEnter;
    }

    public String getTimeLeave() {
        return timeLeave;
    }

    @Override
    public boolean isSynced() {
        return isSynced;
    }

    @Override
    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BranchDetails) {
            BranchDetails other = (BranchDetails) obj;
            return id.equals(other.getId()) &&
                    countyCode.equals(other.getCountyCode()) &&
                    branchNumber == other.getBranchNumber() &&
                    isUrban == other.isUrban() &&
                    isFemale == other.isFemale() &&
                    ((timeEnter == null && other.getTimeLeave() == null) ||
                        (timeEnter != null && other.getTimeEnter() != null && timeEnter.equals(other.getTimeEnter()))) &&
                    ((timeLeave == null && other.getTimeLeave() == null) ||
                        (timeLeave != null && other.getTimeLeave() != null && timeLeave.equals(other.getTimeLeave())));
        }
        return false;
    }
}
