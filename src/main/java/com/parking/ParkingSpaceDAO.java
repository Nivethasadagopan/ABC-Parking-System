package com.parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ParkingSpaceDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/parkingdb";
    private static final String USER = "root";
    private static final String PASSWORD = "yourpassword";

    // Update Parking Space
    public boolean updateParkingSpace(ParkingSpace space) {
        String sql = "UPDATE parking_spaces SET space_number=?, address1=?, address2=?, county=?, " +
                     "price_per_hour=?, available=?, accessible_parking=?, close_to_bus=? WHERE id=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, space.getSpaceNumber());
            ps.setString(2, space.getAddress1());
            ps.setString(3, space.getAddress2());
            ps.setString(4, space.getCounty());
            ps.setDouble(5, space.getPricePerHour());
            ps.setBoolean(6, space.isAvailable());
            ps.setBoolean(7, space.isAccessibleParking());
            ps.setBoolean(8, space.isCloseToBus());
            ps.setString(9, space.getId());

            int rows = ps.executeUpdate();
            return rows > 0; // true if updated successfully

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
