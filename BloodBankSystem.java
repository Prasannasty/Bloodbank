import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodBankSystem {
    private List<Donor> donors;
    private List<Recipient> recipients;
    private List<Blood> bloodInventory;

    public BloodBankSystem() {
        donors = new ArrayList<>();
        recipients = new ArrayList<>();
        bloodInventory = new ArrayList<>();
        loadDonorsFromDatabase();
        loadRecipientsFromDatabase();
        loadBloodInventoryFromDatabase();
    }

    public List<Donor> getDonors() {
        return donors;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public List<Blood> getBloodInventory() {
        return bloodInventory;
    }

    private void loadDonorsFromDatabase() {
        String query = "SELECT * FROM donors";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Donor donor = new Donor(
                        rs.getString("donorId"),
                        rs.getString("name"),
                        rs.getString("bloodType"),
                        rs.getInt("age"),
                        rs.getString("lastDonationDate")
                );
                donors.add(donor);
            }
        } catch (SQLException e) {
            System.out.println("Error loading donors from the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadRecipientsFromDatabase() {
        String query = "SELECT * FROM recipients";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Recipient recipient = new Recipient(
                        rs.getString("recipientId"),
                        rs.getString("name"),
                        rs.getString("bloodType"),
                        rs.getInt("requiredUnits")
                );
                recipients.add(recipient);
            }
        } catch (SQLException e) {
            System.out.println("Error loading recipients from the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadBloodInventoryFromDatabase() {
        String query = "SELECT * FROM blood_inventory";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Blood blood = new Blood(
                        rs.getString("bloodType"),
                        rs.getInt("unitsAvailable")
                );
                bloodInventory.add(blood);
            }
        } catch (SQLException e) {
            System.out.println("Error loading blood inventory from the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // New method to add blood inventory to the database
    public void addBloodInventoryToDatabase(Blood blood) {
        String query = "INSERT INTO blood_inventory (bloodType, unitsAvailable) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, blood.getBloodType());
            pstmt.setInt(2, blood.getUnitsAvailable());
            pstmt.executeUpdate();
            bloodInventory.add(blood);  // Add blood to local list after successful database insert
            System.out.println("Blood inventory added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding blood inventory to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addDonorToDatabase(Donor donor) {
        String query = "INSERT INTO donors (donorId, name, bloodType, age, lastDonationDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, donor.getDonorId());
            pstmt.setString(2, donor.getName());
            pstmt.setString(3, donor.getBloodType());
            pstmt.setInt(4, donor.getAge());
            pstmt.setString(5, donor.getLastDonationDate());
            pstmt.executeUpdate();
            donors.add(donor);  // Add donor to local list after successful database insert
        } catch (SQLException e) {
            System.out.println("Error adding donor to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addRecipientToDatabase(Recipient recipient) {
        String query = "INSERT INTO recipients (recipientId, name, bloodType, requiredUnits) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, recipient.getRecipientId());
            pstmt.setString(2, recipient.getName());
            pstmt.setString(3, recipient.getBloodType());
            pstmt.setInt(4, recipient.getRequiredUnits());
            pstmt.executeUpdate();
            recipients.add(recipient);  // Add recipient to local list after successful database insert
        } catch (SQLException e) {
            System.out.println("Error adding recipient to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fulfillBloodRequest(Recipient recipient) {
        Blood bloodToUse = null;
        for (Blood blood : bloodInventory) {
            if (blood.getBloodType().equals(recipient.getBloodType()) && blood.getUnitsAvailable() >= recipient.getRequiredUnits()) {
                bloodToUse = blood;
                break;
            }
        }

        if (bloodToUse != null) {
            bloodToUse.removeUnits(recipient.getRequiredUnits());
            System.out.println("Blood request fulfilled for recipient: " + recipient.getName());

            try (Connection conn = DatabaseConnection.getConnection()) {
                String updateQuery = "UPDATE blood_inventory SET unitsAvailable = ? WHERE bloodType = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                    pstmt.setInt(1, bloodToUse.getUnitsAvailable());
                    pstmt.setString(2, bloodToUse.getBloodType());
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("Error updating blood inventory in the database: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Not enough blood units available to fulfill the request.");
        }
    }
}