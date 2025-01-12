public class Donor {
    private String donorId;
    private String name;
    private String bloodType;
    private int age;
    private String lastDonationDate;

    public Donor(String donorId, String name, String bloodType, int age, String lastDonationDate) {
        this.donorId = donorId;
        this.name = name;
        this.bloodType = bloodType;
        this.age = age;
        this.lastDonationDate = lastDonationDate;
    }

    public String getDonorId() {
        return donorId;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getAge() {
        return age;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
}