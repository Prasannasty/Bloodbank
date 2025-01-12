public class Blood {
    private String bloodType;
    private int unitsAvailable;

    public Blood(String bloodType, int unitsAvailable) {
        this.bloodType = bloodType;
        this.unitsAvailable = unitsAvailable;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public void addUnits(int units) {
        this.unitsAvailable += units;
    }

    public void removeUnits(int units) {
        this.unitsAvailable -= units;
    }

}