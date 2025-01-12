public class Recipient {
    private String recipientId;
    private String name;
    private String bloodType;
    private int requiredUnits;

    public Recipient(String recipientId, String name, String bloodType, int requiredUnits) {
        this.recipientId = recipientId;
        this.name = name;
        this.bloodType = bloodType;
        this.requiredUnits = requiredUnits;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getRequiredUnits() {
        return requiredUnits;
    }

}