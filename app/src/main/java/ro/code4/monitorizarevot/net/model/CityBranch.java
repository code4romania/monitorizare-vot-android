package ro.code4.monitorizarevot.net.model;

public class CityBranch {
    private String countryCode;
    private int branch;

    public CityBranch(String codJudet, int numarSectie) {
        this.countryCode = codJudet;
        this.branch = numarSectie;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getBranch() {
        return branch;
    }

    @Override
    public boolean equals(Object obj) {
        CityBranch cityBranch = (CityBranch) obj;
        return countryCode.equalsIgnoreCase(cityBranch.getCountryCode())
                && branch == cityBranch.getBranch();
    }
}
