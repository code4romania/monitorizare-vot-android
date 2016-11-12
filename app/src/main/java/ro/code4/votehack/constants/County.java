package ro.code4.votehack.constants;

import java.util.ArrayList;
import java.util.List;

public enum County {
    ALBA("AB", "Alba", 1000), //TODO update branches count
    ARAD("AR", "Arad", 1000),
    ARGES("AG", "Argeș", 1000),
    BACAU("BC", "Bacău", 1000),
    BIHOR("BH", "Bihor", 1000),
    BISTRITA("BN", "Bistriţa-Năsăud", 1000),
    BOTOSANI("BT", "Botoșani", 1000),
    BRASOV("BV", "Brașov", 1000),
    BRAILA("BR", "Brăila", 1000),
    BUCURESTI("B", "București", 1000),
    BUZAU("BZ", "Buzău", 1000),
    CARAS("CS", "Caraș-Severin", 1000),
    CALARASI("CL", "Călărași", 1000),
    CLUJ("CJ", "Cluj", 1000),
    CONSTANTA("CT", "Constanţa", 1000),
    COVASNA("CV", "Covasna", 1000),
    DAMBOVITA("DB", "Dâmboviţa", 1000),
    DOLJ("DJ", "Dolj", 1000),
    GALATI("GL", "Galaţi", 1000),
    GIURGIU("GR", "Giurgiu", 1000),
    GORJ("GJ", "Gorj", 1000),
    HARGHITA("HR", "Harghita", 1000),
    HUNEDOARA("HD", "Hunedoara", 1000),
    IALOMITA("IL", "Ialomiţa", 1000),
    IASI("IS", "Iași", 1000),
    ILFOV("IF", "Ilfov", 1000),
    MARAMURES("MM", "Maramureş", 1000),
    MEHEDINTI("MH", "Mehedinţi", 1000),
    MURES("MS", "Mureş", 1000),
    NEAMT("NT", "Neamţ", 1000),
    OLT("OT", "Olt", 1000),
    PRAHOVA("PH", "Prahova", 1000),
    SATUMARE("SM", "Satu Mare", 1000),
    SALAJ("SJ", "Sălaj", 1000),
    SIBIU("SB", "Sibiu", 1000),
    SUCEAVA("SV", "Suceava", 1000),
    TELEORMAN("TR", "Teleorman", 1000),
    TIMIS("TM", "Timiş", 1000),
    TULCEA("TL", "Tulcea", 1000),
    VASLUI("VS", "Vaslui", 1000),
    VALCEA("VL", "Vâlcea", 1000),
    VRANCEA("VN", "Vrancea", 1000);

    private final String code;
    private final String name;
    private final int branchesCount;

    County(String code, String name, int branchesCount) {
        this.code = code;
        this.name = name;
        this.branchesCount = branchesCount;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getBranchesCount() {
        return branchesCount;
    }

    public static List<String> getCountiesNames() {
        List<String> counties = new ArrayList<>();
        for (County county : values()) {
            counties.add(county.getName());
        }
        return counties;
    }

    public static List<String> getCountyBranches(County county) {
        List<String> branches = new ArrayList<>();
        for (int index = 1; index <= county.getBranchesCount(); index++) {
            branches.add(String.valueOf(index));
        }
        return branches;
    }

    public static County getCountyByIndex(int index) {
        return County.values()[index];
    }
}
