package ro.code4.monitorizarevot.net.model;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BranchDetails extends RealmObject implements Syncable {

    @PrimaryKey
    private String id;

    @Expose
    private String codJudet;

    @Expose
    private int numarSectie;

    @Expose
    private boolean esteZonaUrbana;

    @Expose
    private boolean presedinteBesvesteFemeie;

    @Expose
    private String oraSosirii;

    @Expose
    private String oraPlecarii;

    private boolean isSynced;

    public BranchDetails() {

    }

    public BranchDetails(String countyCode, int branchNumber) {
        this.codJudet = countyCode;
        this.numarSectie = branchNumber;
        this.id = countyCode + String.valueOf(branchNumber);
        this.isSynced = false;
    }

    public BranchDetails(String countyCode, int branchNumber, boolean isUrban, boolean isFemale, String timeEnter, String timeLeave) {
        this(countyCode, branchNumber);
        this.esteZonaUrbana = isUrban;
        this.presedinteBesvesteFemeie = isFemale;
        this.oraSosirii = timeEnter;
        this.oraPlecarii = timeLeave;
    }

    public String getId() {
        return id;
    }

    public String getCountyCode() {
        return codJudet;
    }

    public int getBranchNumber() {
        return numarSectie;
    }

    public boolean isUrban() {
        return esteZonaUrbana;
    }

    public boolean isFemale() {
        return presedinteBesvesteFemeie;
    }

    public String getTimeEnter() {
        return oraSosirii;
    }

    public String getTimeLeave() {
        return oraPlecarii;
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
                    codJudet.equals(other.getCountyCode()) &&
                    numarSectie == other.getBranchNumber() &&
                    esteZonaUrbana == other.isUrban() &&
                    presedinteBesvesteFemeie == other.isFemale() &&
                    ((oraSosirii == null && other.getTimeLeave() == null) ||
                        (oraSosirii != null && other.getTimeEnter() != null && oraSosirii.equals(other.getTimeEnter()))) &&
                    ((oraPlecarii == null && other.getTimeLeave() == null) ||
                        (oraPlecarii != null && other.getTimeLeave() != null && oraPlecarii.equals(other.getTimeLeave())));
        }
        return false;
    }
}
