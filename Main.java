import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BloodBankSystem bloodBankSystem = new BloodBankSystem();
        Scanner scanner = new Scanner(System.in);
//update for new objects
        while (true) {
            System.out.println("===== Blood Bank Management System =====");
            System.out.println("1. View Blood Inventory");
            System.out.println("2. Add Donor");
            System.out.println("3. Add Recipient");
            System.out.println("4. Fulfill Blood Request");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Blood Inventory:");
                    if (bloodBankSystem.getBloodInventory().isEmpty()) {
                        System.out.println("No blood inventory available.");
                    } else {
                        for (Blood blood : bloodBankSystem.getBloodInventory()) {
                            System.out.println("Blood Type: " + blood.getBloodType() + ", Units Available: " + blood.getUnitsAvailable());
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter Donor Name: ");
                    String donorName = scanner.nextLine();
                    System.out.print("Enter Donor Blood Type: ");
                    String donorBloodType = scanner.nextLine();
                    System.out.print("Enter Donor Age: ");
                    int donorAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String donorId = "D" + (bloodBankSystem.getDonors().size() + 1);
                    Donor newDonor = new Donor(donorId, donorName, donorBloodType, donorAge, "2024-08-25");
                    bloodBankSystem.addDonorToDatabase(newDonor);
                    System.out.println("Donor added successfully!");
                    break;
                case 3:
                    System.out.print("Enter Recipient Name: ");
                    String recipientName = scanner.nextLine();
                    System.out.print("Enter Recipient Blood Type: ");
                    String recipientBloodType = scanner.nextLine();
                    System.out.print("Enter Units Required: ");
                    int requiredUnits = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    String recipientId = "R" + (bloodBankSystem.getRecipients().size() + 1);
                    Recipient newRecipient = new Recipient(recipientId, recipientName, recipientBloodType, requiredUnits);
                    bloodBankSystem.addRecipientToDatabase(newRecipient);
                    System.out.println("Recipient added successfully!");
                    break;
                case 4:
                    System.out.print("Enter Recipient ID to fulfill blood request: ");
                    String recId = scanner.nextLine();
                    Recipient recipientToFulfill = null;
                    for (Recipient recipient : bloodBankSystem.getRecipients()) {
                        if (recipient.getRecipientId().equals(recId)) {
                            recipientToFulfill = recipient;
                            break;
                        }
                    }
                    if (recipientToFulfill != null) {
                        bloodBankSystem.fulfillBloodRequest(recipientToFulfill);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the system. Thank you!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}