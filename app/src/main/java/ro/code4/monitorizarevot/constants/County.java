package ro.code4.monitorizarevot.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum County {
    ALBA("AB", "Alba", 440),
    ARAD("AR", "Arad", 437),
    ARGES("AG", "Argeș", 520),
    BACAU("BC", "Bacău", 635),
    BIHOR("BH", "Bihor", 653),
    BISTRITA("BN", "Bistriţa-Năsăud", 313),
    BOTOSANI("BT", "Botoșani", 425),
    BRASOV("BV", "Brașov", 454),
    BRAILA("BR", "Brăila", 280),
    BUCURESTI("B", "București", 1269),
    BUZAU("BZ", "Buzău", 426),
    CARAS("CS", "Caraș-Severin", 365),
    CALARASI("CL", "Călărași", 235),
    CLUJ("CJ", "Cluj", 664),
    CONSTANTA("CT", "Constanţa", 556),
    COVASNA("CV", "Covasna", 214),
    DAMBOVITA("DB", "Dâmboviţa", 433),
    DOLJ("DJ", "Dolj", 531),
    GALATI("GL", "Galaţi", 436),
    GIURGIU("GR", "Giurgiu", 245),
    GORJ("GJ", "Gorj", 332),
    HARGHITA("HR", "Harghita", 290),
    HUNEDOARA("HD", "Hunedoara", 524),
    IALOMITA("IL", "Ialomiţa", 220),
    IASI("IS", "Iași", 746),
    ILFOV("IF", "Ilfov", 258),
    MARAMURES("MM", "Maramureş", 437),
    MEHEDINTI("MH", "Mehedinţi", 290),
    MURES("MS", "Mureş", 568),
    NEAMT("NT", "Neamţ", 488),
    OLT("OT", "Olt", 380),
    PRAHOVA("PH", "Prahova", 623),
    SATUMARE("SM", "Satu Mare", 337),
    SALAJ("SJ", "Sălaj", 312),
    SIBIU("SB", "Sibiu", 373),
    SUCEAVA("SV", "Suceava", 560),
    TELEORMAN("TR", "Teleorman", 334),
    TIMIS("TM", "Timiş", 599),
    TULCEA("TL", "Tulcea", 204),
    VASLUI("VS", "Vaslui", 536),
    VALCEA("VL", "Vâlcea", 430),
    VRANCEA("VN", "Vrancea", 358),
    DIASPORA("D", "Diaspora", 377);

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

    public static County getCountyByIndex(int index) {
        return County.values()[index];
    }

    public static int getIndexByCountyCode(String countyCode) {
        for (County county : values()) {
            if (county.getCode().equals(countyCode)) {
                return Arrays.asList(values()).indexOf(county);
            }
        }
        return -1;
    }
}
